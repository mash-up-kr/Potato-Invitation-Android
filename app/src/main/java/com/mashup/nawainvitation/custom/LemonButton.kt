package com.mashup.nawainvitation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.mashup.nawainvitation.R


class LemonButton(context: Context?, attributeSet: AttributeSet) : AppCompatButton(context, attributeSet) {
    init {
        gravity = Gravity.CENTER
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        setBackgroundColor(
            if(enabled) getEnableButtonColor() else getUnEableButtonColor()
        )

        setTextColor(
            if(enabled) getEnableTextColor() else getUnEnableTextColor()
        )
    }

    private fun getEnableButtonColor() = ContextCompat.getColor(context, R.color.lemon)

    private fun getUnEableButtonColor() = ContextCompat.getColor(context, R.color.pinkish_grey)

    private fun getEnableTextColor() = R.color.black

    private fun getUnEnableTextColor() = R.color.btn_lemon_unenable_text_color
}