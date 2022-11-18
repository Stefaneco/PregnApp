package com.example.pregnapp.di

import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.auth.interactors.*
import com.example.pregnapp.auth.models.ISessionSource
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
            Login(accountService, sessionSource),
            IsValidEmail(),
            IsValidPassword(),
            IsValidName(),
            Register(accountService, sessionSource)
        )
    }
}