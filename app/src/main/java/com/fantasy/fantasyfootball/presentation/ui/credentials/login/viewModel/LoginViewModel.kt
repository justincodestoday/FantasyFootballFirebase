package com.fantasy.fantasyfootball.presentation.ui.credentials.login.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.common.Resource
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.domain.usecase.LoginUseCase
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import com.fantasy.fantasyfootball.presentation.ui.credentials.login.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel() {
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

//    suspend fun login() {
//        if (isFormValid()) {
//            try {
//                val res = safeApiCall { auth.login(email.value!!, password.value!!) }
//                if (res == true) {
//                    login.emit(Unit)
//                    success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
//                } else {
//                    error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
//                }
//            } catch (e: Exception) {
//                error.emit(e.message.toString())
//            }
//        }
//    }

    fun loginOnClick() {
        viewModelScope.launch {
            login()
        }
    }

    suspend fun login() {
        if (isFormValid()) {
            viewModelScope.launch {
                safeApiCall {
                    loginUseCase(
                        LoginEvent.Login(
                            User(email = email.value, password = password.value)
                        )
                    ).onEach {
                        when (it) {
                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                login.emit(Unit)
                                success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
                            }

                            is Resource.Error -> {
                                error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
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