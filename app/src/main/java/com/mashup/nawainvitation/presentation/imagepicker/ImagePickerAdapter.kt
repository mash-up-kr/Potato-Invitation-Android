package com.mashup.nawainvitation.presentation.imagepicker

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.presentation.imagepicker.data.ImageClickData
import io.reactivex.subjects.PublishSubject

class ImagePickerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_PLUS_INIT = 1
    private val TYPE_PLUS = 2

    private var _data: List<Uri> = arrayListOf()

    private val _itemClickSubject = PublishSubject.create<ImageClickData>()
    val itemClickSubject: PublishSubject<ImageClickData>
        get() = _itemClickSubject

    private val _itemLongClickSubject = PublishSubject.create<ImageClickData>()
    val itemLongClickSubject: PublishSubject<ImageClickData>
        get() = _itemLongClickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> ImagePickerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_image_picker, parent, false)
            )
            TYPE_PLUS_INIT -> InitPlusViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_image_picker_plus_init, parent, false)
            )
            TYPE_PLUS -> PlusImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_image_picker_plus, parent, false)
            )
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return if (_data.isEmpty()) {
            // data가 없을 경우 이미지 추가 요청을 위한 view 추가 (InitPlusViewHolder, PlusViewHolder)
            2
        } else if (_data.size >= Constant.MAX_IMAGE_COUNT) {
            Constant.MAX_IMAGE_COUNT
        } else {
            // 마지막 아이템에 이미지 추가 view 추가를 위해 +1
            _data.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_data.isEmpty() && position == 0) {
            TYPE_PLUS_INIT
        } else if (position >= _data.size) {
            TYPE_PLUS
        } else {
            TYPE_IMAGE
        }
    }

    fun setData(data: List<Uri>) {
        _data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(_data.isEmpty() && position == 0){
            (holder as InitPlusViewHolder).bind()
        }else if(position >= _data.size){
            (holder as PlusImageViewHolder).bind()
        }else{
            (holder as ImagePickerViewHolder).bind(_data[position])
        }
    }

    inner class ImagePickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var clRoot: ConstraintLayout
        private lateinit var ivPicked: ImageView

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            clRoot = itemView.findViewById(R.id.clRootImagePicker)
            ivPicked = itemView.findViewById(R.id.ivPickedImagePicker)

            clRoot.setOnClickListener { view ->
                _itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
            clRoot.setOnLongClickListener { view ->
                _itemLongClickSubject.onNext(ImageClickData(view, layoutPosition))
                false
            }
        }

        fun bind(uri: Uri) {
            Glide.with(ivPicked.context)
                .load(uri)
                .centerCrop()
                .into(ivPicked)
        }
    }

    inner class PlusImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var clRoot: ConstraintLayout

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            clRoot = itemView.findViewById(R.id.clRootImagePickerPlus)
            clRoot.setOnClickListener { view ->
                itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
        }

        fun bind() {}
    }

    inner class InitPlusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var clRoot: ConstraintLayout

        init {
            initView(itemView)
        }

        private fun initView(itemView: View) {
            clRoot = itemView.findViewById(R.id.clRootImagePickerPlus)
            clRoot.setOnClickListener { view ->
                itemClickSubject.onNext(ImageClickData(view, layoutPosition))
            }
        }

        fun bind() {}
    }
}

