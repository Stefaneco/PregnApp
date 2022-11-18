package com.example.pregnapp.auth.interactors

import com.example.pregnapp.auth.models.ISessionSource
import com.example.pregnapp.auth.models.SessionData

class GetSessionFromDevice(
    private val sessionSource: ISessionSource
) {
    operator fun invoke() : SessionData {
        return sessionSource.getSessionData()
    }
}