package com.fantasy.fantasyfootball.viewModel

import android.net.Uri
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.service.ImageStorageService
import com.fantasy.fantasyfootball.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionalViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
    val teamName: MutableLiveData<String> = MutableLiveData()
    val navigate: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

    suspend fun addInfo(email: String, imageUri: Uri?) {
        if (isFormValid()) {
            try {
                val imageName = Utils.getCurrentTime()
                viewModelScope.launch {
                    imageUri?.let {
                        ImageStorageService.addImage(imageUri, imageName) { status ->
                            if (!status) {
                                viewModelScope.launch {
                                    error.emit("Image upload failed")
                                }
                            }
                        }
                        val team = Team(name = teamName.value?.trim())
                        safeApiCall {
                            userRepo.addInfo(email, imageName, team)
                            navigate.emit(Unit)
                        }
                    }
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    suspend fun login() {
        try {
            safeApiCall { userRepo.login(email.value!!, password.value!!) }
            success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
        } catch (e: Exception) {
            error.emit(e.message.toString())
        }
    }

    private suspend fun isFormValid(): Boolean {
        formErrors.clear()
        if (teamName.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            formErrors.add(Enums.FormError.MISSING_TEAM_NAME)
            error.emit(Enums.FormError.MISSING_TEAM_NAME.name)
        }
        return formErrors.isEmpty()
    }
}