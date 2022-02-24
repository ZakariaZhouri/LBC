package com.example.lbc.domain.usecase

import com.example.lbc.common.Resource
import com.example.lbc.domain.model.ProductPresentation


interface ProductUseCase : MainUseCase<Resource<List<ProductPresentation>>> {
    suspend fun getProducts()
}