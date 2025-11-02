package com.unclekostya.bookstore.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel (
    private val repository: StoreRepository
) : ViewModel() {
    val cartStatus = mutableStateOf("Cart is loading...")

    val cart = mutableStateOf<Cart?>(null)

    fun gCart() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    cart.value = repository.getCart()
                }
            } catch (e: Exception) {
                cartStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                     cart.value = Cart()
                }
            } catch (e: Exception) {
                cartStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun addItemToCart(
        productId: Int
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    var currentCart = repository.getCart()
                    if (currentCart == null) {
                        currentCart = Cart()
                    }
                    if (!currentCart.listOfProductsId.contains(productId)) {
                        val updatedList = currentCart.listOfProductsId + productId
                        currentCart = currentCart.copy(listOfProductsId = updatedList)
                    }
                    repository.insertOrUpdateCart(currentCart)
                    withContext(Dispatchers.Main) {
                        cart.value = currentCart
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeItemFromCart(
        productId: Int
    )  {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deletePhoneFromCartById(productId)
                    val updatedCart = repository.getCart()
                    withContext(Dispatchers.Main) {
                        cart.value = updatedCart
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}