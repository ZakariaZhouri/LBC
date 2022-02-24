package com.example.lbc.presentation.viewmodels

import android.util.Log
import com.example.lbc.common.AppDispatchers
import com.example.lbc.common.Resource
import com.example.lbc.domain.model.ProductPresentation
import com.example.lbc.domain.usecase.ProductUseCase
import com.example.lbc.presentation.BaseAction
import com.example.lbc.presentation.BaseState
import com.example.lbc.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductViewModel(
    itemDispatcher: AppDispatchers, private val mainUseCase: ProductUseCase,
    initialState: ItemState = ItemState(true, listOf())
) : BaseViewModel<ProductViewModel.ProductAction, ProductViewModel.ItemState>(itemDispatcher, initialState) {
    private var productJob: Job? = null

    sealed class ProductAction : BaseAction {
        object SubscribeToProduct : ProductAction()
        object GetProduct : ProductAction()
    }

    data class ItemState(
        val isSearching: Boolean = false,
        val listProduct: List<ProductPresentation> = emptyList(),
        val errorMessage: String? = null
    ) : BaseState

    override suspend fun onHandle(action: ProductAction) {
        when (action) {
            ProductAction.SubscribeToProduct -> subscribeToProduct()
            is ProductAction.GetProduct -> getItem()
        }
    }

    private fun subscribeToProduct() {
        productJob = mainUseCase.result.onEach { result ->
            when (result) {
                is Resource.Loading -> updateState { state -> state.copy(isSearching = true) }
                is Resource.Success -> updateState { state -> state.copy(isSearching = false, listProduct = result.data!!) }
                is Resource.NetworkError -> updateState { state -> state.copy(isSearching = false, errorMessage = "") }
            }
        }.launchIn(this)
    }

    private suspend fun getItem() {
        mainUseCase.getProducts()
    }

    fun test() {

    }

    override fun onCleared() {
        productJob?.cancel()
        super.onCleared()
    }
}