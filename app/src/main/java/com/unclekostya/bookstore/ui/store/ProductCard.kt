package com.unclekostya.bookstore.ui.store

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.unclekostya.bookstore.R
import com.unclekostya.bookstore.data.local.entity.Product

@Composable
fun ProductCard(
    products: Product,
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.7f),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(6.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data("file:///android_asset/${products.productImageUrl}")
                        .build()
                ),
                contentDescription = products.productName,
                modifier = modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = products.productName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier

            )
            Text(
                text = products.productCost,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}