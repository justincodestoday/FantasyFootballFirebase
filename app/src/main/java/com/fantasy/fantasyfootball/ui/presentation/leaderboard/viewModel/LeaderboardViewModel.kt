package com.fantasy.fantasyfootball.ui.presentation.leaderboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(private val auth: FireStoreUserRepository) :
    BaseViewModel() {
    // can use this or MutableStateFlow in init
    val users: MutableLiveData<List<User>> = MutableLiveData()

    // MutableSharedFlow only works after UI completely renders
    val isLoading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isRefreshing: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override fun onViewCreated() {
        super.onViewCreated()
        getAllUsers()
    }

    // call even before UI is completely rendered
//    init {
//        getUsers()
//    }

    private fun getAllUsers() {
        viewModelScope.launch {
            isLoading.emit(true)
            delay(2000)
            users.value = auth.getAllUsers()
            isLoading.emit(false)
        }
    }

    fun getUsersBySwipe() {
        viewModelScope.launch {
            isRefreshing.emit(true)
            users.value = auth.getAllUsers()
            isRefreshing.emit(false)
        }
    }
}