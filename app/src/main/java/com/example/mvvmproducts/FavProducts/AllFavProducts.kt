package com.example.mvvmproducts.FavProducts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_kotlin.OnProductFavClickListener
import com.example.lec5.DB.ProductDao
import com.example.lec5.DB.ProductDatabase
import com.example.lec5.Product
import com.example.mvvmproducts.AllProducts.AllProductsViewModel
import com.example.mvvmproducts.AllProducts.AllProductsViewModelFactory
import com.example.mvvmproducts.DB.ProductLocalDataSource
import com.example.mvvmproducts.Model.Repo
import com.example.mvvmproducts.R
import com.example.workmanager.ProductAdapter
import kotlinx.coroutines.launch

class AllFavProducts : AppCompatActivity() , OnProductFavClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: FavProductAdapter
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var viewModel: AllFavProductsViewModel
    lateinit var products: List<Product>
    lateinit var allFavProductsViewModelFactory: AllFavProductsViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_fav_products)



        recyclerView = findViewById(R.id.rv_fav)

        val myProductDao : ProductDao = ProductDatabase.getInstance(this).getproductDao()
        val localDataSource = ProductLocalDataSource(myProductDao)


        allFavProductsViewModelFactory = AllFavProductsViewModelFactory(Repo.getInstance(localDataSource))
        viewModel = ViewModelProvider(this ,allFavProductsViewModelFactory ).get(AllFavProductsViewModel::class.java)
        viewModel.getAllFav()

        mAdapter = FavProductAdapter(this)
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    lifecycleScope.launch {  viewModel.productList.collect{ products ->
        mAdapter.submitList(products)
    }}




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onProductClick(product: Product) {
        viewModel.delete(product)
        Toast.makeText(this ,"Product Deleted ", Toast.LENGTH_LONG).show()
    }
}