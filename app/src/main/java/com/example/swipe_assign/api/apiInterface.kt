package com.example.swipe_assign.api

import com.example.swipe_assign.api.models.product_modelItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface apiInterface {

    @GET("get")
    fun getProducts(): Call<List<product_modelItem>>

    @POST("add")
    fun addProduct(@Body product: product_modelItem): Call<product_modelItem>
}