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
import com.mashup.patatoinvitation.imagepicker.data.ImageClickData
import io.reactivex.subjects.PublishSubject

class ImagePickerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _data: MutableList<Uri> = arrayListOf()

    private val _itemClickSubject = PublishSubject.create<ImageClickData>()
    val itemClickSubject: PublishSubject<ImageClickData>
    get() = _itemClickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImagePickerViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_image_picker, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImagePickerViewHolder).bind(_data[position])
    }

    fun addImageUriList(uriList: List<Uri>){
        _data.addAll(uriList)
        notifyDataSetChanged()
    }

    fun deleteImage(position: Int) {
        _data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ImagePickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var ivPickedImage: ImageView

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            ivPickedImage = itemView.findViewById(R.id.ivPickedImagePicker)

            ivPickedImage.setOnClickListener { view ->
                itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
        }

        fun bind(uri: Uri) {
            Glide.with(ivPickedImage.context)
                .load(uri)
                .into(ivPickedImage)
        }
    }
}

