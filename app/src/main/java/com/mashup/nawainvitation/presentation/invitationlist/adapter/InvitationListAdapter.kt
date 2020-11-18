package com.mashup.nawainvitation.presentation.invitationlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseViewHolder
import com.mashup.nawainvitation.databinding.ItemHeaderInvitationListBinding
import com.mashup.nawainvitation.databinding.ItemInvitationListBinding
import com.mashup.nawainvitation.presentation.invitationlist.model.InvitationListItem
import kotlinx.android.synthetic.main.item_invitation_list.view.*

class InvitationListAdapter(
    private var item: List<InvitationListItem>, private val listener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(parent)
            TYPE_INVITATION -> InvitationListViewHolder(parent, listener)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        item[position].apply {
            when (holder) {
                is HeaderViewHolder -> {
                    holder.bind(this)
                }
                is InvitationListViewHolder -> holder.bind(this)
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (item[position].viewType == 0) TYPE_HEADER
        else TYPE_INVITATION
    }

    fun getItemWithPosition(position: Int) = item[position]


    class HeaderViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemHeaderInvitationListBinding, InvitationListItem>(
            parent,
            R.layout.item_header_invitation_list
        ) {

        override fun bind(data: InvitationListItem) {
            binding.model = data
            val params = binding.root.layoutParams as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true
            binding.root.layoutParams = params
        }
    }

    class InvitationListViewHolder(parent: ViewGroup, listener: (position: Int) -> Unit) :
        BaseViewHolder<ItemInvitationListBinding, InvitationListItem>(
            parent,
            R.layout.item_invitation_list
        ) {

        init {
            itemView.setOnClickListener { listener(adapterPosition) }
        }

        override fun bind(data: InvitationListItem) {
            binding.model = data

            Glide.with(itemView.context)
                .load(data.templateImage)
                .into(itemView.ivTypeIcon)
        }
    }

    companion object {
        const val SPAN_SIZE = 2
        const val TYPE_HEADER = 0
        const val TYPE_INVITATION = 1
    }

}
