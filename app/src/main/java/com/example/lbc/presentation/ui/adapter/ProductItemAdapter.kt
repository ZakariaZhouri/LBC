package com.example.lbc.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lbc.common.loadImage
import com.example.lbc.databinding.ProductItemBinding
import com.example.lbc.domain.model.ProductPresentation

class ProductItemAdapter : RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>() {

    private var listProduct: List<ProductPresentation> = emptyList()

    fun updateProducts(list: List<ProductPresentation>) {
        listProduct = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ProductViewHolder(ProductItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProduct[position])
    }

    override fun getItemCount() = listProduct.size

    class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(productPresentation: ProductPresentation) = with(binding) {
            productImage.loadImage(productPresentation.thumbnailUrl)
            productTitle.text = productPresentation.title
        }
    }
}