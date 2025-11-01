package com.unclekostya.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unclekostya.bookstore.ui.cart.CartScreen
import com.unclekostya.bookstore.ui.product.ProductScreen
import com.unclekostya.bookstore.ui.store.StoreScreen
import com.unclekostya.bookstore.ui.viewmodel.CartViewModel
import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    productViewModel: ProductsViewModel,
    cartViewModel: CartViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "store"
    ) {
        composable("store") {
            StoreScreen(
                productViewModel = productViewModel,
                navController = navController
            )
        }
        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel
            )
        }
        composable(
            route ="product/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductScreen(
                productId = productId,
                productViewModel = productViewModel
            )

        }
    }
}