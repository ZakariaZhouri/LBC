package com.example.lbc.domain.usecase

import com.example.lbc.common.Resource
import com.example.lbc.data.api.model.toListProductPresentation
import com.example.lbc.domain.model.ProductPresentation
import com.example.lbc.domain.providers.ProductProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductUseCaseImpl(private val productProvider: ProductProvider) : ProductUseCase {
    private val _result = MutableStateFlow<Resource<List<ProductPresentation>>>(Resource.Loading())
    override val result = _result.asStateFlow()

    override suspend fun getProducts() {
        val response = productProvider.getItems()
        if (response.isSuccessful) {
            response.body()?.let { listProduct ->
                _result.value = Resource.Success(data = listProduct.toListProductPresentation())
            }
        } else {
            _result.value = Resource.NetworkError(response.code())
        }
    }
}