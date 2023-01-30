package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    fun login() {
        viewModelScope.launch {
            if (username.value?.trim { it <= ' ' }
                    .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
            ) {
                error.emit(Enums.FormErrors.EMPTY_FIELD.name)
            } else {
                validateUser(username.value!!, password.value!!)
            }
        }
    }

    fun validateUser(_username: String, _password: String) {
        viewModelScope.launch {
            repo.isValidUser(User(username = _username, password = _password)).collect {
                userLiveData.postValue(it)
            }
        }
    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}