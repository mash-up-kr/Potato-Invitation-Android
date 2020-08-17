package com.mashup.nawainvitation.custom

import android.os.Bundle
import android.view.View
import android.view.Window
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseDialogFragment
import com.mashup.nawainvitation.databinding.DialogNetworkErrorBinding

class NetworkErrorDialog : BaseDialogFragment<DialogNetworkErrorBinding>(R.layout.dialog_network_error){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lemonButtonBack.setOnClickListener { dismiss() }
    }

    private fun setWindowFullScreen() {
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
    }

}