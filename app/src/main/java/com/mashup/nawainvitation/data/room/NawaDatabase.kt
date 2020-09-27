package com.mashup.nawainvitation.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mashup.nawainvitation.data.room.dao.InvitationDao
import com.mashup.nawainvitation.data.room.entity.Invitation

@Database(entities = arrayOf(Invitation::class), version = 1, exportSchema = false)
abstract class NawaDatabase : RoomDatabase() {
    abstract fun invitationDao(): InvitationDao
}