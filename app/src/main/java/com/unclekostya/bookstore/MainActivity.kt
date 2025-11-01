package com.unclekostya.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unclekostya.bookstore.data.local.database.ProductDatabase
import com.unclekostya.bookstore.data.repository.StoreRepository
import com.unclekostya.bookstore.data.repository.StoreRepositoryImpl
import com.unclekostya.bookstore.navigation.NavGraph
import com.unclekostya.bookstore.ui.theme.BookStoreTheme
import com.unclekostya.bookstore.ui.viewmodel.CartViewModel
import com.unclekostya.bookstore.ui.viewmodel.CartViewModelFactory
import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModel
import com.unclekostya.bookstore.ui.viewmodel.ProductsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookStoreTheme {
                // навигация
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                //db
                val db = ProductDatabase.getDatabase(LocalContext.current)
                val dao = db.productDao()
                val repository = StoreRepositoryImpl(dao)

                //viewModels
                val productViewModel: ProductsViewModel = viewModel(factory = ProductsViewModelFactory(repository = repository))
                val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(repository = repository))
                Scaffold(
                    topBar = {
                        TopBar(navController = navController)
                    }

                ) { innerPadding ->
                    Surface(modifier = Modifier
                        .padding(innerPadding
                        )) {
                        NavGraph(
                            navController = navController,
                            productViewModel = productViewModel,
                            cartViewModel = cartViewModel
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Box(
                modifier = modifier
                    .padding(end = 16.dp)
                    .fillMaxSize()
                ) {
                Text(
                    text = "Tech Store",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .align(Alignment.Center)

                )
                IconButton(
                    onClick = {
                        navController.navigate("cart") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = modifier
                        .align(Alignment.CenterEnd)

                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart_white),
                        contentDescription = "cart",
                        modifier = modifier
                            .size(38.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        )
    )
}