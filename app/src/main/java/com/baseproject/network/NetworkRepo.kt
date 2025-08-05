package com.baseproject.network

import com.baseproject.network.NetworkClient.getNetworkClient

class NetworkRepo {

    suspend fun login(email: String, password: String): NetworkStatusCode {
        try {
            val response = getNetworkClient().login(email, password)
            return if (response.isSuccessful) {
                NetworkStatusCode.Success(response.body().toString())
            } else {
                NetworkStatusCode.Error(response.message())
            }
        }catch (e: Exception){
            return when(e){
                is java.net.SocketTimeoutException -> NetworkStatusCode.Timeout(e.message.toString())
                else -> {
                    NetworkStatusCode.Exception(e.message.toString())
                }
            }
        }
    }

}