package com.mashup.patatoinvitation

import android.app.Application
import android.provider.Settings

class PotatoInvitationApplication : Application() {

    companion object {

        lateinit var INSTANCE: PotatoInvitationApplication
    }

    val deviceIdentifier by lazy {
        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}