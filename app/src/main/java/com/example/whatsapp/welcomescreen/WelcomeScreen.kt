package com.example.whatsapp.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R
import com.example.whatsapp.navigation.Routes

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.siro_1),

            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
            Text( text=" welcome to shiro app" , fontSize =22.sp, fontWeight = FontWeight.Bold )
        Spacer(modifier = Modifier.height(14.dp))
        Row() {
            Text(text = "Read out shiro~ ", color = Color.Gray)
            Text(text = " privacy policy", color= Color.Red)
        }
        Row() {
            Text(text = " or haan sinchan ~ ", color=Color.Gray)
            Text(text = " term and conditions", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(Routes.UserRegistration)
        } , modifier = Modifier.size(240.dp,40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id=R.color.dark_orange))) {
            Text(text = "welcome in shiro ki duniya", fontSize = 15.sp)
        }


    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = rememberNavController())
}
