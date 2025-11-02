package com.unclekostya.bookstore.ui.product

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.unclekostya.bookstore.R
import com.unclekostya.bookstore.ui.viewmodel.CartViewModel
import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModel

@Composable
fun ProductScreen(
    productId: Int,
    productViewModel: ProductsViewModel,
    cartViewModel: CartViewModel
) {
    LaunchedEffect(Unit) {
        productViewModel.getProductById(productId)
        productViewModel.getProductInfoById(productId)
    }
    val getPhoneInformation = productViewModel.productCharacteristics
    val getPhone = productViewModel.productMainInfo
    val listOfCharacteristics = listOf(
        "Phone  model",
        "OS",
        "Display",
        "Refresh rate",
        "Processor",
        "Storage",
        "RAM",
        "Battery capacity"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(color = Color.White )
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context = LocalContext.current)
                    .data("file:///android_asset/${getPhone.value?.productImageUrl}")
                    .build()

            ),
            contentDescription =  null,
            modifier = Modifier
                .size(156.dp)
                .padding(start = 30.dp,top = 20.dp),
            alignment = Alignment.TopStart
        )
        Text(
            text = getPhone.value?.productName ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(200.dp),
        )
        IconButton(
            onClick = {
                cartViewModel.addItemToCart(getPhone.value?.productId ?: 0)
            },
            modifier = Modifier
                .padding(top = 120.dp, end = 18.dp)
                .align(Alignment.TopEnd)
                .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                .width(175.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.white_add_filled),
                contentDescription = "add to cart",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 12.dp)
                    .size(32.dp)
            )
            Text(
                text = "Add to cart",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, end = 30.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 40.dp)
        ) {
            listOfCharacteristics.forEach { item ->
                CharacteristicsTable(
                    characteristic = item,
                    model = getPhoneInformation
                )
            }
        }
    }
}