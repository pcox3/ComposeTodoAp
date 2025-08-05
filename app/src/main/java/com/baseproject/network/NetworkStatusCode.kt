package com.baseproject.network

sealed class NetworkStatusCode {

    class Nothing(): NetworkStatusCode()
    class Success(val data: String) : NetworkStatusCode()
    class Error(val message: String) : NetworkStatusCode()
    class Timeout(val message: String) : NetworkStatusCode()
    class Exception(val message: String) : NetworkStatusCode()

}