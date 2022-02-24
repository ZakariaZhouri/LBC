package com.example.lbc.common

sealed class Resource<T>(val data: T? = null, val networkError: Int? = null) {
    class Loading<T>() : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class NetworkError<T>(networkError: Int) : Resource<T>(networkError = networkError)

}

enum class NetworkError(val networkError: Int) {
    NOT_AUTHORISE(networkError = 403) {
        override fun message() = "Not authorise"
    },
    NO_CONNEXION(networkError = 400) {
        override fun message() = "no connexion"
    };

    abstract fun message(): String
}