package com.mashup.nawainvitation.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.mashup.nawainvitation.R

class LoadingDialog(context: Context) : Dialog(context, R.style.dialog_transparent_full_screen) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_progress)
    }
}