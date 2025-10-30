package com.unclekostya.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    val productName: String,
    val productCost: String,
    val count: Int,
    val productImageUrl: String,
)