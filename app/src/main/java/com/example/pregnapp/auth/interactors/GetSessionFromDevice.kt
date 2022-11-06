package com.example.pregnapp.auth.interactors

import com.example.pregnapp.network.ISessionSource
import com.example.pregnapp.network.SessionData

class GetSessionFromDevice(
    private val sessionSource: ISessionSource
) {
    operator fun invoke() : SessionData {
        return sessionSource.getSessionData()
    }
}