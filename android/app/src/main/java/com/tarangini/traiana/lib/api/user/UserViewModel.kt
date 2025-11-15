package com.tarangini.traiana.lib.api.user

import android.content.Context
import android.util.Log
import coil.network.HttpException
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Success
import com.tarangini.traiana.lib.dto.RegisterUserDTO
import com.tarangini.traiana.utils.GetLocation
import com.tarangini.traiana.utils.RetrofitInstance
import com.tarangini.traiana.utils.SecureTokenManager
import kotlinx.coroutines.launch

class UserViewModel(
  initialState: States
) : MavericksViewModel<States>(initialState) {

  fun register(data : RegisterUserDTO,context: Context) {
    setState { copy(registerRequest = Loading()) }
    viewModelScope.launch {
      try {
        val response = RetrofitInstance.api.registerUser(data.copy(
          gender = data.gender.lowercase()
        ))
        val token = response.payload ?: ""
        if(token.isNotEmpty()){
          SecureTokenManager.saveToken(context,token)
        }
        setState { copy(registerRequest = Success(token)) }
      }catch (e : Exception){
        setState { copy(registerRequest = Fail(e)) }
        if(e is HttpException){
          Log.d("REGISTER ERROR", "ERROR : ${e.response}")
        }
      }
    }
  }

  fun getUser(){
    setState { copy(getUserRequest = Loading()) }
    viewModelScope.launch {
      try {
        val response = RetrofitInstance.api.getUser()
        val user = response.payload ?: throw IllegalStateException("User not found")
        setState { copy(getUserRequest = Success(user)) }
      }catch (e : Exception){
        setState { copy(getUserRequest = Fail(e)) }
      }
    }
  }

  fun updateCurrentLocation(context: Context){
    GetLocation(context){ la , lo ->
      setState {  copy(currentLocation = Pair(la,lo)) }
    }
  }
}