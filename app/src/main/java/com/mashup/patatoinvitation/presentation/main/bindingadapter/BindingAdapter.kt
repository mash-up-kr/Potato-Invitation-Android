package com.mashup.patatoinvitation.presentation.main.bindingadapter

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:isChecked")
fun ImageView.setCheck(isChecked: Boolean) {
    if (isChecked) {
        setBackgroundColor(Color.parseColor("#ffeedd"))
    } else {
        setBackgroundColor(Color.parseColor("#33ee22"))
    }
}