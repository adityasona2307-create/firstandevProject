package com.example.whatsapp.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R
import com.example.whatsapp.navigation.Routes

@Composable
fun BottomNavigation(navController: NavController, initialSelectedIndex: Int = 0) {
    var selectedItem by remember { mutableIntStateOf(initialSelectedIndex) }
    val items = listOf("Chats", "Updates", "Communities", "Calls")
    val icons = listOf(
        Icons.AutoMirrored.Filled.Chat,
        Icons.Default.Update,
        Icons.Default.Groups,
        Icons.Default.Call
    )

    NavigationBar(
        containerColor = colorResource(id = R.color.dark_orange),
        contentColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = icons[index], 
                        contentDescription = item
                    ) 
                },
                label = { Text(text = item) },
                selected = selectedItem == index,
                onClick = { 
                    selectedItem = index 
                    when(index) {
                        0 -> navController.navigate(Routes.HomeScreen)
                        1 -> navController.navigate(Routes.UpdateScreen)
                        2 -> navController.navigate(Routes.CommunityScreen)
                        3 -> navController.navigate(Routes.CallScreen)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                    unselectedTextColor = Color.White.copy(alpha = 0.7f),
                    indicatorColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(navController = rememberNavController())
}
