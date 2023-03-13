package com.fantasy.fantasyfootball.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.*
import com.fantasy.fantasyfootball.service.ImageStorageService
import com.fantasy.fantasyfootball.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: FireStoreUserRepository,
    private val teamRepo: FireStoreTeamRepository
) : BaseViewModel() {
    val loggedIn: MutableLiveData<Boolean?> = MutableLiveData()

    init {
        viewModelScope.launch {
            val res = safeApiCall { userRepo.getCurrentUser() }
            res?.let {
                user.value = it
                Log.d("debugging", it.toString())
            }
        }
    }

    fun updateProfile(
        imageUri: Uri?
    ) {
        val imageName = user.value?.image ?: Utils.getCurrentTime()
        viewModelScope.launch {
            imageUri?.let {
                ImageStorageService.addImage(imageUri, imageName) { status ->
                    if (!status) {
                        viewModelScope.launch {
                            error.emit("Image GG Already")
                        }
                    }
                }
                user.value?.let {
                    safeApiCall {
                        it.email?.let { uid ->
                            userRepo.updateUser(
                                uid,
                                it.copy(image = imageName)
                            )
                        }
                    }
                    finish.emit(Unit)
                }
            }
        }
    }

    fun editUser(user: User) {
        viewModelScope.launch {
            val _user = userRepo.getCurrentUser()
            _user?.email?.let { userRepo.updateUser(it, user) }
        }
    }

    fun editTeam(teamId: Int, team: Team) {
        viewModelScope.launch {
            teamRepo.editTeam(teamId, team)
        }
    }

    fun getUserWithTeam(userId: Int) {
        viewModelScope.launch {
            val res = userRepo.getUserWithTeam(userId)
            res?.let {
                userTeam.value = it
            }
        }
    }

    fun isLoggedIn() {
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

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

//    class Provider(
//        private val userRepo: UserRepositoryImpl,
//        private val teamRepo: TeamRepositoryImpl
//    ): ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return ProfileViewModel(userRepo, teamRepo) as T
//        }
//    }
}