package com.mashup.nawainvitation

import android.app.Application
import android.provider.Settings
import androidx.room.Room
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.room.NawaDatabase

class NawaInvitationApplication : Application() {

    companion object {

        lateinit var INSTANCE: NawaInvitationApplication
        lateinit var nawaDatabase: NawaDatabase
    }

    val deviceIdentifier by lazy {
        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        nawaDatabase = Room.databaseBuilder(applicationContext, NawaDatabase::class.java, "nawa-database").build()

        Dlog.d("deviceIdentifier : ${INSTANCE.deviceIdentifier}")
    }
}