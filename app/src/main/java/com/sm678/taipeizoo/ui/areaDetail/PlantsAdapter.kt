package com.sm678.taipeizoo.ui.areaDetail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sm678.taipeizoo.R
import com.sm678.taipeizoo.databinding.ListItemAreaHeaderBinding
import com.sm678.taipeizoo.databinding.ListItemPlaceholderBinding
import com.sm678.taipeizoo.databinding.ListItemPlantBinding
import com.sm678.taipeizoo.model.*
import me.samlss.broccoli.Broccoli
import me.samlss.broccoli.BroccoliGradientDrawable
import me.samlss.broccoli.PlaceholderParameter

private const val ITEM_VIEW_TYPE_AREA_HEADER = 0
private const val ITEM_VIEW_TYPE_TEXT_HEADER = 1
private const val ITEM_VIEW_TYPE_PLANT = 2
private const val ITEM_VIEW_TYPE_PLACEHOLDER = 3

class PlantsAdapter(private val broccoliViewMap: MutableMap<View, Broccoli>,
                    private val clickListener: PlantsListener,
                    private val openUrlListener: OpenUrlListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(PlantsDiffCallback()) {

    private val placeholderList = getPlaceholderList(6)

    fun addAreaHeaderAndPlaceHolderList(list: List<DataItem>) {
        val items = list + listOf(TextHeader) + placeholderList
        submitList(items)
    }

    fun addHeaderAndSubmitList(areaHeader: List<AreaHeader>, list: List<Plant>) {
        val items = areaHeader + listOf(TextHeader) + list
        submitList(items)
    }

    private fun getPlaceholderList(num: Int): List<DataItem> {
        var items = listOf(PlantPlaceholder)
        var count = 1
        while (count < num) {
            count++
            items = items + listOf(PlantPlaceholder)
        }
        return items
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AreaDetailViewHolder -> {
                val areaHeader = getItem(position) as AreaHeader
                holder.bind(areaHeader, openUrlListener)
            }
            is PlantViewHolder -> {
                val plant = getItem(position) as Plant
                holder.bind(plant, clickListener)
            }
            is PlaceholderViewHolder -> {
                var broccoli: Broccoli? = broccoliViewMap[holder.binding.root]
                if (broccoli == null) {
                    broccoli = Broccoli()
                    broccoliViewMap[holder.binding.root] = broccoli
                }
                holder.bind(broccoli)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is PlaceholderViewHolder) {
            broccoliViewMap[holder.binding.root]?.removeAllPlaceholders()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_AREA_HEADER -> AreaDetailViewHolder.from(parent)
            ITEM_VIEW_TYPE_TEXT_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_PLANT -> PlantViewHolder.from(parent)
            ITEM_VIEW_TYPE_PLACEHOLDER -> PlaceholderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AreaHeader -> ITEM_VIEW_TYPE_AREA_HEADER
            is TextHeader -> ITEM_VIEW_TYPE_TEXT_HEADER
            is Plant -> ITEM_VIEW_TYPE_PLANT
            is PlantPlaceholder -> ITEM_VIEW_TYPE_PLACEHOLDER
        }
    }

    //area detail header
    class AreaDetailViewHolder(val binding: ListItemAreaHeaderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AreaHeader, _clickListener: OpenUrlListener) {
            binding.areaHeader = item
            binding.openUrlListener = _clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AreaDetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAreaHeaderBinding.inflate(layoutInflater, parent, false)
                return AreaDetailViewHolder(binding)
            }
        }
    }

    //plants
    class PlantViewHolder private constructor(val binding: ListItemPlantBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Plant, _clickListener: PlantsListener) {
            binding.plant = item
            binding.clickListener = _clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlantViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlantBinding.inflate(layoutInflater, parent, false)
                return PlantViewHolder(binding)
            }
        }
    }

    //text header
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    //placeholder header
    class PlaceholderViewHolder(val binding: ListItemPlaceholderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(broccoli: Broccoli) {
            broccoli.removeAllPlaceholders()
            broccoli.addPlaceholder(
                PlaceholderParameter.Builder()
                    .setView(binding.image)
                    .setDrawable(BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                        Color.parseColor("#CCCCCC"), 0F, 1000, LinearInterpolator()))
                    .build()
            )
            val titleAnimation: Animation = ScaleAnimation(0.3f, 1f, 1f, 1f). apply {
                duration = 400
                repeatMode = Animation.REVERSE
                repeatCount = Animation.INFINITE
            }
            broccoli.addPlaceholder(
                PlaceholderParameter.Builder()
                    .setView(binding.titleText)
                    .setAnimation(titleAnimation)
                    .setDrawable(GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 25f
                        setColor(Color.parseColor("#DDDDDD"))
                    })
                    .build()
            )
            val infoAnimation: Animation = ScaleAnimation(0.35f, 1f, 1f, 1f). apply {
                duration = 350
                repeatMode = Animation.REVERSE
                repeatCount = Animation.INFINITE
            }
            broccoli.addPlaceholder(
                PlaceholderParameter.Builder()
                    .setView(binding.infoText)
                    .setAnimation(infoAnimation)
                    .setDrawable(GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 25f
                        setColor(Color.parseColor("#DDDDDD"))
                    })
                    .build()
            )
            broccoli.show()
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlaceholderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlaceholderBinding.inflate(layoutInflater, parent, false)
                return PlaceholderViewHolder(binding)
            }
        }
    }

}


class PlantsDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


class PlantsListener(val clickListener: (plant: Plant) -> Unit) {
    fun onClick(plant: Plant) = clickListener(plant)
}

class OpenUrlListener(val openUrlListener: (url: String) -> Unit) {
    fun openUrl(url: String) = openUrlListener(url)
}
