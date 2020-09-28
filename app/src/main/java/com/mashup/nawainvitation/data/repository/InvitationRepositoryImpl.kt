package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.api.InvitationApi
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.room.dao.InvitationDao
import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import com.mashup.nawainvitation.data.room.entity.LocationEntity
import com.mashup.nawainvitation.presentation.main.model.InvitationsData
import com.mashup.nawainvitation.presentation.main.model.mapToPresentation
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.typechoice.model.TypeData
import com.mashup.nawainvitation.presentation.typechoice.model.mapToPresentation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class InvitationRepositoryImpl(
    private val invitationApi: InvitationApi,
    private val invitationDao: InvitationDao
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
        return invitationDao.getInvitationById(templateId)
            .subscribeOn(Schedulers.io())
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
        val request = InvitationEntity(
            invitationTitle = invitationTitle,
            invitationContents = invitationContents,
            templateId = templatesId
        )
        return invitationDao.insertWord(request)
            .subscribeOn(Schedulers.io())
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
        val request = InvitationEntity(
            templateId = templatesId,
            locationEntity = LocationEntity(
                invitationAddressName = documents.addressName,
                invitationPlaceName = documents.placeName,
                invitationRoadAddressName = documents.roadAddressName,
                longitude = documents.x,
                latitude = documents.y
            )
        )

        return invitationDao.insertLocation(request)
            .subscribeOn(Schedulers.io())
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
        val request = InvitationEntity(
            invitationTime = invitationTime,
            templateId = templatesId
        )
        return invitationDao.insertTime(request)
            .subscribeOn(Schedulers.io())
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

    override fun deleteInvitationById(templatesId: Int, callback: BaseResponse<Any>): Disposable {
        return invitationDao.deleteInvitationById(templatesId)
            .subscribeOn(Schedulers.io())
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