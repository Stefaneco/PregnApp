package com.example.pregnapp.di

import android.content.Context
import com.example.pregnapp.auth.AccountService
import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.auth.models.ISessionSource
import com.example.pregnapp.auth.models.SessionSource
import com.example.pregnapp.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideSessionSource(@ApplicationContext context: Context): ISessionSource {
        return SessionSource(context)
    }

    @Singleton
    @Provides
    fun provideAccountService(httpClient : HttpClient, httpRoutes: IHttpRoutes): IAccountService {
        return AccountService(httpClient, httpRoutes)
    }

    @Singleton
    @Provides
    fun provideHttpClient(sessionSource: ISessionSource, httpRoutes: IHttpRoutes): HttpClient {
        //MockApiEngine.givenFailure(HttpStatusCode.BadRequest)
        return KtorClientFactory(sessionSource, httpRoutes, MockApiEngine.get()).build()
        //return KtorClientFactory(sessionSource, httpRoutes, CIO.create()).build()
    }

    @Singleton
    @Provides
    fun provideHttpRoutes() : IHttpRoutes {
        return HttpRoutes()
    }
}