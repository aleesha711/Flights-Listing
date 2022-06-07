package com.aleesha.lib.image.loader.abstraction

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes

interface ImageLoader {
    fun load(
        progress: ProgressBar?,
        imageView: ImageView,
        imageUrl: String,
        scalingType: ImageScaleType,
        @DrawableRes placeHolder: Int? = null,
        @DrawableRes errorDrawable: Int? = null
    )
}
