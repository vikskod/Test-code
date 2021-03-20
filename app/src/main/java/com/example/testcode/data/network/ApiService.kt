package com.example.testcode.data.network

import com.example.testcode.data.model.ParentResponse
import com.example.testcode.data.model.StudentResponse
import retrofit2.http.*

interface ApiService {

    // Login as a parent
    @FormUrlEncoded
    @POST("login")
    suspend fun loginParent(
        @Field("username") username: String,
        @Field("password") password: String,
    ): ParentResponse

    // Login as a student
    @FormUrlEncoded
    @POST("login")
    suspend fun loginStudent(
        @Field("username") username: String,
        @Field("password") password: String,
    ): StudentResponse

    // Login as a student from parent account
    @POST("login/student/{student-user-id}")
    suspend fun loginParentAsStudent(
        @Header("Authorization") auth: String,
        @Path("student-user-id") studentId: String,
    ): StudentResponse
}