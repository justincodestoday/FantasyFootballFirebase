package com.fantasy.fantasyfootball.presentation.ui.credentials.login.viewModel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.common.Resource
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.domain.usecase.LoginUseCase
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import com.fantasy.fantasyfootball.presentation.ui.credentials.login.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel() {
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

    lateinit var viewModelJob: CompletableJob
    lateinit var scope: CoroutineScope

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

//    fun onLoginClicked() {
//        viewModelScope.launch {
//            login()
//        }
//    }

    override fun onViewCreated() {
        super.onViewCreated()

        viewModelJob = SupervisorJob()
        scope = CoroutineScope(Dispatchers.Main + viewModelJob)
        Log.d("debugging", scope.coroutineContext.job.toString())
    }

    suspend fun login() {
        if (isFormValid()) {
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
                }.launchIn(scope)
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

    fun clear() {
        viewModelJob.cancel()
        Log.d("debugging", scope.coroutineContext.job.toString())
    }
}