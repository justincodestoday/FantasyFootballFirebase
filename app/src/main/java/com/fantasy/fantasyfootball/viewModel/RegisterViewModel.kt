package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepo: UserRepository,
    private val teamRepo: TeamRepository
) : ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String> = MutableLiveData()

    val team: MutableLiveData<Team> = MutableLiveData()
    val teamName: MutableLiveData<String> = MutableLiveData()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    fun register() {
        viewModelScope.launch {
            if (name.value?.trim { it <= ' ' }.isNullOrEmpty()) {
                error.emit(Enums.FormErrors.MISSING_NAME.name)
            } else if (teamName.value?.trim { it <= ' ' }.isNullOrEmpty()) {
                error.emit(Enums.FormErrors.MISSING_TEAM_NAME.name)
            } else if (username.value?.trim { it <= ' ' }
                    .isNullOrEmpty() || username.value?.trim { it <= ' ' }!!.length < 8) {
                error.emit(Enums.FormErrors.INVALID_USERNAME.name)
            } else if (password.value?.trim { it <= ' ' }
                    .isNullOrEmpty() || password.value?.trim { it <= ' ' }!!.length < 8) {
                error.emit(Enums.FormErrors.INVALID_PASSWORD.name)
            } else if (passwordConfirm.value?.trim { it <= ' ' } != password.value?.trim { it <= ' ' }) {
                error.emit(Enums.FormErrors.PASSWORDS_NOT_MATCHING.name)
            } else {
                val user =
                    User(
                        name = name.value,
                        username = username.value,
                        password = password.value
                    )
                val id = userRepo.createUser(user)
                val team = Team(ownerId = id.toInt(), name = teamName.value?.trim())
                teamRepo.createTeam(team)
                success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
//                navigate.emit(Unit)
            }
        }
    }

    class Provider(private val userRepo: UserRepository, private val teamRepo: TeamRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(userRepo, teamRepo) as T
        }
    }
}