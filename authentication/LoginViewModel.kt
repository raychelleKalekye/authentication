package com.example.studyapp.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.Data.UserRepository
import kotlinx.coroutines.launch
open class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    open fun login(username: String, password: String, onLoginSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            userRepository.getUserStream(username, password).collect { user ->
                if (user != null && user.password == password) {
                    onLoginSuccess()
                } else {
                    onFailure()
                }
            }
        }
    }
}
