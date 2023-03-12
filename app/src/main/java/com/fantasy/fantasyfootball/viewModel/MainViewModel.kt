package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
//    fun getUserById(userId: Int) {
//        viewModelScope.launch {
//            userRepo.getUserById(userId).collect {
//                if (it != null) {
//                    user.value = it
//                }
//            }
//        }
//    }

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

    fun isLoggedIn(): Boolean? {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.isAuthenticated() }
                res.let {
                    loggedIn.value = res
                }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
        return loggedIn.value
    }

    fun logout() {
        viewModelScope.launch {
            try {
                safeApiCall { userRepo.deAuthenticate() }
                success.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

//    class Provider(private val userRepo: UserRepositoryImpl) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return MainViewModel(userRepo) as T
//        }
//    }
}