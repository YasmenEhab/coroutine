package com.example.mvvmproducts.FavProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lec5.Product
import com.example.mvvmproducts.Model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllFavProductsViewModel (private val repo: Repo) : ViewModel()   {



    private val _productList = MutableStateFlow<List<Product>>(emptyList())

    val productList: StateFlow<List<Product>> = _productList


    fun getAllFav(){
        viewModelScope.launch(Dispatchers.IO) {
           val products = repo.getAllFav().collect{
               _productList.value = it
           }
           // _productList.postValue(products)

        }
    }

    fun delete(product : Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(product)
        }
    }

}

