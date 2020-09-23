package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.api.UserApi
import com.mashup.nawainvitation.data.base.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override fun getUsers(callback: BaseResponse<Any>): Disposable {
        return userApi.postUsers()
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