package com.example.pregnapp.di

import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.network.ISessionSource
import com.example.pregnapp.auth.interactors.AuthInteractors
import com.example.pregnapp.auth.interactors.GetSessionFromDevice
import com.example.pregnapp.auth.interactors.Login
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class InteractorsModule {

    @Provides
    @Singleton
    fun provideAuthInteractors(
        accountService: IAccountService,
        sessionSource: ISessionSource
    ) : AuthInteractors {
        return AuthInteractors(
            GetSessionFromDevice(sessionSource),
            Login(accountService, sessionSource)
        )
    }
}