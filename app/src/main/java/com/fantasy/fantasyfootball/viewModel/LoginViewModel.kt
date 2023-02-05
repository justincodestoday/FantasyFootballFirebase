package com.fantasy.fantasyfootball.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    val user: MutableSharedFlow<User?> = MutableSharedFlow()

    val username: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    fun login() {
        viewModelScope.launch {
            if (username.value?.trim { it <= ' ' }
                    .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
            ) {
                error.emit(Enums.FormErrors.EMPTY_FIELD.name)
            } else {
                Log.d("debugging", "value ${username.value} ${password.value}")
                val result = async { validateUser(username.value!!, password.value!!) }
                result.await()
                username.value = null
                password.value = null
                Log.d("debugging", "null ${username.value} ${password.value}")
            }
        }

    }

    fun validateUser(_username: String, _password: String) {
        viewModelScope.launch {
            repo.isValidUser(User(username = _username, password = _password)).collect {
                user.emit(it)
            }
        }
    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}