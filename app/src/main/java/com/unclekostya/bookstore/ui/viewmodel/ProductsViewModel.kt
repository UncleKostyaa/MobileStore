package com.unclekostya.bookstore.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic
import com.unclekostya.bookstore.data.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val repository: StoreRepository
): ViewModel() {
    val product = mutableStateOf("Loading products")
    val products = mutableStateOf<List<Product>>(emptyList())
    val productCharacteristic = mutableStateOf("Loading info....")
    val productCharacteristics = mutableStateOf<ProductCharacteristic?>(null)
    val productMainInfo = mutableStateOf<Product?>(null)
    val productMainInfoText = mutableStateOf("Checking....")
    fun getAll() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    products.value = repository.getAllProducts()
                }
            } catch (e: Exception) {
                product.value = "Error: ${e.message}"
            }
        }
    }

    fun getProductInfoById(
        productId: Int
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    productCharacteristics.value = repository.getProductCharacteristicById(productId = productId)
                }
            } catch (e: Exception) {
                productCharacteristic.value = "Error: ${e.message}"
            }
        }

    }
    fun getProductById(productId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    productMainInfo.value = repository.getProductById(productId = productId)
                }
            } catch (e: Exception) {
                productMainInfoText.value = "Error: ${e.message}"
            }
        }
    }
    suspend fun fetchProductById(productId: Int): Product? {
            return try {
                repository.getProductById(productId)
            } catch (e: Exception) {
                null
            }
    }
}
