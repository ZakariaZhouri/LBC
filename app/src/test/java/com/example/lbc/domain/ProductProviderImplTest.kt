package com.example.lbc.domain

import com.example.lbc.data.api.model.ItemModel
import com.example.lbc.data.api.model.toListProductPresentation
import com.example.lbc.domain.providers.ProductProvider
import com.example.lbc.domain.usecase.ProductUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductProviderImplTest {

    @MockK
    lateinit var productProvider: ProductProvider
    private lateinit var provider: ProductUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        provider = ProductUseCaseImpl(productProvider)
    }


    @Test
    fun `provides items success`() = runTest {
        //When
        val response = Response.success(getListItems())
        coEvery { productProvider.getItems() } returns response
        provider.getProducts()
        val listEmits = provider.result.value
        //Then
        assertEquals(listEmits.data, getListItems().toListProductPresentation())
    }

    @Test
    fun `provides items success with empty list`() = runTest {
        //When
        val response = Response.success(emptyList<ItemModel>())
        coEvery { productProvider.getItems() } returns response
        provider.getProducts()
        val listEmits = provider.result.value
        //Then
        assertEquals(listEmits.data, emptyList<ItemModel>().toListProductPresentation())
    }


    @Test
    fun `provides items error`() = runTest {
        //When
        val response = Response.error<List<ItemModel>>(403, "Error".toResponseBody())
        coEvery { productProvider.getItems() } returns response
        provider.getProducts()
        val listEmits = provider.result.value
        //Then
        assertEquals(listEmits.networkError, 403)
    }

    private fun getListItems(): List<ItemModel> {
        return listOf(
            ItemModel(id = 1, title = "title1", url = "url1", thumbnailUrl = "thumbnailUrl1"),
            ItemModel(id = 2, title = "title2", url = "url2", thumbnailUrl = "thumbnailUrl2"),
        )
    }
}