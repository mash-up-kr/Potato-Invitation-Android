package com.mashup.patatoinvitation.data.repository

import com.mashup.patatoinvitation.data.base.BaseResponse
import io.reactivex.disposables.Disposable

interface UserRepository {
    fun getUsers(
        callback: BaseResponse<Any>
    ): Disposable
}