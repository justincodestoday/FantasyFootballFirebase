package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: UserRepository) : BaseViewModel() {
    val user: MutableSharedFlow<User?> = MutableSharedFlow()

    val username: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()

    suspend fun login() {
        if (username.value?.trim { it <= ' ' }
                .isNullOrEmpty() || password.value?.trim { it <= ' ' }.isNullOrEmpty()
        ) {
            error.emit(Enums.FormError.EMPTY_FIELD.name)
        } else {
//            val existingUser = repo.login(username.value!!, password.value!!)
//            if (existingUser) {
//                user.emit(User(username = username.value, password = password.value))
//                success.emit(Enums.FormSuccess.LOGIN_SUCCESSFUL.name)
//            } else {
//                error.emit(Enums.FormError.WRONG_CREDENTIALS.name)
//            }

            viewModelScope.launch {
                try {
                    safeApiCall { repo.login(username.value!!, password.value!!) }
                    user.emit(User(username = username.value, password = password.value))
                    success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
                } catch (e: Exception) {
                    error.emit(e.message.toString())
                }
            }
        }
    }

//    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return LoginViewModel(repo) as T
//        }
//    }
}