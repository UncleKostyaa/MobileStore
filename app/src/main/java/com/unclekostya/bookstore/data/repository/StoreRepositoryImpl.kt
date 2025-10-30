package com.unclekostya.bookstore.data.repository

import com.unclekostya.bookstore.data.local.dao.StoreDao
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic

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
}