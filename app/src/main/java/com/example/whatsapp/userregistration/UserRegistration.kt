package com.example.whatsapp.userregistration

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.navigation.Routes
import com.example.whatsapp.viewModels.AuthState
import com.example.whatsapp.viewModels.PhoneAuthViewModel

@Composable
fun UserRegistration(
    navController: NavController,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    val countries = listOf("Japan", "India", "USA", "UK", "Germany")
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }

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
            is AuthState.OtpSent -> {
                navController.navigate(Routes.OtpVerification(phoneNumber = "$countryCode$phoneNumber"))
            }
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
            text = "Enter your phone number",
            fontSize = 22.sp,
            color = Color(0xFF008069), // WhatsApp Green
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "WhatsApp will need to verify your phone number. What's my number?",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Country Selection
        Column(
            modifier = Modifier.width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = { expanded = true }) {
                    Text(text = selectedCountry, color = Color.Black, fontSize = 16.sp)
                }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFF008069)
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFF008069)
            )
            
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(text = country) },
                        onClick = {
                            selectedCountry = country
                            countryCode = when(country) {
                                "India" -> "+91"
                                "USA" -> "+1"
                                "Japan" -> "+81"
                                "UK" -> "+44"
                                "Germany" -> "+49"
                                else -> "+91"
                            }
                            expanded = false
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Number Input
        Row(
            modifier = Modifier.width(200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = countryCode,
                onValueChange = { countryCode = it },
                modifier = Modifier.width(60.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF008069),
                    unfocusedIndicatorColor = Color(0xFF008069),
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF008069),
                    unfocusedIndicatorColor = Color(0xFF008069),
                ),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color(0xFF008069))
        } else {
            TextButton(
                onClick = {
                    if (phoneNumber.length >= 10 && activity != null) {
                        viewModel.sendOtp("$countryCode$phoneNumber", activity)
                    } else {
                        Toast.makeText(context, "Enter a valid number", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .background(Color(0xFF008069), RoundedCornerShape(4.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Debug Bypass Button
        TextButton(onClick = {
            navController.navigate(Routes.HomeScreen) {
                popUpTo(Routes.UserRegistration) { inclusive = true }
            }
        }) {
            Text(text = "Debug: Skip Login", color = Color.Gray)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserRegistrationPreview() {
    UserRegistration(navController = rememberNavController())
}
