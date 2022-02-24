package com.example.lbc.data.repository

import com.example.lbc.data.api.Service
import com.example.lbc.domain.providers.ProductProvider

class ServiceImpl(private val service: Service) : ProductProvider {
    override suspend fun getItems() = service.getItems()
}