package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepo: UserRepository) : ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            userRepo.getUserById(userId).collect {
                if (it != null) {
                    user.value = it
                }
            }
        }
    }

    class Provider(private val userRepo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(userRepo) as T
        }
    }
}