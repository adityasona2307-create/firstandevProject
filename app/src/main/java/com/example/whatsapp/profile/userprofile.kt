package com.example.whatsapp.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import com.example.whatsapp.R
import com.example.whatsapp.models.PhoneAuthuser
import com.example.whatsapp.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val user: PhoneAuthuser) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        val currentUser = auth.currentUser ?: return
        _profileState.value = ProfileState.Loading
        db.getReference("users").child(currentUser.uid).get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(PhoneAuthuser::class.java)
            if (user != null) {
                _profileState.value = ProfileState.Success(user)
            } else {
                _profileState.value = ProfileState.Error("User not found")
            }
        }.addOnFailureListener {
            _profileState.value = ProfileState.Error(it.localizedMessage ?: "Failed to fetch profile")
        }
    }

    fun updateProfile(name: String, bitmap: Bitmap?) {
        val currentUser = auth.currentUser ?: return
        _profileState.value = ProfileState.Loading

        val imageUrl = if (bitmap != null) convertBitmapTo64(bitmap) else null

        val userUpdate = mutableMapOf<String, Any>(
            "userName" to name
        )
        if (imageUrl != null) {
            userUpdate["imageUrl"] = imageUrl
        }

        db.getReference("users").child(currentUser.uid).updateChildren(userUpdate)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserProfile()
                } else {
                    _profileState.value = ProfileState.Error(task.exception?.localizedMessage ?: "Update failed")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()
    var name by remember { mutableStateOf("") }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(profileState) {
        if (profileState is ProfileState.Success) {
            name = (profileState as ProfileState.Success).user.userName
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.dark_orange))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clickable { /* Handle image pick - This would normally launch an Intent or use a PhotoPicker */ },
                contentAlignment = Alignment.BottomEnd
            ) {
                if (selectedBitmap != null) {
                    Image(
                        bitmap = selectedBitmap!!.asImageBitmap(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    val currentUser = (profileState as? ProfileState.Success)?.user
                    val decodedBitmap = remember(currentUser?.imageUrl) {
                        if (currentUser?.imageUrl?.isNotEmpty() == true) {
                            try {
                                val imageBytes = Base64.decode(currentUser.imageUrl, Base64.DEFAULT)
                                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            } catch (e: Exception) {
                                null
                            }
                        } else null
                    }

                    if (decodedBitmap != null) {
                        Image(
                            bitmap = decodedBitmap.asImageBitmap(),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Inside
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.dark_orange))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Change Picture", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.dark_orange),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (profileState is ProfileState.Loading) {
                CircularProgressIndicator(color = colorResource(id = R.color.dark_orange))
            } else {
                Button(
                    onClick = { viewModel.updateProfile(name, selectedBitmap) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_orange)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Profile", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            if (profileState is ProfileState.Error) {
                Text(
                    text = (profileState as ProfileState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Routes.WelcomeScreen) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Sign Out", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}
