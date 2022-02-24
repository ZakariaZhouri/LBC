package com.example.lbc.presentation

import com.example.lbc.common.AppDispatchers
import com.example.lbc.domain.usecase.ProductUseCase
import com.example.lbc.presentation.viewmodels.ProductViewModel
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProductViewModelTest {
    private val initialState = ProductViewModel.ItemState()
    private val productUseCase: ProductUseCase = mockk()

    @ExperimentalCoroutinesApi
    val testCoroutineDispatcher = UnconfinedTestDispatcher()


    private lateinit var viewModel: ProductViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val dispatchers = AppDispatchers(testCoroutineDispatcher, testCoroutineDispatcher, testCoroutineDispatcher)
        viewModel = ProductViewModel(
            dispatchers,
            productUseCase,
            initialState
        )
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `launch Get product action`() {
        // When
        viewModel.handle(ProductViewModel.ProductAction.GetProduct)
        // THEN
        coVerify { productUseCase.getProducts() }
    }

}