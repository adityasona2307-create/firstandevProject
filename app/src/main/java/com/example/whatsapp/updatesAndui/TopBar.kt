package com.example.whatsapp.updatesAndui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R

@Preview(showSystemUi = true)
@Composable
fun TopBar() {
    var isSearching by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            if (isSearching) {
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text(text = "Search") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.dark_orange),
                        unfocusedIndicatorColor = colorResource(id = R.color.dark_orange)
                    ),
                    trailingIcon = {
                        IconButton(onClick = { 
                            isSearching = false 
                            search = ""
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close Search")
                        }
                    }
                )
            } else {
                Text(
                    text = "Updates",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.dark_orange),
                    modifier = Modifier.padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.camera),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { isSearching = true },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { showMenu= true

                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.threedots),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        )

                    DropdownMenu(expanded = showMenu ,
                    onDismissRequest = { showMenu = false} ) {
                        DropdownMenuItem(text = {Text(text = "New group")},
                            onClick = { showMenu = false})


                        DropdownMenuItem(text = {Text(text = "New community")},
                            onClick = { showMenu = false})


                        DropdownMenuItem(text = {Text(text = "Broadcast lists")},
                            onClick = { showMenu = false})

                        DropdownMenuItem(text = {Text(text = "Linked devices")},
                            onClick = { showMenu = false})

                        DropdownMenuItem(text = {Text(text = "Starred")},
                            onClick = { showMenu = false})

                        DropdownMenuItem(text = {Text(text = "Payments")},
                            onClick = { showMenu = false})


                        DropdownMenuItem(text = {Text(text = "Read all")},
                            onClick = { showMenu = false})


                        DropdownMenuItem(text = {Text(text = "Settings")},
                            onClick = { showMenu = false})










                    }
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

    }
}
