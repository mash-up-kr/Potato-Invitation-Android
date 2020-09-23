package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.model.response.InvitationsResponse
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData
import io.reactivex.disposables.Disposable

interface InvitationRepository {
    fun getInvitationTypes(
        callback: BaseResponse<List<TypeData>>
    ): Disposable

    fun getInvitations(
        templateId: Int,
        callback: BaseResponse<InvitationsResponse>
    ): Disposable

    fun patchInvitationWords(
        invitationTitle: String,
        invitationContents: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable

    fun patchInvitationTime(
        invitationTime: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable

    fun patchInvitationAddress(
        documents: Documents,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable
}