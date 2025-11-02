package com.unclekostya.bookstore.data.repository

import com.unclekostya.bookstore.data.local.dao.StoreDao
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic
import okhttp3.internal.filterList

class StoreRepositoryImpl(private val storeDao: StoreDao): StoreRepository {
    override suspend fun getAllProducts(): List<Product> =
        storeDao.getAllProducts()

    override suspend fun getProductById(productId: Int): Product =
        storeDao.getProductById(productId)

    override suspend fun getProductCharacteristicById(productId: Int): ProductCharacteristic =
        storeDao.getProductCharacteristicById(productId)

    override suspend fun getCart(): Cart =
        storeDao.getCart()

    override suspend fun clearCart() =
        storeDao.clearCart()

    override suspend fun insertProduct(product: Product) =
        storeDao.insertProduct(product)

    override suspend fun insertProductCharacteristic(productCharacteristic: ProductCharacteristic) =
        storeDao.insertProductCharacteristic(productCharacteristic)

    override suspend fun insertOrUpdateCart(cart: Cart) =
        storeDao.insertOrUpdateCart(cart)

    override suspend fun deletePhoneFromCartById(productId: Int) {
        val cart = storeDao.getCart() ?: Cart()
        val updatedList =  cart.listOfProductsId.filterNot { it == productId }
        val updatedCart = cart.copy(listOfProductsId = updatedList)
        storeDao.insertOrUpdateCart(updatedCart)

    }
}