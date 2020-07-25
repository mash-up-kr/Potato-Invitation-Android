package com.mashup.patatoinvitation.presentation.imagepicker

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.presentation.imagepicker.data.ImageClickData
import io.reactivex.subjects.PublishSubject
import java.lang.Exception

class ImagePickerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val MAX_IMAGE_COUNT = 5

    private val TYPE_IMAGE = 0
    private val TYPE_PLUS = 1

    private var _data: List<Uri> = arrayListOf()

    private val _itemClickSubject = PublishSubject.create<ImageClickData>()
    val itemClickSubject: PublishSubject<ImageClickData>
        get() = _itemClickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> ImagePickerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_image_picker, parent, false)
            )
            TYPE_PLUS -> PlusImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_image_picker_plus, parent, false)
            )
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return if (_data.size >= MAX_IMAGE_COUNT) MAX_IMAGE_COUNT else _data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > _data.size) TYPE_PLUS else TYPE_IMAGE
    }

    fun setData(data: List<Uri>) {
        _data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > _data.size) {
            (holder as PlusImageViewHolder).bind()
        } else {
            (holder as ImagePickerViewHolder).bind(_data[position])
        }
    }

    inner class ImagePickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var ivPicked: ImageView

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            ivPicked = itemView.findViewById(R.id.ivPicked)

            ivPicked.setOnClickListener { view ->
                itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
        }

        fun bind(uri: Uri) {
            Glide.with(ivPicked.context)
                .load(uri)
                .into(ivPicked)
        }
    }

    inner class PlusImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var ivPlus: ImageView

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            ivPlus = itemView.findViewById(R.id.ivPlus)

            ivPlus.setOnClickListener { view ->
                itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
        }

        fun bind() {
        }
    }
}

