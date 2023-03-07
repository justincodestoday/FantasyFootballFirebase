package com.fantasy.fantasyfootball.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepo: UserRepository,
//    private val teamRepo: TeamRepository
) : BaseViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String> = MutableLiveData()

    val team: MutableLiveData<Team> = MutableLiveData()
    val teamName: MutableLiveData<String> = MutableLiveData()

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
//            val existingUser = userRepo.getUserByUsername(username.value!!)
//            if (existingUser == null) {
//                val user =
//                    User(
//                        name = name.value,
//                        username = username.value,
//                        password = password.value
//                    )
//                val id = userRepo.createUser(user)
//                val team = Team(ownerId = id.toInt(), name = teamName.value?.trim())
//                teamRepo.createTeam(team)
//                success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
//            } else {
//                error.emit(Enums.FormError.USER_EXISTS.name)
//            }

            viewModelScope.launch {
                try {
                    val user = User(
                        name = name.value,
                        username = username.value,
                        password = password.value
                    )
                    val res = safeApiCall { userRepo.register(user) }
                    if (res != null) {
                        success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
                    } else {
                        error.emit(Enums.FormError.USER_EXISTS.name)
                    }
                } catch (e: Exception) {
                    error.emit(e.message.toString())
                }
            }
        }
    }

//    class Provider(private val userRepo: UserRepository, private val teamRepo: TeamRepository) :
//        ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RegisterViewModel(userRepo, teamRepo) as T
//        }
//    }
}