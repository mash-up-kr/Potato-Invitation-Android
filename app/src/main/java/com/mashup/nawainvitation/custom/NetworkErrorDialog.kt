package com.mashup.nawainvitation.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseDialogFragment
import com.mashup.nawainvitation.databinding.ActivityMainBinding

class NetworkErrorDialog : BaseDialogFragment<ActivityMainBinding>(R.layout.dialog_network_error){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}