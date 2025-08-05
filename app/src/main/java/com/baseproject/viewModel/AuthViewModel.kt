package com.baseproject.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.baseproject.network.NetworkRepo
import com.baseproject.network.NetworkStatusCode
import com.baseproject.network.NetworkStatusCode.*

class AuthViewModel: ViewModel()  {

    val repo = NetworkRepo()
    var isSuccessful = mutableStateOf<NetworkStatusCode>(Nothing())

   suspend fun login(email: String, password: String) {
       val result = repo.login(email, password)
        when(result){
            is Success -> isSuccessful.value = Success(result.data)
            is Error -> isSuccessful.value = Error(result.message)
            is Exception -> isSuccessful.value = Exception(result.message)
            is Timeout -> { isSuccessful.value = Timeout(result.message) }
            else -> {}
        }
    }

}