package com.example.testcode.repository

import com.example.testcode.data.model.ParentResponse
import com.example.testcode.data.network.ApiService
import com.example.testcode.data.pref.MyPreferences
import com.example.testcode.util.Resource
import retrofit2.HttpException
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: ApiService,
    private val preferences: MyPreferences
) : MainRepository {
    override suspend fun getParentLogin(
        userName: String,
        password: String
    ): Resource<ParentResponse> {
        return try {
            val response = api.loginParent(userName, password)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result)
            else
                Resource.Failure(false, response.code(), response.errorBody())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    Resource.Failure(true, null, null)
                }
            }
        }
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}