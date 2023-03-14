package com.fantasy.fantasyfootball.viewModel

import android.util.Patterns
import androidx.databinding.ObservableArrayList
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
    val register: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

    suspend fun register() {
        if (isFormValid()) {
            try {
                val user = User(
                    name = name.value,
                    email = email.value,
                    password = password.value,
                )
                val res = safeApiCall { userRepo.register(user) }
                if (res != null) {
                    safeApiCall {  }
                    register.emit(Unit)
                    success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
                } else {
                    error.emit(Enums.FormError.USER_EXISTS.name)
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    private suspend fun isFormValid(): Boolean {
        formErrors.clear()
        if (name.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            formErrors.add(Enums.FormError.MISSING_NAME)
            error.emit(Enums.FormError.MISSING_NAME.name)
        } else if (email.value?.trim { it <= ' ' }.isNullOrEmpty() ||
            !Patterns.EMAIL_ADDRESS.matcher("${email.value?.trim { it <= ' ' }}").matches()
        ) {
            formErrors.add(Enums.FormError.INVALID_EMAIL)
            error.emit(Enums.FormError.INVALID_EMAIL.name)
        } else if (password.value?.trim { it <= ' ' }
                .isNullOrEmpty() || password.value?.trim { it <= ' ' }!!.length < 8) {
            formErrors.add(Enums.FormError.INVALID_PASSWORD)
            error.emit(Enums.FormError.INVALID_PASSWORD.name)
        } else if (passwordConfirm.value?.trim { it <= ' ' } != password.value?.trim { it <= ' ' }) {
            formErrors.add(Enums.FormError.PASSWORDS_NOT_MATCHING)
            error.emit(Enums.FormError.PASSWORDS_NOT_MATCHING.name)
        }
        return formErrors.isEmpty()
    }
}