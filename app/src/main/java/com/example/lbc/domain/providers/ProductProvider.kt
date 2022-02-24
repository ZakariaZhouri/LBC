package com.example.lbc.domain.providers

import com.example.lbc.data.api.model.ItemModel
import retrofit2.Response

interface ProductProvider {
    suspend fun getItems(): Response<List<ItemModel>>
}