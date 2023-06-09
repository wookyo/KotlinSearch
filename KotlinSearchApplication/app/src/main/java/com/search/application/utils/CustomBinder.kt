package com.v1.application.utils

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.search.application.R


object CustomBinder {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setThumbnailImage(imageView: ImageView,
                          url: String?) {
        url?.let {
            val context = imageView.context ?: return
            val options = RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(context)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("iszzim")
    fun isZzim(view: ImageButton, iszzim: Boolean) {
        view.setImageResource(
            if (iszzim) R.drawable.ic_favor_on else R.drawable.ic_favor_off
        )
    }

}