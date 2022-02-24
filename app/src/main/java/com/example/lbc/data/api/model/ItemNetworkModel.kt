
package com.example.lbc.data.api.model

import com.example.lbc.domain.model.ProductPresentation
import com.fasterxml.jackson.annotation.JsonProperty

data class ItemModel(
    @JsonProperty("id") var id: Int,
    @JsonProperty("title") var title: String,
    @JsonProperty("url") var url: String,
    @JsonProperty("thumbnailUrl") var thumbnailUrl: String
)

fun ItemModel.toProductPresentation() = ProductPresentation(id, title, url, thumbnailUrl)
fun List<ItemModel>.toListProductPresentation() = this.map { it.toProductPresentation() }




