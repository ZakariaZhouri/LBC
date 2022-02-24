package com.example.lbc.data.api

import com.example.lbc.data.api.model.ItemModel
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("technical-test.json")
    suspend fun getItems(): Response<List<ItemModel>>
}