package com.mashup.nawainvitation.data.injection

import com.mashup.nawainvitation.data.api.ApiProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.data.repository.InvitationRepositoryImpl
import com.mashup.nawainvitation.data.repository.UserRepository
import com.mashup.nawainvitation.data.repository.UserRepositoryImpl

object Injection {

    fun provideInvitationRepository(): InvitationRepository {
        return InvitationRepositoryImpl(
            ApiProvider.provideInvitationApi()
        )
    }

    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl(
            ApiProvider.provideUserApi()
        )
    }
}