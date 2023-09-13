package com.fantasy.fantasyfootball.presentation.ui.profile.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.service.ImageStorageService
import com.fantasy.fantasyfootball.domain.repository.UserRepository
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import com.fantasy.fantasyfootball.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val auth: UserRepository) :
    BaseViewModel() {
    val loggedIn: MutableLiveData<Boolean?> = MutableLiveData()

    fun updateProfile(imageUri: Uri?) {
        viewModelScope.launch {
            try {
                val imageName = user.value?.image ?: Utils.getCurrentTime()
                imageUri?.let {
                    ImageStorageService.addImage(imageUri, imageName) { status ->
                        if (!status) viewModelScope.launch { error.emit(Enums.FormError.IMAGE_UPLOAD_FAILED.name) }
                    }
                    user.value?.let {
                        safeApiCall { auth.updateUser(it.copy(image = imageName)) }
                        success.emit(Enums.FormSuccess.UPDATE_SUCCESSFUL.name)
                    }
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun editUser(user: User) {
        viewModelScope.launch {
            try {
                safeApiCall { auth.updateUser(user) }
                success.emit(Enums.FormSuccess.UPDATE_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun updatePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                auth.updatePassword(currentPassword, newPassword)
                fetchCurrentUser()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { auth.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun isLoggedIn() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { auth.isAuthenticated() }
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
                safeApiCall { auth.deAuthenticate() }
                success.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}