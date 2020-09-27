package com.mashup.nawainvitation.data.room.dao

import androidx.room.*
import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import io.reactivex.Single

@Dao
interface InvitationDao {

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitationById(templateId: Int?): Single<InvitationEntity>

    @Query("SELECT templateId from invitation WHERE templateId = :templateId")
    fun getInvitationId(templateId: Int?): Int?

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitation(templateId: Int?): InvitationEntity

    @Query("DELETE from invitation WHERE templateId = :templateId")
    fun deleteInvitationById(templateId: Int): Single<Void>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvitation(invitationEntity: InvitationEntity): Void

    @Transaction
    private fun insertWord(request: InvitationEntity): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(invitationTitle= request.invitationTitle, invitationContents = request.invitationContents)
        return insertInvitation(invitation)
    }

    fun insertWordSync(request: InvitationEntity) : Single<Void> {
        return Single.create {
            it.onSuccess(insertWord(request))
        }
    }

    @Transaction
    private fun insertLocationSync(request: InvitationEntity): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(locationEntity = request.locationEntity)
        return insertInvitation(invitation)
    }

    fun insertLocation(request: InvitationEntity) : Single<Void> {
        return Single.create {
            it.onSuccess(insertLocationSync(request))
        }
    }

    @Transaction
    private fun insertTimeSync(request: InvitationEntity): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(invitationTime = request.invitationTime)
        return insertInvitation(invitation)
    }

    fun insertTime(request: InvitationEntity) : Single<Void> {
        return Single.create {
            it.onSuccess(insertTimeSync(request))
        }
    }

    @Transaction
    private fun insertImageSync(request: InvitationEntity): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(images = request.images)
        return insertInvitation(invitation)
    }

    fun insertImage(request: InvitationEntity) : Single<Void> {
        return Single.create {
            it.onSuccess(insertImageSync(request))
        }
    }
}
