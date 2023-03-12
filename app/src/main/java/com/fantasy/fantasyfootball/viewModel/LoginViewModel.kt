package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FireStoreUserRepository) : BaseViewModel() {
//    val user: MutableSharedFlow<User?> = MutableSharedFlow()

//    val userPair: MutableSharedFlow<Pair<User?, Boolean?>> = MutableSharedFlow()

    val username: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()

//    val error: MutableSharedFlow<String> = MutableSharedFlow()

    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()

    suspend fun login() {
        if (username.value?.trim { it <= ' ' }
                .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
        ) {
            error.emit(Enums.FormError.EMPTY_FIELD.name)
        } else {
            val existingUser = auth.login(username.value!!, password.value!!)
//            val existingUser = repo.login(username.value!!, password.value!!)
            if (existingUser != null) {
//                user.emit(existingUser)
//                userPair.emit(
//                    Pair(
//                        User(username = username.value, password = password.value),
//                        existingUser
//                    )
//                )
                success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
            } else {
                error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
            }
        }
    }

//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            val res = safeApiCall {
//                auth.login(email, password)
//            }
//            if(res != null) {
//                loginFinish.emit(Unit)
//            } else {
//                error.emit("Login failed")
//            }
//        }
//    }

//    fun login() {
//        viewModelScope.launch {
//            if (username.value?.trim { it <= ' ' }
//                    .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
//            ) {
//                error.emit(Enums.FormError.EMPTY_FIELD.name)
//            } else {
//                Log.d("debugging", "value ${username.value} ${password.value}")
//                val result = async { validateUser(username.value!!, password.value!!) }
//                result.await()
//                username.value = null
//                password.value = null
//                Log.d("debugging", "null ${username.value} ${password.value}")
//            }
//        }
//    }

//    fun validateUser(_username: String, _password: String) {
//        viewModelScope.launch {
//            repo.isValidUser(User(username = _username, password = _password)).collect {
//                user.emit(it)
//            }
//        }
//    }

//    class Provider(private val repo: UserRepositoryImpl) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return LoginViewModel(repo) as T
//        }
//    }
}