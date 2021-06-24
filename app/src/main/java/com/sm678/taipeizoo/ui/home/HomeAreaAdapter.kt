package com.sm678.taipeizoo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sm678.taipeizoo.databinding.ListItemHomeAreaBinding
import com.sm678.taipeizoo.model.Area


class HomeAreaAdapter(val clickListener: HomeAreaListener) :
    ListAdapter<Area, HomeAreaAdapter.ViewHolder>(HomeAreaDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemHomeAreaBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Area, _clickListener: HomeAreaListener) {
            binding.apply {
                area = item
                clickListener = _clickListener
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHomeAreaBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class HomeAreaDiffCallback : DiffUtil.ItemCallback<Area>() {

    override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem == newItem
    }
}


class HomeAreaListener(val clickListener: (area: Area) -> Unit) {
    fun onClick(area: Area) = clickListener(area)
}
