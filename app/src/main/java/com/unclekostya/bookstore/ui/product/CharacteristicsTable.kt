package com.unclekostya.bookstore.ui.product

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic

@Composable
fun CharacteristicsTable(
    characteristic: String,
    model: MutableState<ProductCharacteristic?>
) {
    val value = when (characteristic) {
        "OS" -> model.value?.phoneOs
        "Display" -> model.value?.phoneDisplay
        "Refresh rate" -> model.value?.phoneRefreshRate?.let { "$it Hz" }
        "Processor" -> model.value?.phoneProcessor
        "RAM" -> model.value?.phoneRam?.let { "$it GB" }
        "Battery capacity" -> model.value?.phoneBatteryCapacity?.let { "$it mAh" }
        "Storage" -> model.value?.phoneStorage?.let { "$it GB" }
        else -> model.value?.phoneModel
    } ?: "—"
    Row {
        Text(
            text = characteristic,
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color =Color.Black
                )
                .padding(2.dp)
                .height(60.dp)

        )
        Text(
            text = value,
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color =Color.Black
                )
                .padding(2.dp)
                .height(60.dp)
        )
    }
}