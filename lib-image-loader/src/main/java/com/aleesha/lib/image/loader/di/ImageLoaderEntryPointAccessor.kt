package com.aleesha.lib.image.loader.di

import android.content.Context
import dagger.hilt.android.EntryPointAccessors

object ImageLoaderEntryPointAccessor {
    fun access(context: Context): ImageLoaderEntryPoint {
        return EntryPointAccessors.fromApplication(context, ImageLoaderEntryPoint::class.java)
    }
}

