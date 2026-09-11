package com.example.whatsapp.callUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.whatsapp.bottomnavigation.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R

@Composable
fun CallTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calls",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.threedots),
                contentDescription = "More",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CallUi(navController: NavController) {
    data class MyModel(
        val imageOne: Int,
        val imageTwo: Int,
        val timeStamp: String,
        val mainText: String
    )

    val myList = listOf(
        MyModel(R.drawable.fullteam, R.drawable.audiocall, "02:15 PM", "Cjp core"),
        MyModel(R.drawable.naren, R.drawable.audiocall, "03:32 PM", "Meowdii ji"),
        MyModel(R.drawable.naimsiha, R.drawable.audiocall, "yesterday", "My core<3"),
        MyModel(R.drawable.dharmendra, R.drawable.audiocall, timeStamp = "A week ago", mainText = "Lost coal")
    )

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController, initialSelectedIndex = 3) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CallTopBar()
            Column {
                Row(modifier = Modifier.padding(start = 18.dp, top = 18.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.link),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = colorResource(id = R.color.dark_orange),
                                shape = CircleShape
                            )
                            .padding(8.dp),
                    )
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = " Create Call link",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Share a link for your WhatsApp call",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )

                    }

                }
                Text(
                    text = "Recent",
                    modifier = Modifier.padding(start = 12.dp, top = 20.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                myList.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = item.imageOne),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = item.mainText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.audiocall),
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),

                                    )
                                Text(
                                    text = item.timeStamp,
                                    modifier = Modifier.padding(start = 4.dp),
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Image(
                            painter = painterResource(id = item.imageTwo),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.dark_orange))
                        )
                    }
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CallUiPreview() {
    CallUi(navController = rememberNavController())
}
