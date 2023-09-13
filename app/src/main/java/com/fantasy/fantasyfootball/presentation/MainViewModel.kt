package com.fantasy.fantasyfootball.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.domain.repository.UserRepository
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepo: UserRepository) :
    BaseViewModel() {

    val nameLiveData: MutableLiveData<String?> = MutableLiveData()
    val emailLiveData: MutableLiveData<String?> = MutableLiveData()
    val imageLiveData: MutableLiveData<String?> = MutableLiveData()

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun updateNameData() {
        (name != null).let { isNameNotNull ->
            if (isNameNotNull) {
                nameLiveData.postValue(user.value?.name)
            }
        }
    }

    fun updateEmailData() {
        (email != null).let { isEmailNotNull ->
            if (isEmailNotNull) {
                emailLiveData.postValue(user.value?.email)
            }
        }
    }

    fun updateImageData() {
        (image != null).let { isImageNotNull ->
            if (isImageNotNull) {
                imageLiveData.postValue(user.value?.image)
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
}