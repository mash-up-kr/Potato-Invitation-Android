package com.mashup.nawainvitation.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mashup.nawainvitation.data.room.dao.InvitationDao
import com.mashup.nawainvitation.data.room.entity.InvitationEntity

@Database(entities = arrayOf(InvitationEntity::class), version = 1, exportSchema = false)
abstract class NawaDatabase : RoomDatabase() {
    abstract fun invitationDao(): InvitationDao
}