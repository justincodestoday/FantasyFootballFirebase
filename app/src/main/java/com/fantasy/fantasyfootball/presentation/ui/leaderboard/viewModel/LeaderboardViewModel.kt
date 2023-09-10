package com.fantasy.fantasyfootball.presentation.ui.leaderboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(private val auth: UserRepositoryImpl) :
    BaseViewModel() {
    // can use this or MutableStateFlow in init
    val users: MutableLiveData<List<User>> = MutableLiveData()
    // MutableSharedFlow only works after UI completely renders
    val isLoading: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override fun onViewCreated() {
        super.onViewCreated()
        getAllUsers()
    }

    // call even before UI is completely rendered
//    init {
//        getUsers()
//    }

    fun getAllUsers() {
        viewModelScope.launch {
            isLoading.emit(true)
//            delay(2000)
            users.value = auth.getAllUsers()
            isLoading.emit(false)
        }
    }
}