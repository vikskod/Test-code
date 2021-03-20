package com.example.testcode.data.model

import com.google.gson.annotations.SerializedName

data class ParentResponse(
    @SerializedName("auth_token")
    val authToken: String,
    @SerializedName("student_ids")
    val studentIds: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("username")
    val username: String
)