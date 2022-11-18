package com.example.pregnapp.network

data class RequestState<T>(
    val message: String? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
) {

    companion object {

        fun <T> error(
            message: String,
        ): RequestState<T> {
            return RequestState(
                message = message,
                data = null,
            )
        }

        fun <T> data(
            message: String? = null,
            data: T? = null,
        ): RequestState<T> {
            return RequestState(
                message = message,
                data = data,
            )
        }

        fun <T>loading() = RequestState<T>(isLoading = true)
    }
}