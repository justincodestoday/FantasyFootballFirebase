package com.fantasy.fantasyfootball.viewModel

import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val res = repo.getUserByCredentials(username, password)
            res?.let {
                user.value = it
            }
        }
    }

//    private val _loginForm = MutableLiveData<LoginFormState>()
//    val loginFormState: LiveData<LoginFormState> = _loginForm
//
//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

//    fun login(username: String, password: String) {
//        // can be launched in a separate asynchronous job
//        val result = userRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
//    }

//    fun loginDataChanged(username: String, password: String) {
//        if (!isUserNameValid(username)) {
//            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
//        } else if (!isPasswordValid(password)) {
//            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
//        } else {
//            _loginForm.value = LoginFormState(isDataValid = true)
//        }
//    }

    // A placeholder username validation check
//    private fun isUserNameValid(username: String): Boolean {
//        return if (username.contains("@")) {
//            Patterns.EMAIL_ADDRESS.matcher(username).matches()
//        } else {
//            username.isNotBlank()
//        }
//    }

    // A placeholder password validation check
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 5
//    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}