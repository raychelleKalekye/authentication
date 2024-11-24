package com.example.studyapp.Data

import com.example.studyapp.Data.database.User
import com.example.studyapp.Data.database.UserDAO
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAO: UserDAO) {
    // Function to get user by username
    fun getUserStream(username: String, password: String): Flow<User?> {
        return userDAO.getUser(username, password) // Make sure this returns a Flow<User?>
    }

    // Function to insert a new user
    suspend fun insertUser(user: User) {
        userDAO.insert(user)
    }

    // Function to delete a user
    suspend fun deleteUser(user: User) {
        userDAO.delete(user)
    }

    // Function to update a user
    suspend fun updateUser(user: User) {
        userDAO.update(user)
    }
}
