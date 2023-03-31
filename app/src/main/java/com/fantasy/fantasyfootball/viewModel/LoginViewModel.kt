package com.fantasy.fantasyfootball.viewModel

import androidx.databinding.ObservableArrayList
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FireStoreUserRepository) :
    BaseViewModel() {
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

    suspend fun login() {
        if (isFormValid()) {
            try {
                val res = safeApiCall { auth.login(email.value!!, password.value!!) }
                if (res == true) {
                    login.emit(Unit)
                    success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
                } else {
                    error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    private suspend fun isFormValid(): Boolean {
        formErrors.clear()
        if (email.value?.trim { it <= ' ' }
                .isNullOrEmpty()
        ) {
            formErrors.add(Enums.FormError.MISSING_EMAIL)
            error.emit(Enums.FormError.MISSING_EMAIL.name)
        } else if (password.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            formErrors.add(Enums.FormError.MISSING_PASSWORD)
            error.emit(Enums.FormError.MISSING_PASSWORD.name)
        }
        return formErrors.isEmpty()
    }
}