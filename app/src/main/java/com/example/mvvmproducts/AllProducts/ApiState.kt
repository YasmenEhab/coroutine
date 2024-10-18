package com.example.mvvmproducts.AllProducts

import com.example.lec5.Product

sealed class ApiState {
    data class Success(val products: List<Product>) : ApiState()
    data class Failure(val message: String) : ApiState()
    object Loading : ApiState()
}

//sealed class ApiState {
//    object Loading : ApiState()  // Loading state
//    data class Success(val products: List<Product>) : ApiState()  // Success state with products
//    data class Error(val message: String) : ApiState()  // Error state with error message
//}

