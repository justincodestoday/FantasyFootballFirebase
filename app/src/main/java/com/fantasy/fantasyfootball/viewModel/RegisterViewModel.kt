package com.fantasy.fantasyfootball.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
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

    val navigate: MutableSharedFlow<Unit> = MutableSharedFlow()

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

    val nameError = MediatorLiveData<String>().apply {
        value = ""
        addSource(name) {
            value = validateName(it)
        }
    }

    val usernameError = MediatorLiveData<String>().apply {
        value = ""
        addSource(username) {
            value = validateUsername(it)
        }
    }

//    val passwordError = MediatorLiveData<String>().apply {
//        value = ""
//        addSource(password) {
//            value = validatePassword(it)
//        }
//    }

    val passwordError = ObservableField<String>()

    fun btnClick() {
        if (password.value?.isEmpty() == true) {
            passwordError.set("Empty password")
            return
        }

        if (password.value?.length!! < 7) {
            passwordError.set("Invalid pass")
        } else {
            passwordError.set(null)
        }
    }

    fun validateName(name: String?): String? {
        return when {
            name.isNullOrEmpty() -> "Invalid name"
            else -> null
        }
    }

    fun validateUsername(username: String?): String? {
        return when {
            username.isNullOrEmpty() -> "Invalid username"
            else -> null
        }
    }

    fun validatePassword(password: String?): String? {
        return when {
            password.isNullOrEmpty() -> "Invalid password"
            password.length < 8 -> "Password too short"
            else -> null
        }
    }

    private fun validate(name: MutableLiveData<String>): Boolean {
        if (name.value.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun register() {
        viewModelScope.launch {
            val user =
                User(name = name.value?.trim(), username = username.value?.trim(), password = password.value?.trim())
//            if (password.value?.isEmpty() == true) {
//                passwordError.set("Empty password")
//                return@launch
//            }
//
//            if (password.value?.length!! < 7) {
//                passwordError.set("Invalid pass")
//            } else {
//                passwordError.set(null)
//            }
//            repo.createUser(user)
            if (name.value.isNullOrEmpty() || username.value.isNullOrEmpty() || password.value.isNullOrEmpty() || passwordConfirm.value.isNullOrEmpty()) {
                error.emit(Enums.FormErrors.MISSING_NAME.name)
            } else {
                repo.createUser(user)
                success.emit(Enums.FormSuccess.REGISTER_SUCCESSFUL.name)
//                navigate.emit(Unit)
            }
        }
    }

    class Provider(private val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(repo) as T
        }
    }
}