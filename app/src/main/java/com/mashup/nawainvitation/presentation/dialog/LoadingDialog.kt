package com.mashup.nawainvitation.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.util.Dlog

class LoadingDialog(context: Context) : Dialog(context, R.style.dialog_transparent_full_screen) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_progress)
    }

    @CallSuper
    override fun show() {
        try {
            super.show()
        } catch (e: WindowManager.BadTokenException) {
            Dlog.wtf(e.message)
        } catch (e: Exception) {
            Dlog.wtf(e.message)
        }
    }

    @CallSuper
    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: IllegalArgumentException) {
            Dlog.wtf(e.message)
        } catch (e: Exception) {
            Dlog.wtf(e.message)
        }
    }
}