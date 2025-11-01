    package com.unclekostya.bookstore.ui.store

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
    import androidx.compose.foundation.lazy.grid.items
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalConfiguration
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModel

    @Composable
    fun StoreScreen(
        productViewModel: ProductsViewModel,
        navController: NavHostController
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val context = LocalContext.current

        val columns = when {
            screenWidth < 600.dp -> 2
            screenWidth < 900.dp -> 3
            else -> 4
        }
        LaunchedEffect(Unit) {
            productViewModel.getAll()
        }
        val allProducts = productViewModel.products.value
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                ,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allProducts) { products ->
                ProductCard(
                    products = products,
                    context = context,
                    navController = navController)
            }
        }
    }