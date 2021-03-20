package com.example.testcode.repository

import com.example.testcode.data.UserPreferences
import com.example.testcode.data.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferences: UserPreferences?
) : BaseRepository() {

    suspend fun loginParent(
        username: String,
        password: String
    ) = safeApiCall { apiService.loginParent(username, password) }

    suspend fun loginStudent(
        username: String,
        password: String
    ) = safeApiCall { apiService.loginStudent(username, password) }

    suspend fun loginParentAsStudent(
        header: String,
        studentId: String
    ) = safeApiCall { apiService.loginParentAsStudent(header, studentId) }

    suspend fun saveAuthToken(token: String) {
        preferences?.saveAuthToken(token)
    }

}