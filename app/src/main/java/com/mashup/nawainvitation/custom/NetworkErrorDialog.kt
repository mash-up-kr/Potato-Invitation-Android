package com.mashup.nawainvitation.custom

import android.os.Bundle
import android.view.View
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseDialogFragment
import com.mashup.nawainvitation.databinding.DialogNetworkErrorBinding

class NetworkErrorDialog : BaseDialogFragment<DialogNetworkErrorBinding>(R.layout.dialog_network_error){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lemonButtonBack.setOnClickListener { dismiss() }
    }
}