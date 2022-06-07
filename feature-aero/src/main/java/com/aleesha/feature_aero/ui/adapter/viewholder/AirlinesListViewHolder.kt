package com.aleesha.feature_aero.ui.adapter.viewholder

import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.feature_aero.R
import com.aleesha.feature_aero.databinding.ItemListBinding
import com.aleesha.lib.image.loader.abstraction.ImageLoader
import com.aleesha.lib.image.loader.abstraction.ImageScaleType
import com.aleesha.lib.image.loader.di.ImageLoaderEntryPointAccessor

class AirlinesListViewHolder(private val context: Context, binding: ItemListBinding, private val onItemClick: (Int) -> Unit, private val onFavoriteClick: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val textViewTitle: TextView = binding.title
    private val textViewDescription: TextView = binding.code
    private val imageView: ImageView = binding.icon
    private val favoriteImageView: ImageView = binding.markFavorite
    private val progressBar: ProgressBar = binding.progress
    private val imageLoader: ImageLoader = ImageLoaderEntryPointAccessor.access(context).imageLoaderBareBoneProvider()

    init {
        itemView.setOnClickListener { onItemClick.invoke(absoluteAdapterPosition) }

        favoriteImageView.setOnClickListener { onFavoriteClick.invoke(absoluteAdapterPosition) }
    }

    fun bindType(airlinesItem: AirlinesItem) {
        textViewTitle.text = airlinesItem.name
        textViewDescription.text = airlinesItem.code
        imageLoader.load(
            progressBar,
            imageView,
            airlinesItem.site + airlinesItem.logoURL,
            ImageScaleType.CENTER_INSIDE,
            androidx.appcompat.R.color.abc_color_highlight_material,
            R.drawable.ic_placeholder_airline
        )

        if (airlinesItem.isFavorite) {
            favoriteImageView.setColorFilter(
                ContextCompat.getColor(
                    context,
                    com.google.android.material.R.color.design_default_color_primary
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        } else {
            favoriteImageView.setColorFilter(
                ContextCompat.getColor(
                    context,
                    com.google.android.material.R.color.abc_color_highlight_material
                ),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }
}
