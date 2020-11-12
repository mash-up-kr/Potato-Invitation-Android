package com.mashup.nawainvitation.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.presentation.main.model.TypeItem

class TypePagerAdapter : RecyclerView.Adapter<TypePagerAdapter.PagerViewHolder>() {

    private val items = mutableListOf<TypeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_type_select, parent, false)
        )


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun replaceAll(items: List<TypeItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getIndexFromTemplateId(templateId: Int): Int {
        items.mapIndexed { index, typeItem ->
            if (templateId == typeItem.templateId) {
                return index
            }
        }
        return 0
    }

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.tv_item_type_title)
        private val image = itemView.findViewById<ImageView>(R.id.iv_item_type)
        private val background = itemView.findViewById<ImageView>(R.id.iv_item_type)

        fun bind(item: TypeItem) {
            title.text = item.title

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(image)

            Glide.with(itemView.context)
                .load(item.backgroundImageUrl)
                .into(background)
        }
    }

}