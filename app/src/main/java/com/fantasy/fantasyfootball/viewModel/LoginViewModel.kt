package com.fantasy.fantasyfootball.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: FireStoreUserRepository) :
    BaseViewModel() {
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

//    suspend fun login() {
//        if (email.value?.trim { it <= ' ' }
//                .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
//        ) {
//            error.emit(Enums.FormError.EMPTY_FIELD.name)
//        } else {
//            val existingUser = repo.login(username.value!!, password.value!!)
//            if (existingUser) {
//                user.emit(User(username = username.value, password = password.value))
//                success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
//            } else {
//                error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
//            }
//        }
//    }

    suspend fun login() {
        if (isFormValid()) {
            try {
                val res = safeApiCall { repo.login(email.value!!, password.value!!) }
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