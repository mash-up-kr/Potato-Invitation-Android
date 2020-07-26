package com.mashup.patatoinvitation.data.injection

import com.mashup.patatoinvitation.data.api.ApiProvider
import com.mashup.patatoinvitation.data.repository.InvitationRepository
import com.mashup.patatoinvitation.data.repository.InvitationRepositoryImpl
import com.mashup.patatoinvitation.data.repository.UserRepository
import com.mashup.patatoinvitation.data.repository.UserRepositoryImpl

object Injection {

    fun provideInvitationRepository(): InvitationRepository {
        return InvitationRepositoryImpl(
            ApiProvider.provideRepoApi()
        )
    }

    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl(
            ApiProvider.provideUserApi()
        )
    }
}