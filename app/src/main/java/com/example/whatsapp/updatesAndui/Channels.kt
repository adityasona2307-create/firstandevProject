package com.example.whatsapp.updatesAndui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R

@Composable
fun ChannelItem(status: StatusModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = status.image),
            contentDescription = status.name,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row (modifier = Modifier.weight(1f)) {
            Text(
                text = status.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Text(
                text = status.time,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

        }
    }
}

@Composable
fun ChannelsSection() {
    val statusList = listOf(
        StatusModel(image = R.drawable.junaid, name = "junaid bhai ka bhandara", time = "14:24"),
        StatusModel(image = R.drawable.naren, name = "Mandir K chor ", time = "22/07/26"),
        StatusModel(image = R.drawable.abhj, name = "CJP notifcations", time = "20/07/26")
    )
    Column {
        statusList.forEach { channel ->
            ChannelItem(channel)
            HorizontalDivider(modifier = Modifier.height(1.dp), color = Color.LightGray)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChannelsPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        ChannelsSection()
    }
}
