package com.mashup.patatoinvitation.presentation.typechoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mashup.patatoinvitation.databinding.ItemTypeChoiceBinding
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData


class TypePagerAdapter(val inflater: LayoutInflater) : PagerAdapter() {
    private val _datas: MutableList<TypeData> = mutableListOf()

    private val _views: MutableList<CardView> = mutableListOf()

    private lateinit var binding: ItemTypeChoiceBinding

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        binding = ItemTypeChoiceBinding.inflate(inflater)
        binding.tvTypeTitle.text = _datas[position].title

        Glide.with(binding.ivTypeImage.context)
            .load(_datas[position].imageUrl)
            .centerCrop()
            .into(binding.ivTypeImage)

        binding.tvTypeDescription.text = _datas[position].description

        _views.add(binding.cardView)
        container.addView(binding.root)
        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    fun addTypeItem(data: TypeData) {
        _datas.add(data)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return _datas.size
    }
}