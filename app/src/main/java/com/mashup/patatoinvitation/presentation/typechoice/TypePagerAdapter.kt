package com.mashup.patatoinvitation.presentation.typechoice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.databinding.ItemTypeChoiceBinding
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData


class TypePagerAdapter(val context: Context, val inflater: LayoutInflater) : PagerAdapter() {
    private val _dataList: MutableList<TypeData> = mutableListOf()

    private val _viewList: MutableList<CardView> = mutableListOf()

    private lateinit var binding: ItemTypeChoiceBinding

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        binding = ItemTypeChoiceBinding.inflate(inflater)
        binding.tvTypeTitle.text = _dataList[position].title

        Glide.with(binding.ivTypeImage.context)
            .load(_dataList[position].imageUrl)
            .centerCrop()
            .into(binding.ivTypeImage)

        binding.tvTypeDescription.text = _dataList[position].description

        binding.tvTypeMakeSuggest.text =
            if (_dataList[position].isEditing) {
                context.getText(R.string.make_suggest_type_editing)
            } else {
                context.getText(R.string.make_suggest_type_default)
            }

        _viewList.add(binding.cardView)
        container.addView(binding.root)
        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    fun addAllTypeDataList(data: List<TypeData>) {
        _dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return _dataList.size
    }

    fun getTypeData(position: Int): TypeData {
        return _dataList[position]
    }
}