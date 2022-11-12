package com.example.pregnapp.auth.models

interface ISessionSource {

    fun getSessionData(): SessionData

    fun updateSessionData(sessionData: SessionData)
}