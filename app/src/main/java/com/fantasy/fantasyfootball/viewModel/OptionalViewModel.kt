package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Account
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreAccountRepository
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionalViewModel @Inject constructor(
    private val userRepo: FireStoreUserRepository,
    private val teamRepo: FireStoreTeamRepository,
    private val accountRepo: FireStoreAccountRepository
) :
    BaseViewModel() {
    val navigate: MutableSharedFlow<Unit> = MutableSharedFlow()

    suspend fun registerTeam() {
        if (teamName.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            error.emit(Enums.FormError.MISSING_TEAM_NAME.name)
        } else {
            viewModelScope.launch {
                try {
                    proceed()
                } catch (e: Exception) {
                    error.emit(e.message.toString())
                }
            }
        }
    }

    fun proceed() {
        viewModelScope.launch {
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
                            getCurrentUser()
                            val user = user.value
//                            user.copy(user.)
                            val account = Account(user = user.value, team = team)
                            accountRepo.registerAccount(account)
                            navigate.emit(Unit)
                        }
                    }
                }
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}