package com.mashup.patatoinvitation.data.repository

import com.mashup.patatoinvitation.data.base.BaseResponse
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import io.reactivex.disposables.Disposable

interface InvitationRepository {
    fun getInvitationTypes(
        deviceIdentifier: String,
        callback: BaseResponse<List<TypeData>>
    ): Disposable

    fun patchInvitationWords(
        deviceIdentifier: String,
        invitationTitle: String,
        invitationContents: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable
}