package com.example.swipe_assign.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe_assign.api.adapters.product_adapter
import com.example.swipe_assign.api.apiInterface
import com.example.swipe_assign.api.models.product_modelItem
import com.example.swipe_assign.databinding.FragmentGetProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class get_product : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: product_adapter
    private lateinit var binding: FragmentGetProductBinding
    private var productList: List<product_modelItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetProductBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerView

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://app.getswipe.in/api/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)

        val retrofitData = retrofitBuilder.getProducts()

        retrofitData.enqueue(object : Callback<List<product_modelItem>?> {
            override fun onResponse(
                call: Call<List<product_modelItem>?>,
                response: Response<List<product_modelItem>?>
            ) {
                val responseBody = response.body()
                productList = responseBody ?: emptyList()

                myAdapter = product_adapter(requireContext() as Activity, productList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                // Call setupSearchView here to ensure myAdapter is initialized before onQueryTextChange
                setupSearchView()
            }

            override fun onFailure(call: Call<List<product_modelItem>?>, t: Throwable) {
                // Handle failure
            }
        })

        return binding.root
    }

    private fun setupSearchView() {
        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (::myAdapter.isInitialized) {
                    myAdapter.filterProducts(newText.orEmpty())
                }
                return true
            }
        })
    }
}
