package com.aleesha.lib.image.loader.di

import android.content.Context
import com.aleesha.lib.image.loader.abstraction.ImageLoader
import com.aleesha.lib.image.loader.abstraction.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ImageLoaderModule {

    @Provides
    @Singleton
    fun providePicasso(
        @ApplicationContext context: Context,
    ): Picasso {
        val picassoBuilder = Picasso.Builder(context)
        return picassoBuilder.build()
    }

    @Provides
    @Singleton
    fun providePicassoImageLoader(
        picasso: Lazy<Picasso>
    ): ImageLoader {
        return PicassoImageLoader(picasso)
    }
}