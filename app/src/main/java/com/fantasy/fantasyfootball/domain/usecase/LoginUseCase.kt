package com.fantasy.fantasyfootball.domain.usecase

import com.fantasy.fantasyfootball.common.Resource
import com.fantasy.fantasyfootball.domain.repository.UserRepository
import com.fantasy.fantasyfootball.presentation.ui.credentials.login.LoginEvent
import com.fantasy.fantasyfootball.util.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authService: UserRepository
) {
    operator fun invoke(event: LoginEvent) = flow {
        try {
            when (event) {
                is LoginEvent.Login -> {
                    emit(Resource.Loading(true))
                    val res = authService.login(event.user.email!!, event.user.password!!)
                    emit(Resource.Success(res))
//                    event.user.email?.let { email ->
//                        event.user.password?.let { pass ->
//                            if (Utils.validate(email, pass)) {
//                                emit(Resource.Loading(true))
//                                val res = authService.login(email, pass)
//                                emit(Resource.Success(res))
//                            } else {
//                                emit(Resource.Error("Validation failed"))
//                            }
//                        } ?: emit(Resource.Error("Password is null"))
//                    } ?: emit(Resource.Error("Email is null"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}