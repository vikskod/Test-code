package com.example.testcode.data.model

import com.google.gson.annotations.SerializedName

data class StudentResponse(
    @SerializedName("auth_token")
    val authToken: String,
    @SerializedName("parent_id")
    val parentId: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("username")
    val username: String
)