package com.example.pregnapp.network

class HttpRoutes: IHttpRoutes {
    val BASE_URL = "https://10.0.2.2:7254/api"
    val LOGIN = "$BASE_URL/account/login"
    val REFRESH = "$BASE_URL/account/refresh"
    val REGISTER = "$BASE_URL/account/register"

    override fun login(): String = LOGIN

    override fun refresh(): String = REFRESH

    override fun register(): String = REGISTER

}