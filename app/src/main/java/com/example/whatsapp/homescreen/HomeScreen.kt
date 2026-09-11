package com.example.whatsapp.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R
import com.example.whatsapp.bottomnavigation.BottomNavigation
import com.example.whatsapp.chat_box.ChatListModel

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.whatsapp.navigation.Routes

@Composable
fun HomeScreen(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }
    val chatList = listOf(
        ChatListModel(R.drawable.abhj, "Abhijit CJP", "2:29 PM", "Aditya, it's done bro"),
        ChatListModel(R.drawable.sallu, "sallu", "1:15 PM", "Adii bhaii, its's done bro"),
        ChatListModel(R.drawable.naren, "Narednra ", "1:45 PM", "Maalik 2 masala Tea laga du"),
        ChatListModel(R.drawable.dharmendra, "Dharmendra pradhan ", "1:00 PM", "Bhai yrr gadkari ko bhi maaro na"),
        ChatListModel(R.drawable.naimsiha, "Naimisha <3", "Yesterday", "Baby! i m going to deactivate insta"),
        ChatListModel(R.drawable.munawar, "Munawar", "Monday", "Bhai vo ayesha ko release krva do yrr"),
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = colorResource(id = R.color.dark_orange),
                modifier = Modifier.size(44.dp),
            ) {
                Icon(painter = painterResource(id = R.drawable.yel), contentDescription = null)
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title Text (Left side)
                Text(
                    text = "ShiroApp",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.dark_orange),
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                // Icons Row (Right side)
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Camera Icon",
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options Icon",
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                showMenu = false
                                navController.navigate(Routes.ProfileScreen)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { showMenu = false }
                        )
                    }
                }
            }
            HorizontalDivider()
            LazyColumn {
                items(chatList) { chat ->
                    Chatdesign(chatListModel = chat)
                }
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}




