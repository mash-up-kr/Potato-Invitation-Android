package com.mashup.patatoinvitation.data.injection

import com.mashup.patatoinvitation.data.api.ApiProvider
import com.mashup.patatoinvitation.data.repository.InvitationRepository
import com.mashup.patatoinvitation.data.repository.InvitationRepositoryImpl

object Injection {

    fun provideInvitationRepository(): InvitationRepository {
        return InvitationRepositoryImpl(
            ApiProvider.provideRepoApi()
        )
    }
}