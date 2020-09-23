package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.api.InvitationApi
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.model.request.InvitationAddressRequest
import com.mashup.nawainvitation.data.model.request.InvitationTimeRequest
import com.mashup.nawainvitation.data.model.request.InvitationWordsRequest
import com.mashup.nawainvitation.presentation.model.InvitationsData
import com.mashup.nawainvitation.presentation.model.TypeData
import com.mashup.nawainvitation.presentation.model.mapToPresentation
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class InvitationRepositoryImpl(
    private val invitationApi: InvitationApi
) : InvitationRepository {

    override fun getInvitationTypes(
        callback: BaseResponse<List<TypeData>>
    ): Disposable {
        return invitationApi.getTemplateTypes()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it.invitationTypeItemList.mapToPresentation())
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }

    override fun getInvitations(
        templateId: Int,
        callback: BaseResponse<InvitationsData>
    ): Disposable {
        return invitationApi.getInvitations(templateId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it.mapToPresentation())
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }

    override fun patchInvitationWords(
        invitationTitle: String,
        invitationContents: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
        val request = InvitationWordsRequest(
            invitationTitle = invitationTitle,
            invitationContents = invitationContents,
            templatesId = templatesId
        )

        return invitationApi.patchInvitationWords(request)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it)
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }

    override fun patchInvitationAddress(
        documents: Documents,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
        val request = InvitationAddressRequest(
            invitationAddressName = documents.addressName,
            invitationPlaceName = documents.placeName,
            invitationRoadAddressName = documents.roadAddressName,
            x = documents.x,
            y = documents.y,
            templatesId = templatesId
        )

        return invitationApi.patchInvitationAddress(request)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it)
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }

    override fun patchInvitationTime(
        invitationTime: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
        val request = InvitationTimeRequest(
            invitationTime = invitationTime,
            templatesId = templatesId
        )
        return invitationApi.patchInvitationTime(request)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it)
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }
}