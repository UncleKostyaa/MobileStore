package com.unclekostya.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productCharacteristic")
data class ProductCharacteristic(
    @PrimaryKey val productId: Int,
    val phoneModel: String?,
    val phoneOs: String?,
    val phoneDisplay: String?,
    val phoneRefreshRate: Int?,
    val phoneProcessor: String?,
    val phoneStorage: Int?,
    val phoneRam: Int,
    val phoneBatteryCapacity: Int?,
)