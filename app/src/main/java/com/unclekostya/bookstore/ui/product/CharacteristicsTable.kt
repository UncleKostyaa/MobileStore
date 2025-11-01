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
    val stringCh: String? = when(characteristic) {
        "OS" -> model.value?.phoneOs
        "Display" -> model.value?.phoneDisplay
        "Refresh rate" -> null
        "Processor" -> model.value?.phoneProcessor
        "RAM" -> null
        "Battery capacity" -> null
        "Storage" -> null
        else -> model.value?.phoneModel
    }
    val intCh: Int? = when(characteristic) {
        "OS" -> null
        "Display" -> null
        "Refresh rate" -> model.value?.phoneRefreshRate
        "Processor" -> null
        "RAM" -> model.value?.phoneRam
        "Battery capacity" -> model.value?.phoneBatteryCapacity
        "Storage" -> model.value?.phoneStorage
        else -> null
    }
    Row(

    ) {
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
            text = stringCh ?: "$intCh",
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