package com.unclekostya.bookstore.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
        Button(
            onClick = {
                cartViewModel.clearCart()
            }
        ) {
            Text(
                text = "Clean cart"
            )
        }
        productsInCart.forEach { item ->
            Card(
                modifier = Modifier
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context = LocalContext.current)
                                .data(data = "file:///android_asset/${item.productImageUrl}")
                                .build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 24.dp, start = 20.dp)
                            .align(Alignment.TopStart)
                    )
                    Text(
                        text = item.productName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp)
                            .fillMaxWidth()

                    )
                    Text(
                        text = item.productCost,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 42.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
                    Button(
                        onClick = {
                            cartViewModel.removeItemFromCart(item.productId)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = "Remove from cart"
                        )
                    }
                }
            }

        }
    }

}