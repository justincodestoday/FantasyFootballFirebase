package com.fantasy.fantasyfootball.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String> = MutableLiveData()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

//    val formErrors = ObservableArrayList<Enums.FormErrors>()

//    fun isFormValid(): Boolean {
//        formErrors.clear()
//        if (name.value.isNullOrEmpty()) {
//            formErrors.add(Enums.FormErrors.MISSING_NAME)
//        }
//        // all the other validation you require
//        return formErrors.isEmpty()
//    }

//    private fun validate(vararg list: MutableLiveData<String>): Boolean {
//        for (field in list) {
//            if (field.value.isNullOrBlank()) {
//                return false
//            }
//        }
//        return true
//    }

    private fun validate(name: MutableLiveData<String>): Boolean {
        if (name.value.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun register() {
        viewModelScope.launch {
            val user =
                User(name = name.value, username = username.value, password = password.value)
//            repo.createUser(user)
            if (!name.value.isNullOrEmpty() || !username.value.isNullOrEmpty() || !password.value.isNullOrEmpty() || passwordConfirm.value.isNullOrEmpty()) {
                error.emit("Not filled")
            } else {
                repo.createUser(user)
                success.emit(R.string.register_successfully.toString())
            }
        }
    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(repo) as T
        }
    }
}