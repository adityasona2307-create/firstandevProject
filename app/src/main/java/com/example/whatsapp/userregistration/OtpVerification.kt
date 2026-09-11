package com.example.whatsapp.userregistration

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whatsapp.navigation.Routes
import com.example.whatsapp.viewModels.AuthState
import com.example.whatsapp.viewModels.PhoneAuthViewModel

@Composable
fun OtpVerification(
    navController: NavController,
    phoneNumber: String,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    var otpValue by remember { mutableStateOf("") }

    // Safely get the Activity
    val activity = remember(context) {
        var ctx = context
        while (ctx is android.content.ContextWrapper) {
            if (ctx is Activity) break
            ctx = ctx.baseContext
        }
        ctx as? Activity
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.UserRegistration) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetAuthState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Verifying your number",
            fontSize = 22.sp,
            color = Color(0xFF008069),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Waiting to automatically detect an SMS sent to $phoneNumber. Wrong number?",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = otpValue,
            onValueChange = { 
                if (it.length <= 6) {
                    otpValue = it
                    if (it.length == 6) {
                        viewModel.verifyOtp(it)
                    }
                }
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            placeholder = { Text(text = "- - -  - - -", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp, 
                textAlign = TextAlign.Center,
                letterSpacing = 8.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF008069),
                unfocusedIndicatorColor = Color(0xFF008069),
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Enter 6-digit code",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color(0xFF008069))
        } else {
            TextButton(
                onClick = {
                    if (otpValue.length == 6) {
                        viewModel.verifyOtp(otpValue)
                    } else {
                        Toast.makeText(context, "Enter 6-digit OTP", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .background(Color(0xFF008069), RoundedCornerShape(4.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Verify OTP",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        TextButton(onClick = { 
            if (activity != null) {
                viewModel.sendOtp(phoneNumber, activity)
                Toast.makeText(context, "Resending OTP...", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Resend SMS", color = Color(0xFF008069), fontWeight = FontWeight.Bold)
        }
    }
}
