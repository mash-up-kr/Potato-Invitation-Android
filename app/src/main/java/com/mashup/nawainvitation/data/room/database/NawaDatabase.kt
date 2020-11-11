package com.mashup.nawainvitation.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mashup.nawainvitation.data.room.dao.InvitationDao
import com.mashup.nawainvitation.data.room.dao.InvitationDaoV2
import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2

@Database(
    entities = [InvitationEntity::class, InvitationEntityV2::class],
    version = 2,
    exportSchema = false
)
abstract class NawaDatabase : RoomDatabase() {

    abstract fun invitationDao(): InvitationDao

    abstract fun invitationDaoV2(): InvitationDaoV2
}