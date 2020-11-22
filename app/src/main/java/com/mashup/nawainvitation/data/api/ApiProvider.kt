package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiProvider {

    private const val baseUrl = "http://15.165.190.104/apis/v2/"

    fun provideInvitationApi(): InvitationApi = getRetrofitBuild().create(InvitationApi::class.java)

    fun provideUserApi(): UserApi = getRetrofitBuild().create(UserApi::class.java)

    private fun getRetrofitBuild() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getOkhttpClient())
        // 받은 응답을 옵서버블 형태로 변환해 줍니다.
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(getGsonConverter())
        .build()

    private fun getGsonConverter() = GsonConverterFactory.create()

    private fun getOkhttpClient() = OkHttpClient.Builder().apply {

        //TimeOut 시간을 지정합니다.
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        writeTimeout(5, TimeUnit.SECONDS)

        // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        addInterceptor(getLoggingInterceptor())

        // header 에 정보를 추가해 준다.
        addInterceptor(AddHeaderInterceptor())
    }.build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}