package com.example.mvvmproducts.AllProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lec5.Product
import com.example.mvvmproducts.Model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: Repo) : ViewModel() {


    private val _productList = MutableLiveData<ApiState>()
    val productList: LiveData<ApiState> = _productList

//     fun getProducts() {
//         _productList.value = ApiState.Loading
//
//
//
//
//         }
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val products = repo.getProducts()
//                _productList.postValue(products)
//            } catch (e: Exception) {
//
//            }
//        }
//    }
//fun getProducts() =viewModelScope.launch{
//    val products = repo.getProducts()
//    _productList.postValue(products)
//    _
//}
    fun getProducts() {

        _productList.value = ApiState.Loading


        viewModelScope.launch {
            repo.getProducts()
                .catch { e ->

                    _productList.value = ApiState.Failure(e.message ?: "Unknown Error")
                }
                .collect { products ->

                    _productList.value = ApiState.Success(products)
                }
        }
    }

    fun insertToFav(product : Product){
       viewModelScope.launch(Dispatchers.IO) {
         repo.insertProduct(product)
       }
    }
}