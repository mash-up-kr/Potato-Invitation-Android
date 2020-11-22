package com.mashup.nawainvitation.data.room.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private lateinit var nawaDatabase: NawaDatabase
    fun getDatabase() = nawaDatabase

    @Synchronized
    fun initDatabase(context: Context) {
        nawaDatabase = Room
            .databaseBuilder(context, NawaDatabase::class.java, "nawa-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}