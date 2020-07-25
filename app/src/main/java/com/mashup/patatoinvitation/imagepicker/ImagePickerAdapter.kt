package com.mashup.patatoinvitation.imagepicker

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.patatoinvitation.R
import io.reactivex.subjects.PublishSubject

class ImagePickerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: MutableList<Uri>? = null

    private val _itemClickSubject = PublishSubject.create<View>()
    val itemClickSubject: PublishSubject<View>
    get() = _itemClickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImagePickerViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_image_picker, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImagePickerViewHolder).bind(data!![position])
    }

    inner class ImagePickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var ivPickedImage: ImageView

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            ivPickedImage = itemView.findViewById(R.id.ivPickedImage)

            ivPickedImage.setOnClickListener { view ->
                itemClickSubject.onNext(view)
            }
        }

        fun bind(uri: Uri) {
            Glide.with(ivPickedImage)
                .load(uri)
                .into(ivPickedImage)
        }
    }
}

