package com.mashup.patatoinvitation.data.repository

import com.mashup.patatoinvitation.PotatoInvitationApplication
import com.mashup.patatoinvitation.data.api.UserApi
import com.mashup.patatoinvitation.data.base.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    private val deviceIdentifier = PotatoInvitationApplication.INSTANCE.deviceIdentifier

    override fun getUsers(callback: BaseResponse<Any>): Disposable {
        return userApi.postUsers(deviceIdentifier)
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