package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(private val repo: FireStoreUserRepository) :
    BaseViewModel() {
    val users: MutableLiveData<List<User>> = MutableLiveData()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            users.value = repo.getAllUsers()
        }
    }
}