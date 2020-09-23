package com.mashup.nawainvitation.data.repository

import com.mashup.nawainvitation.data.base.BaseResponse
import io.reactivex.disposables.Disposable

interface UserRepository {
    fun getUsers(
        callback: BaseResponse<Any>
    ): Disposable
}