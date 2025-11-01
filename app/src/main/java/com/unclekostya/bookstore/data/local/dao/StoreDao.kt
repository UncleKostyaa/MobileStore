package com.unclekostya.bookstore.data.local.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic

@Dao
interface StoreDao{

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE productId = :productId")
    suspend fun getProductById(productId: Int): Product

    @Query("SELECT * FROM productCharacteristic WHERE productId = :productId")
    suspend fun getProductCharacteristicById(productId: Int): ProductCharacteristic

    @Query("SELECT * FROM cart")
    suspend fun getCart(): Cart

    @Query("DELETE FROM cart WHERE Id = 1")
    suspend fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCharacteristic(productCharacteristic: ProductCharacteristic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCart(cart: Cart)




}