package com.mashup.patatoinvitation.data.repository

import com.mashup.patatoinvitation.data.base.BaseResponse
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import io.reactivex.disposables.Disposable

interface InvitationRepository {
    fun getInvitationTypes(
        callback: BaseResponse<List<TypeData>>
    ): Disposable

    fun patchInvitationWords(
        invitationTitle: String,
        invitationContents: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable
}