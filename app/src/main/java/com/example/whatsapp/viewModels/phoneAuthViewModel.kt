package com.example.whatsapp.viewModels

import android.app.Activity
import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.ViewModel
import com.example.whatsapp.models.PhoneAuthuser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object OtpSent : AuthState()
    data class Success(val user: PhoneAuthuser? = null) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var verificationId: String? = null

    fun sendOtp(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.Loading
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    _authState.value = AuthState.Error(e.localizedMessage ?: "Verification failed")
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@PhoneAuthViewModel.verificationId = verificationId
                    _authState.value = AuthState.OtpSent
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otp: String) {
        if (verificationId == null) {
            _authState.value = AuthState.Error("Verification ID is null")
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        _authState.value = AuthState.Loading
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    val phoneAuthUser = PhoneAuthuser(
                        userID = user.uid,
                        phoneNumber = user.phoneNumber ?: ""
                    )
                    saveUserToDb(phoneAuthUser)
                }
            } else {
                _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Sign in failed")
            }
        }
    }

    private fun saveUserToDb(user: PhoneAuthuser) {
        db.getReference("users").child(user.userID).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Failed to save user info")
                }
            }
    }

    fun markUserAsSignedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchUserProfile(currentUser.uid) { user ->
                _authState.value = AuthState.Success(user)
            }
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }

    fun fetchUserProfile(userId: String, onResult: (PhoneAuthuser?) -> Unit) {
        db.getReference("users").child(userId).get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(PhoneAuthuser::class.java)
            onResult(user)
        }.addOnFailureListener {
            onResult(null)
        }
    }

    fun saveUserProfile(name: String, bitmap: Bitmap?) {
        val currentUser = auth.currentUser ?: return
        _authState.value = AuthState.Loading
        
        val imageUrl = if (bitmap != null) convertBitmapTo64(bitmap) else ""
        
        val userUpdate = mapOf(
            "userName" to name,
            "imageUrl" to imageUrl
        )

        db.getReference("users").child(currentUser.uid).updateChildren(userUpdate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserProfile(currentUser.uid) { updatedUser ->
                        _authState.value = AuthState.Success(updatedUser)
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Update failed")
                }
            }
    }

    private fun convertBitmapTo64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
