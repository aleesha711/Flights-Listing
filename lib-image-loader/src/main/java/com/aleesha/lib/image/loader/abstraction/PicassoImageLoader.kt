package com.aleesha.lib.image.loader.abstraction

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject

class PicassoImageLoader @Inject constructor(private val picassoLazy: Lazy<Picasso>) : ImageLoader {

    override fun load(
        progress: ProgressBar?,
        imageView: ImageView,
        imageUrl: String,
        scalingType: ImageScaleType,
        @DrawableRes placeHolder: Int?,
        @DrawableRes errorDrawable: Int?
    ) {
        render(progress, imageView, imageUrl, scalingType, placeHolder, errorDrawable)
    }

    private fun render(
        progress: ProgressBar?,
        imageView: ImageView,
        imageUrl: String,
        scalingType: ImageScaleType,
        @DrawableRes placeHolder: Int?,
        @DrawableRes errorDrawable: Int?
    ) {
        when (scalingType) {
            ImageScaleType.CENTER_INSIDE -> {
                picassoLazy.get()?.let {
                    it.load(imageUrl)
                        .fit()
                        .centerInside()
                        .also { requestCreator ->
                            placeHolder?.let { requestCreator.placeholder(placeHolder) }
                            errorDrawable?.let { requestCreator.error(errorDrawable) }
                        }.into(
                            imageView,
                            object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                    progress?.visibility = View.GONE
                                }

                                override fun onError(e: java.lang.Exception?) {
                                    progress?.visibility = View.GONE
                                }
                            }
                        )
                }
            }
            ImageScaleType.CENTER_CROP -> {
                picassoLazy.get()?.let {
                    it.load(imageUrl)
                        .fit()
                        .centerCrop()
                        .also { requestCreator ->
                            placeHolder?.let { requestCreator.placeholder(placeHolder) }
                            errorDrawable?.let { requestCreator.error(errorDrawable) }
                        }.into(
                            imageView,
                            object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                    progress?.visibility = View.GONE
                                }

                                override fun onError(e: java.lang.Exception?) {
                                    progress?.visibility = View.GONE
                                }
                            }
                        )
                }
            }
        }
    }
}
