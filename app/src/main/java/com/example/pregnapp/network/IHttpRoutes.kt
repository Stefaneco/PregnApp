package com.example.pregnapp.network

interface IHttpRoutes {

    fun login(): String

    fun refresh(): String

    fun register(): String
}