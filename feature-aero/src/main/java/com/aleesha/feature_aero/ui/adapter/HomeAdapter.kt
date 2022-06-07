package com.aleesha.feature_aero.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.feature_aero.databinding.ItemListBinding
import com.aleesha.feature_aero.ui.adapter.viewholder.AirlinesListViewHolder

class HomeAdapter(private val context: Context, private val onItemClick: (Int) -> Unit, private val onFavoriteClick: (Int) -> Unit) : ListAdapter<AirlinesItem, AirlinesListViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlinesListViewHolder {
        return AirlinesListViewHolder(
            context,
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick, onFavoriteClick
        )
    }

    override fun onBindViewHolder(holder: AirlinesListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bindType(currentItem)
    }

    fun getCurrentItem(position: Int): AirlinesItem {
        return super.getItem(position)
    }

    fun updateItem(name: String) {
        val item = currentList.first {
            it.name == name
        }
        item.isFavorite = false
        val updateIndex = currentList.indexOf(item)
        notifyItemChanged(updateIndex)
    }

    private class DiffCallback : DiffUtil.ItemCallback<AirlinesItem>() {
        override fun areItemsTheSame(oldItem: AirlinesItem, newItem: AirlinesItem) = oldItem == newItem
        override fun areContentsTheSame(oldItem: AirlinesItem, newItem: AirlinesItem) = oldItem.code == newItem.code && oldItem.name == newItem.name
    }
}
