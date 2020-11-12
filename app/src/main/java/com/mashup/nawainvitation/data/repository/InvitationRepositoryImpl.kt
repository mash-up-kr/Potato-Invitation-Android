package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.api.InvitationApi
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.room.dao.InvitationDaoV2
import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter
import com.mashup.nawainvitation.presentation.main.model.ImageInfoItem
import com.mashup.nawainvitation.presentation.main.model.InvitationsItem
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import com.mashup.nawainvitation.presentation.main.model.mapToPresentation
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class InvitationRepositoryImpl(
    private val invitationApi: InvitationApi,
    private val invitationDao: InvitationDaoV2
) : InvitationRepository {

    override fun getAllTypes(callback: BaseResponse<List<TypeItem>>): Disposable {
        return invitationApi.getTemplateTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it.invitationTypeItemList.map { item ->
                    item.mapToPresentation()
                })
            }) {
                callback.onError(it)
            }
    }

    override fun insertTempInvitation() {
        makeCompletable(
            call = {
                invitationDao.insertInvitation(
                    InvitationEntityV2()
                )
            }
        )
    }

    override fun getInvitations(): Flowable<List<InvitationsItem>> {
        return invitationDao.getInvitationsRx().map {
            it.map { item ->
                item.mapToPresentation()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLatestInvitation(): Flowable<InvitationsItem> {
        return invitationDao.getInvitationsRx().flatMap {
            val latestItem = it.lastOrNull()

            if (latestItem == null) {
                insertTempInvitation()
                Flowable.just(InvitationEntityV2().mapToPresentation())
            } else {
                Flowable.just(latestItem.mapToPresentation())
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateInvitationTemplateId(
        templateId: Int
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateTemplateId(templateId)
            }
        )
    }

    override fun updateInvitationWords(
        invitationTitle: String,
        invitationContents: String
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateWord(
                    title = invitationTitle,
                    content = invitationContents
                )
            }
        )
    }

    override fun updateInvitationAddress(
        documents: Documents
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateAddress(documents)
            }
        )
    }

    override fun updateInvitationTime(
        invitationTime: String
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateTime(invitationTime)
            }
        )
    }

    override fun updateInvitationImages(
        imageList: List<ImageInfoItem>
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateImages(
                    ImageListTypeAdapter.imageListToJsonString(imageList)
                )
            }
        )
    }

    override fun updateInvitationHashcode(
        hashCode: String?
    ): Disposable {
        return makeCompletable(
            call = {
                invitationDao.updateHashCode(hashCode)
            }
        )
    }

    private val MEDIA_TYPE_TEXT = "text/plain".toMediaTypeOrNull()
    private val MEDIA_TYPE_MULTIPART = "multipart/form-data".toMediaTypeOrNull()

    override fun pathInvitation(
        templatesId: Int,
        callback: BaseResponse<String>
    ): Disposable {
        //TODO [JinSeong] RX 조합법 확인하기
        return Single.fromCallable { }.flatMap {
            val invitations = invitationDao.getInvitations()
            val data = invitations.last()

            val bodyTemplatesId = RequestBody.create(MEDIA_TYPE_TEXT, templatesId.toString())
            val bodyInvitationTitle =
                RequestBody.create(MEDIA_TYPE_TEXT, data.invitationTitle ?: "")
            val bodyInvitationContents =
                RequestBody.create(MEDIA_TYPE_TEXT, data.invitationContents ?: "")
            val bodyInvitationTime =
                RequestBody.create(MEDIA_TYPE_TEXT, data.invitationTime ?: "")
            val bodyInvitationAddressName = RequestBody.create(
                MEDIA_TYPE_TEXT,
                data.locationEntity?.invitationAddressName ?: ""
            )
            val bodyInvitationRoadAddressName = RequestBody.create(
                MEDIA_TYPE_TEXT,
                data.locationEntity?.invitationRoadAddressName ?: ""
            )
            val bodyInvitationPlaceName = RequestBody.create(
                MEDIA_TYPE_TEXT,
                data.locationEntity?.invitationPlaceName ?: ""
            )
            val mLatitude = data.locationEntity?.latitude
            val bodyLatitude = RequestBody.create(
                MEDIA_TYPE_TEXT,
                if (mLatitude == null) "" else data.locationEntity.latitude.toString()
            )
            val mLongitude = data.locationEntity?.longitude
            val bodyLongitude = RequestBody.create(
                MEDIA_TYPE_TEXT,
                if (mLongitude == null) "" else data.locationEntity.longitude.toString()
            )
            var bodyImages: Array<MultipartBody.Part>? = null
            val imageList = ImageListTypeAdapter.jsonStringToImageList(data.images)
            if (!imageList.isNullOrEmpty()) {
                bodyImages = getImagesMultiPartBody(imageList)
            }
            invitationApi.postInvitations(
                templateId = bodyTemplatesId,
                invitationTitle = bodyInvitationTitle,
                invitationContents = bodyInvitationContents,
                invitationTime = bodyInvitationTime,
                invitationAddressName = bodyInvitationAddressName,
                invitationRoadAddressName = bodyInvitationRoadAddressName,
                invitationPlaceName = bodyInvitationPlaceName,
                latitude = bodyLatitude,
                longitude = bodyLongitude,
                images = bodyImages
            )

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it.invitationHashCode ?: "")
            }) {
                callback.onError(it)
            }
    }

    private fun getImagesMultiPartBody(imageList: List<ImageInfoItem>): Array<MultipartBody.Part> {
        val bodyImages = mutableListOf<MultipartBody.Part>()

        for (i in imageList.indices) {
            val imageUri = imageList[i].imageUri!!
            val file = File(imageUri.replace("file://", ""))
            val requestBody = file.asRequestBody(MEDIA_TYPE_MULTIPART)
            bodyImages.add(MultipartBody.Part.createFormData("files", file.name, requestBody))
        }
        return bodyImages.toTypedArray()
    }

    private fun makeCompletable(call: () -> Unit) =
        Completable.fromCallable {
            call.invoke()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

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
}