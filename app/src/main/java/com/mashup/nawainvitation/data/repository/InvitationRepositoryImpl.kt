package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.api.InvitationApi
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.model.request.InvitationsRequest
import com.mashup.nawainvitation.data.room.dao.InvitationDao
import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import com.mashup.nawainvitation.data.room.entity.LocationEntity
import com.mashup.nawainvitation.presentation.main.model.InvitationsData
import com.mashup.nawainvitation.presentation.main.model.mapToPresentation
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.typechoice.model.TypeData
import com.mashup.nawainvitation.presentation.typechoice.model.mapToPresentation
import io.reactivex.Completable
import io.reactivex.Single
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
            .subscribeOn(Schedulers.io())
            .flatMap {
                val typeDatas = mutableListOf<TypeData>()
                it.invitationTypeItemList.forEach { typeItem ->
                    val invitation = invitationDao.getInvitation(typeItem.templateId)
                    if (invitation == null) {
                        invitationDao.insertInvitation(
                            InvitationEntity(
                                templateId = typeItem.templateId,
                                templateBackgroundImageUrl = typeItem.templateBackgroundImageUrl,
                                templateTypeDescription = typeItem.templateTypeDescription
                            )
                        )
                    }
                    val isEditing =
                        invitation != null && invitation.invitationTitle.isNullOrEmpty().not()
                    typeDatas.add(typeItem.mapToPresentation(isEditing))
                }
                Single.just(typeDatas)
            }
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

    override fun getInvitation(
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
        return makeCompletable(
            call = {
                val invitation = invitationDao.getInvitation(templatesId)
                invitation?.let {
                    invitationDao.insertInvitation(
                        it.copy(
                            invitationTitle = invitationTitle,
                            invitationContents = invitationContents
                        )
                    )
                }
            },
            callback = callback
        )
    }

    override fun patchInvitationAddress(
        documents: Documents,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
        return makeCompletable(
            call = {
                val invitation = invitationDao.getInvitation(templatesId)
                invitation?.let {
                    invitationDao.insertInvitation(
                        it.copy(
                            locationEntity = LocationEntity(
                                invitationAddressName = documents.addressName,
                                invitationPlaceName = documents.placeName,
                                invitationRoadAddressName = documents.roadAddressName,
                                longitude = documents.x,
                                latitude = documents.y
                            )
                        )
                    )
                }
            },
            callback = callback
        )
    }

    override fun patchInvitationTime(
        invitationTime: String,
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
        return makeCompletable(
            call = {
                val invitation = invitationDao.getInvitation(templatesId)
                invitation?.let {
                    invitationDao.insertInvitation(
                        it.copy(
                            invitationTime = invitationTime,
                            templateId = templatesId
                        )
                    )
                }
            },
            callback = callback
        )
    }

    private fun makeCompletable(call: () -> Unit, callback: BaseResponse<Any>) =
        Completable.fromCallable {
            call.invoke()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(Unit)
            }) {
                callback.onError(it)
            }

    override fun pathInvitation(
        templatesId: Int,
        callback: BaseResponse<String>
    ): Disposable {
        return invitationDao.getInvitationById(templatesId)
            .subscribeOn(Schedulers.io())
            .flatMap { data ->

                val request = InvitationsRequest(
                    templateId = templatesId.toLong(),
                    invitationTitle = data.invitationTitle,
                    invitationContents = data.invitationContents,
                    invitationTime = data.invitationTime,
                    invitationAddressName = data.locationEntity?.invitationAddressName,
                    invitationRoadAddressName = data.locationEntity?.invitationRoadAddressName,
                    invitationPlaceName = data.locationEntity?.invitationPlaceName,
                    latitude = data.locationEntity?.latitude,
                    longitude = data.locationEntity?.longitude
                )

                invitationApi.postInvitations(request)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                val hashCode = it.invitationHashCode
                if (hashCode.isNullOrEmpty()) {
                    callback.onFail("invitationHashCode is null")
                } else {
                    callback.onSuccess(hashCode)
                }
            }) {
                callback.onError(it)
            }
    }

    override fun deleteInvitationById(
        templatesId: Int,
        callback: BaseResponse<Any>
    ): Disposable {
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
                callback.onError(it)
            }
    }
}