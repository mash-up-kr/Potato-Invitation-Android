package com.mashup.nawainvitation.data.room.dao

import androidx.room.*
import com.mashup.nawainvitation.data.room.entity.Invitation
import io.reactivex.Single

@Dao
interface InvitationDao {

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitationById(templateId: Int?): Single<Invitation>

    @Query("SELECT templateId from invitation WHERE templateId = :templateId")
    fun getInvitationId(templateId: Int?): Int?

    @Query("SELECT * from invitation WHERE templateId = :templateId")
    fun getInvitation(templateId: Int?): Invitation

    @Query("DELETE from invitation WHERE templateId = :templateId")
    fun deleteInvitationById(templateId: Int): Single<Void>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvitation(invitation: Invitation): Void

    @Transaction
    fun insert(request: Invitation): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(invitationTitle= request.invitationTitle, invitationContents = request.invitationContents)
        return insertInvitation(invitation)
    }

    fun insertWord(request: Invitation) : Single<Void> {
        return Single.create {
            it.onSuccess(insert(request))
        }
    }

    @Transaction
    fun insertLocationSync(request: Invitation): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(location = request.location)
        return insertInvitation(invitation)
    }

    fun insertLocation(request: Invitation) : Single<Void> {
        return Single.create {
            it.onSuccess(insertLocationSync(request))
        }
    }

    @Transaction
    fun insertTimeSync(request: Invitation): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(invitationTime = request.invitationTime)
        return insertInvitation(invitation)
    }

    fun insertTime(request: Invitation) : Single<Void> {
        return Single.create {
            it.onSuccess(insertTimeSync(request))
        }
    }

    @Transaction
    fun insertImageSync(request: Invitation): Void {
        val data = getInvitation(request.templateId)
        val invitation = data.copy(images = request.images)
        return insertInvitation(invitation)
    }

    fun insertImage(request: Invitation) : Single<Void> {
        return Single.create {
            it.onSuccess(insertImageSync(request))
        }
    }
}
