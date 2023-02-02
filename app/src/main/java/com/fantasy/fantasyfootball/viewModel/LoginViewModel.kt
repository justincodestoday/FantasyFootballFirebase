package com.fantasy.fantasyfootball.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    val user: MutableSharedFlow<User?> = MutableSharedFlow()

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    fun login() {
        Log.d("debug", "${username.value}")
        viewModelScope.launch {
            if (username.value?.trim { it <= ' ' }
                    .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
            ) {
                Log.d("debugging", "error")
                error.emit(Enums.FormErrors.EMPTY_FIELD.name)
    } else {
        Log.d("debugging", "login")
        validateUser(username.value!!, password.value!!)
    }
}
}

suspend fun validateUser(_username: String, _password: String) {

    Log.d("debugging", "$_username and $_password")

//        viewModelScope.launch(Dispatchers.IO) {
    Log.d("debugging", "$_username and $_password")
    val _user = repo.isValidUser(User(username = _username, password = _password))
            user.emit(_user)

//        }
    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}