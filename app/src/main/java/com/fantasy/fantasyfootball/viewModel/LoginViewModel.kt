package com.fantasy.fantasyfootball.viewModel


import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: FireStoreUserRepository) : BaseViewModel() {
    suspend fun login() {
        if (username.value?.trim { it <= ' ' }
                .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
        ) {
            error.emit(Enums.FormError.EMPTY_FIELD.name)
        } else {

//            val existingUser = repo.login(username.value!!, password.value!!)
//            if (existingUser) {
//                user.emit(User(username = username.value, password = password.value))
//                success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
//            } else {
//                error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
//            }

            viewModelScope.launch {
                try {
                    val res = safeApiCall { repo.login(username.value!!, password.value!!) }
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
    }
}