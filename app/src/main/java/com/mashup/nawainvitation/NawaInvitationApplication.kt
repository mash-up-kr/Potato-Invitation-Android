package com.mashup.nawainvitation

import android.app.Application
import android.provider.Settings

class NawaInvitationApplication : Application() {

    companion object {

        lateinit var INSTANCE: NawaInvitationApplication
    }

    val deviceIdentifier by lazy {
        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}