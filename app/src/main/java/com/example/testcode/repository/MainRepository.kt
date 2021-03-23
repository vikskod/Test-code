package com.example.testcode.repository

import com.example.testcode.data.model.ParentResponse
import com.example.testcode.util.Resource

interface MainRepository {

    suspend fun getParentLogin(userName:String, password: String): Resource<ParentResponse>
}