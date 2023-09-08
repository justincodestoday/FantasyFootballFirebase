package com.fantasy.fantasyfootball.domain.usecase

import com.fantasy.fantasyfootball.common.Resource
import com.fantasy.fantasyfootball.data.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.service.AuthService
import com.fantasy.fantasyfootball.ui.presentation.login.LoginEvent
import com.fantasy.fantasyfootball.util.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authService: FireStoreUserRepository
) {
    operator fun invoke(event: LoginEvent) = flow {
        try {
            when (event) {
                is LoginEvent.Login -> {
                    event.user.email?.let { email ->
                        event.user.password?.let { pass ->
                            if (Utils.validate(email, pass)) {
                                emit(Resource.Loading(true))
                                val res = authService.login(email, pass)
                                emit(Resource.Success(res))
                            } else {
                                emit(Resource.Error("Validation failed"))
                            }
                        } ?: emit(Resource.Error("Password is null"))
                    } ?: emit(Resource.Error("Email is null"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}