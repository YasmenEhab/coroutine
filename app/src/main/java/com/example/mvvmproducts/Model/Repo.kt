package com.example.mvvmproducts.Model

import com.example.lec5.Product
import com.example.mvvmproducts.DB.ProductLocalDataSource
import com.example.mvvmproducts.Network.RetrofitHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repo(private val localSource : ProductLocalDataSource) {

    companion object {
        @Volatile
        private var INSTANCE: Repo? = null

        fun getInstance(localDataSource: ProductLocalDataSource): Repo? {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Repo(localDataSource)
                INSTANCE
            }
        }
    }

    suspend fun getProducts(): Flow<List<Product>>  {
        val serviceObj = RetrofitHelper.service
        val result = serviceObj.getProducts()
        val productList = result.products
        //return productList

        return flow {emit(productList)}
    }

    suspend fun insertProduct(product: Product) {
            localSource.insertProduct(product)
    }

    suspend fun getAllFav(): Flow<List<Product>> {
        return localSource.getAllFav()
    }

    suspend fun delete(product: Product) {
        localSource.delete(product)
    }
}