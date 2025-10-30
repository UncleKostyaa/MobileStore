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

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "store"
    ) {
        composable("store") {
            StoreScreen(

            )
        }
        composable("cart") {
            CartScreen(

            )
        }
        composable(
            route ="product/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.IntType})
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductScreen(
                productId = productId
            )

        }
    }
}