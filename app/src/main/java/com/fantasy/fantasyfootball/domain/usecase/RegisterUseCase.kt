package com.fantasy.fantasyfootball.domain.usecase

import com.fantasy.fantasyfootball.common.Resource
import com.fantasy.fantasyfootball.data.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.ui.presentation.register.RegisterEvent
import com.fantasy.fantasyfootball.util.Utils
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authService: FireStoreUserRepository
) {
    operator fun invoke(event: RegisterEvent) = flow<Resource<FirebaseUser?>> {
        try {
            when (event) {
                is RegisterEvent.Register -> {
                    event.user.email?.let { email ->
                        event.user.password?.let { pass ->
                            event.user.name?.let { name ->
                                if (Utils.validate(email, pass, name)) {
                                    emit(Resource.Loading())
                                    val res = authService.register(event.user)
                                    res?.let {
                                        emit(Resource.Success(it))
                                    } ?: emit(Resource.Error("Failed to register user"))
                                } else {
                                    emit(Resource.Error("Validation failed"))
                                }
                            } ?: emit(Resource.Error("Username is null"))
                        } ?: emit(Resource.Error("Password is null"))
                    } ?: emit(Resource.Error("Email is null"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}