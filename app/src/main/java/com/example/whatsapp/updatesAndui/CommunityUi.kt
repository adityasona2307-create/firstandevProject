package com.example.whatsapp.updatesAndui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.R
import com.example.whatsapp.bottomnavigation.BottomNavigation

data class CommunityData(
    val name: String,
    val image: Int,
    val groups: List<GroupData>
)

data class GroupData(
    val name: String,
    val lastMessage: String,
    val time: String,
    val image: Int
)

@Preview(showSystemUi = true)
@Composable
fun CommunityUiPreview() {
    CommunityUi(navController = rememberNavController())
}

@Composable
fun CommunityUi(navController: NavController) {
    val communities = listOf(
        CommunityData(
            name = "Gen Z Protest",
            image = R.drawable.community,
            groups = listOf(
                GroupData("Announcements", "20 july ko Sansad march hai", "10:30 AM", R.drawable.community),
                GroupData("Food service", "Junaid Bhai aaj biryani laayein hain", "Yesterday", R.drawable.junaid)
            )
        ),
        CommunityData(
            name = "CJP spokeperson Team",
            image = R.drawable.siro_1,
            groups = listOf(
                GroupData("Announcements", "Vijeta Dahiya is no more member of our Team", "Tuesday", R.drawable.community),
                GroupData("Team Meeting","Meating at Janatar Mantar at 8:00pm ","Monday", R.drawable.junaid)
            )
        )
    )

    Scaffold(
        topBar = {
            Column {
                Text(
                    text = "Communities",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.dark_orange),
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            }
        },
        bottomBar = { BottomNavigation(navController = navController, initialSelectedIndex = 2) }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // New Community Row
            NewCommunityRow()

            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(colorResource(id = R.color.light_gray).copy(alpha = 0.3f)))

            // List of Communities
            communities.forEach { community ->
                CommunitySection(community)
                Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(colorResource(id = R.color.light_gray).copy(alpha = 0.3f)))
            }
        }
    }
}

@Composable
fun NewCommunityRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.dark_orange).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                tint = colorResource(id = R.color.dark_orange),
                modifier = Modifier.size(24.dp)
            )
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.dark_orange)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "New community",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CommunitySection(community: CommunityData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Community Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = community.image),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = community.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
        }
        
        HorizontalDivider(modifier = Modifier.padding(start = 80.dp), color = Color.LightGray.copy(alpha = 0.5f))

        // Community Groups
        community.groups.forEach { group ->
            GroupItem(group)
        }

        // View All
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp).padding(start = 12.dp)
            )
            Spacer(modifier = Modifier.width(28.dp))
            Text(
                text = "View all",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun GroupItem(group: GroupData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = group.image),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = group.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = group.time,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Text(
                text = group.lastMessage,
                color = Color.Gray,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
