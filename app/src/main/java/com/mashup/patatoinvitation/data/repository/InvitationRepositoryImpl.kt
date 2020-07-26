package com.mashup.patatoinvitation.data.repository

import com.mashup.patatoinvitation.PotatoInvitationApplication
import com.mashup.patatoinvitation.data.api.InvitationApi
import com.mashup.patatoinvitation.data.base.BaseResponse
import com.mashup.patatoinvitation.data.model.request.InvitationWordsRequest
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class InvitationRepositoryImpl(
    private val invitationApi: InvitationApi
) : InvitationRepository {

    private val deviceIdentifier = PotatoInvitationApplication.INSTANCE.deviceIdentifier

    override fun getInvitationTypes(
        callback: BaseResponse<List<TypeData>>
    ): Disposable {
        return invitationApi.getTemplateTypes(deviceIdentifier)
            //이 이후에 수행되는 코드는 메인 쓰레드에서 실행됩니다.
            .observeOn(AndroidSchedulers.mainThread())
            //구독할 때 수행할 작업을 구현합니다.
            .doOnSubscribe {
                callback.onLoading()
            }
            //스트림이 종료될 때 수행할 작업을 구현합니다.
            //Single에서 스트림이 종료되는 시점은 성공(Success)과 에러(Error) 입니다.
            .doOnTerminate {
                callback.onLoaded()
            }
            //옵서버블을 구독합니다.
            .subscribe({
                callback.onSuccess(it.invitationTypeItemList.map {
                    TypeData(
                        title = it.typeName,
                        description = it.typeDescription,
                        imageUrl = it.imageUrl,
                        isEditing = it.isExistInvitation,
                        templateId = it.templateId
                    )
                })
            }) {
                // 에러 처리 작업을 구현합니다.
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
        return invitationApi.patchInvitationWords(deviceIdentifier, request)
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