package com.mashup.nawainvitation.data.room.database

import android.content.Context
import androidx.room.Room
import com.mashup.nawainvitation.base.util.Dlog

object DatabaseProvider {

    private lateinit var nawaDatabase: NawaDatabase
    fun getDatabase() = nawaDatabase

    @Synchronized
    fun initDatabase(context: Context) {
        Dlog.d("db 생성")
        nawaDatabase = Room
            .databaseBuilder(context, NawaDatabase::class.java, "nawa-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}