package com.example.pregnapp.network

interface ISessionSource {

    fun getSessionData(): SessionData

    fun updateSessionData(sessionData: SessionData)
}