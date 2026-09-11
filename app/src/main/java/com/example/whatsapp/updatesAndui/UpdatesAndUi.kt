package com.example.whatsapp.updatesAndui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R
import com.example.whatsapp.bottomnavigation.BottomNavigation

@Composable
fun UpdatesAndUi(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { },
                    containerColor = colorResource(id = R.color.light_gray),
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Text Status",
                        tint = Color.DarkGray,
                    )
                }
                FloatingActionButton(
                    onClick = { },
                    containerColor = colorResource(id = R.color.dark_orange),
                    modifier = Modifier.padding(bottom = 65.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Camera",
                        tint = Color.White
                    )
                }
            }
        },
        bottomBar = { BottomNavigation(navController = navController, initialSelectedIndex = 1) }
    ) { innerPadding: PaddingValues ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TopBar()
            StatusScreen()
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Channels",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Explore >",
                        color = colorResource(id = R.color.dark_orange),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }

                ChannelsSection()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UpdatesAndUiPreview() {
    UpdatesAndUi(navController = rememberNavController())
}
