package com.example.lbc.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lbc.common.bind
import com.example.lbc.common.mapDistinct
import com.example.lbc.databinding.ProductFragmentBinding
import com.example.lbc.domain.model.ProductPresentation
import com.example.lbc.presentation.ui.adapter.ProductItemAdapter
import com.example.lbc.presentation.viewmodels.ProductViewModel
import org.koin.android.ext.android.inject

class ProductFragment : Fragment() {

    private lateinit var binding: ProductFragmentBinding
    private var productAdapter = ProductItemAdapter()
    private val viewModel: ProductViewModel by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.mapDistinct { state -> state.listProduct }.bind(this, ::displayList)
        viewModel.state.mapDistinct { state -> state.isSearching }.bind(this, ::displayLoading)
        viewModel.state.mapDistinct { state -> state.errorMessage }.bind(this, ::displayMessageError)
        binding.apply {
            productRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = productAdapter
            }
        }
        viewModel.handle(ProductViewModel.ProductAction.SubscribeToProduct)
        viewModel.handle(ProductViewModel.ProductAction.GetProduct)
    }

    private fun displayList(listProduct: List<ProductPresentation>) = binding.apply {
        productAdapter.updateProducts(listProduct)
    }

    private fun displayLoading(isLoading: Boolean) = binding.apply {
        progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun displayMessageError(message: String?) = binding.apply {
        errorMessage.text = message
    }


}