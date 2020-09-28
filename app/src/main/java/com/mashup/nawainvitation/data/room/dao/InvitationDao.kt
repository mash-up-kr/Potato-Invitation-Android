package com.mashup.nawainvitation.data.room.dao

import androidx.room.*
import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import io.reactivex.Single

@Dao
interface InvitationDao {

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitationById(templateId: Int?): Single<InvitationEntity>

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitation(templateId: Int?): InvitationEntity

    @Query("DELETE from invitation WHERE templateId = :templateId")
    fun deleteInvitationById(templateId: Int): Single<Void>

    @Query("DELETE FROM invitation")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvitation(invitationEntity: InvitationEntity?): Void

    @Transaction
    fun insertWordSync(request: InvitationEntity): Void {
        val data : InvitationEntity? = getInvitation(request.templateId)
        return when (data == null) {
            true -> { insertInvitation(request) }
            else -> {
                val invitation = data.copy(
                    invitationTitle = request.invitationTitle,
                    invitationContents = request.invitationContents
                )
                insertInvitation(invitation)
            }
        }
    }

        fun insertWord(request: InvitationEntity): Single<Void> {
            return Single.create {
                it.onSuccess(insertWordSync(request))
            }
        }

        @Transaction
        fun insertLocationSync(request: InvitationEntity): Void {
            val data : InvitationEntity? = getInvitation(request.templateId)
            return when (data == null) {
                true -> { insertInvitation(request) }
                else -> {
                    val invitation = data.copy(locationEntity = request.locationEntity)
                    insertInvitation(invitation)
                }
            }
        }

        fun insertLocation(request: InvitationEntity): Single<Void> {
            return Single.create {
                it.onSuccess(insertLocationSync(request))
            }
        }

        @Transaction
        fun insertTimeSync(request: InvitationEntity): Void {
            val data : InvitationEntity? = getInvitation(request.templateId)
            return when (data == null) {
                true -> { insertInvitation(request) }
                else -> {
                    val invitation = data.copy(invitationTime = request.invitationTime)
                    insertInvitation(invitation)
                }
            }
        }

        fun insertTime(request: InvitationEntity): Single<Void> {
            return Single.create {
                it.onSuccess(insertTimeSync(request))
            }
        }

        @Transaction
        fun insertImageSync(request: InvitationEntity): Void {
            val data : InvitationEntity? = getInvitation(request.templateId)
            return when (data == null) {
                true -> { insertInvitation(request) }
                else -> {
                    val invitation = data.copy(images = request.images)
                    insertInvitation(invitation)
                }
            }
        }

        fun insertImage(request: InvitationEntity): Single<Void> {
            return Single.create {
                it.onSuccess(insertImageSync(request))
            }
        }
    }
