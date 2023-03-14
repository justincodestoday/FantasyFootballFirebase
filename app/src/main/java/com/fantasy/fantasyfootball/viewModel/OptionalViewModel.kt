package com.fantasy.fantasyfootball.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionalViewModel @Inject constructor(
    private val userRepo: FireStoreUserRepository,
    private val teamRepo: FireStoreTeamRepository
) :
    BaseViewModel() {
    val navigate: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<Enums.FormError>()

    suspend fun registerTeam(email: String) {
        if (isFormValid()) {
            try {
                val team = Team(name = teamName.value?.trim())
                safeApiCall {
                    teamRepo.registerTeam(team, teamName.value!!) {
                        if (it == Enums.FormError.TEAM_NAME_EXISTS.name) {
                            viewModelScope.launch {
                                error.emit(Enums.FormError.TEAM_NAME_EXISTS.name)
                            }
                        }
                        if (it == "No duplicates") {
                            viewModelScope.launch {
                                navigate.emit(Unit)
                            }
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