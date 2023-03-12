package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val auth: FireStoreUserRepository) : BaseViewModel() {
//    val user: MutableLiveData<User?> = MutableLiveData()
    val loggedIn: MutableLiveData<Boolean> = MutableLiveData()

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
            val res = auth.getCurrentUser()
            user.value = res
        }
    }

    fun isLoggedIn(): Boolean? {
        viewModelScope.launch {
            val res = auth.isAuthenticate()
            res.let {
                loggedIn.value = it
            }
        }
        return loggedIn.value
    }

//    class Provider(private val userRepo: UserRepositoryImpl) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return MainViewModel(userRepo) as T
//        }
//    }
}