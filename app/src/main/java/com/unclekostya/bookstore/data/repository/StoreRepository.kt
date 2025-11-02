package com.unclekostya.bookstore.data.repository

import androidx.room.Insert
import androidx.room.Query
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic

interface StoreRepository {
    suspend fun getAllProducts(): List<Product>

    suspend fun getProductById(productId: Int): Product

    suspend fun getProductCharacteristicById(productId: Int): ProductCharacteristic

    suspend fun getCart(): Cart

    suspend fun clearCart()

    suspend fun insertProduct(product: Product)

    suspend fun insertProductCharacteristic(productCharacteristic: ProductCharacteristic)

    suspend fun insertOrUpdateCart(cart: Cart)
    suspend fun deletePhoneFromCartById(productId: Int)
}