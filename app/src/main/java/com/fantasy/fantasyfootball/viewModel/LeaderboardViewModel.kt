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
class LeaderboardViewModel @Inject constructor(val repo: FireStoreUserRepository) : ViewModel() {
    val users: MutableLiveData<List<User>> = MutableLiveData()

//    fun getUsersWithTeams() {
//        viewModelScope.launch {
//            users.value = repo.getUsersWithTeams().sortedWith(compareBy() {
//                it.team.points
//            }).reversed()
//        }
//    }
}