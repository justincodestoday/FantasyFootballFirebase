package com.fantasy.fantasyfootball.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.*
import com.fantasy.fantasyfootball.service.ImageStorageService
import com.fantasy.fantasyfootball.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
    val loggedIn: MutableLiveData<Boolean?> = MutableLiveData()

    init {
        fetchCurrentUser()
    }

    fun updateProfile(
        imageUri: Uri?
    ) {
        val imageName = user.value?.image ?: Utils.getCurrentTime()
        viewModelScope.launch {
            imageUri?.let {
                ImageStorageService.addImage(imageUri, imageName) { status ->
                    if (!status) {
                        viewModelScope.launch {
                            error.emit("Image GG Already")
                        }
                    }
                }
                user.value?.let {
                    safeApiCall {
                        userRepo.updateUser(it.copy(image = imageName))
                    }
                    finish.emit(Unit)
                }
            }
        }
    }

    fun editUser(user: User) {
        viewModelScope.launch {
            safeApiCall { userRepo.updateUser(user) }
        }
    }

    fun isLoggedIn() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.isAuthenticated() }
                res.let {
                    loggedIn.value = res
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                safeApiCall { userRepo.deAuthenticate() }
                success.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}