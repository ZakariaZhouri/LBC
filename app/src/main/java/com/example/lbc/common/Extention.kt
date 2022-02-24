package com.example.lbc.common

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

fun ImageView.loadImage(imagePath: String) {
    val theImage = GlideUrl(
        imagePath, LazyHeaders.Builder()
            .addHeader("User-Agent", "5")
            .build()
    )
    Glide.with(this).load(theImage)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this);
}

fun <T, R> LiveData<T>.mapDistinct(function: (T) -> R): LiveData<R> = this.map(function).distinctUntilChanged()
fun <T> LiveData<T>.bind(owner: LifecycleOwner, function: (T) -> Unit) {
    this.observe(owner, Observer { function(it) })
}

