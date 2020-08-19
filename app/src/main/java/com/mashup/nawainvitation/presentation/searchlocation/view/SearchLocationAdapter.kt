package com.mashup.nawainvitation.presentation.searchlocation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.databinding.ItemSearchLocationBinding
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents

class SearchLocationAdapter(private val click: (position: Int) -> Unit) :
    RecyclerView.Adapter<SearchLocationAdapter.MapHolder>() {

    private val itemAddress = mutableListOf<Documents>()

    fun setData(data: List<Documents>) {
        data.let {
            itemAddress.clear()
            itemAddress.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun resetData() : MutableList<Documents> {
        itemAddress.clear()
        notifyDataSetChanged()
        return itemAddress
    }

    fun getItem(position: Int) = itemAddress[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapHolder {
        val binding: ItemSearchLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search_location, parent, false
        )
        return MapHolder(binding, click)
    }

    override fun getItemCount(): Int = itemAddress.size

    override fun onBindViewHolder(holder: MapHolder, position: Int) {
        holder.bind(itemAddress[position])
    }

    class MapHolder(private val binding: ItemSearchLocationBinding, click: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { click(adapterPosition) }
        }

        fun bind(bindingItem: Documents) {
            binding.model = bindingItem
        }
    }

}