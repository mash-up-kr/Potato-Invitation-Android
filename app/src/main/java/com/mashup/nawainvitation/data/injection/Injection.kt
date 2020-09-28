package com.mashup.nawainvitation.data.injection

import com.mashup.nawainvitation.data.api.ApiProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.data.repository.InvitationRepositoryImpl
import com.mashup.nawainvitation.data.repository.UserRepository
import com.mashup.nawainvitation.data.repository.UserRepositoryImpl
import com.mashup.nawainvitation.data.room.database.DatabaseProvider

object Injection {

    fun provideInvitationRepository(): InvitationRepository {
        return InvitationRepositoryImpl(
            ApiProvider.provideInvitationApi(),
            DatabaseProvider.getDatabase().invitationDao()
        )
    }

    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl(
            ApiProvider.provideUserApi()
        )
    }
}