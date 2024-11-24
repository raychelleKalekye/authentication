package com.example.studyapp.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.Data.UserRepository
import com.example.studyapp.Data.database.User
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun signUp(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {

                userRepository.insertUser(User(username = username, password = password))
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}
