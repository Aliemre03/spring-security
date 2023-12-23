package com.aed.basic.model

data class UserCreateRequest(
    val name: String,
    val username: String,
    val password: String,
    val role: List<Role>
)
