package com.unclekostya.bookstore.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.ui.viewmodel.CartViewModel
import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    product: ProductsViewModel
) {
    val cartItems = cartViewModel.cart.value
    var productsInCart by remember { mutableStateOf<List<Product>>(emptyList()) }
    LaunchedEffect(cartItems) {
        cartItems?.let {
            val loadedProducts = withContext(Dispatchers.IO) {
                it.listOfProductsId.mapNotNull { id ->
                    try {
                        product.fetchProductById(id)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            productsInCart = loadedProducts
        }
    }
    Column {
        productsInCart.forEach { item ->
            Text(
                text = item.productName
            )

        }
    }

}