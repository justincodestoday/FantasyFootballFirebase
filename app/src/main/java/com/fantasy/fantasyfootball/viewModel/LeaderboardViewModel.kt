package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(private val auth: FireStoreUserRepository) :
    BaseViewModel() {
    val users: MutableLiveData<List<User>> = MutableLiveData()

    fun getUsers() {
        viewModelScope.launch {
            users.value = auth.getAllUsers()
        }
    }
}