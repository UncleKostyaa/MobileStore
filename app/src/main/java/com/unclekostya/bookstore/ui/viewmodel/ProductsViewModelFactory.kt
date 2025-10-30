package com.unclekostya.bookstore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unclekostya.bookstore.data.repository.StoreRepository

class ProductsViewModelFactory(
    private val repository: StoreRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}