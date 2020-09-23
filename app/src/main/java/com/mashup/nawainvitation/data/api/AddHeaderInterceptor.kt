package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.NawaInvitationApplication
import okhttp3.Interceptor
import okhttp3.Response

// https://github.com/square/okhttp/wiki/Interceptors
class AddHeaderInterceptor : Interceptor {

    private val androidId = NawaInvitationApplication.INSTANCE.deviceIdentifier

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("deviceIdentifier", androidId)
                .build()
        )
    }
}