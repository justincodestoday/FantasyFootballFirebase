package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FireStoreUserRepository
) : BaseViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String> = MutableLiveData()

    val team: MutableLiveData<Team> = MutableLiveData()
    val teamName: MutableLiveData<String> = MutableLiveData()

    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
//    val error: MutableSharedFlow<String> = MutableSharedFlow()

    suspend fun register() {
        if (name.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            error.emit(Enums.FormError.MISSING_NAME.name)
        } else if (teamName.value?.trim { it <= ' ' }.isNullOrEmpty()) {
            error.emit(Enums.FormError.MISSING_TEAM_NAME.name)
        } else if (username.value?.trim { it <= ' ' }
                .isNullOrEmpty() || username.value?.trim { it <= ' ' }!!.length < 8) {
            error.emit(Enums.FormError.INVALID_USERNAME.name)
        } else if (password.value?.trim { it <= ' ' }
                .isNullOrEmpty() || password.value?.trim { it <= ' ' }!!.length < 8) {
            error.emit(Enums.FormError.INVALID_PASSWORD.name)
        } else if (passwordConfirm.value?.trim { it <= ' ' } != password.value?.trim { it <= ' ' }) {
            error.emit(Enums.FormError.PASSWORDS_NOT_MATCHING.name)
        } else {
            viewModelScope.launch {
                safeApiCall {
                    auth.createUser(
                        User(
                            name = name.value,
                            username = username.value,
                            password = password.value
                        )
                    )
                }
                finish.emit(Unit)
//            val existingUser = userRepo.getUserByUsername(username.value!!)
//            if (existingUser == null) {
//                val user =
//                    User(
//                        name = name.value,
//                        username = username.value,
//                        password = password.value
//                    )
//                val id = userRepo.createUser(user)
//                //new line
////                userRepo.register(user)
//                val team = Team(ownerId = id.toInt(), name = teamName.value?.trim())
//                teamRepo.createTeam(team)
//                success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
//            } else {
//                error.emit(Enums.FormError.USER_EXISTS.name)
//            }
//        }
            }
        }
    }

//    class Provider(private val userRepo: UserRepositoryImpl, private val teamRepo: TeamRepository) :
//        ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RegisterViewModel(userRepo, teamRepo) as T
//        }
//    }
}