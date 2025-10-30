package com.unclekostya.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey val Id: Int = 1,
    val listOfProductsId: List<Int> = emptyList()
)