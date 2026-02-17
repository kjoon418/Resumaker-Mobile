package com.resumaker.data.auth.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserDto
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("job") val job: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("date_joined") val dateJoined: String?
)
