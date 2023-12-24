package com.aed.jwt.dto

import com.aed.jwt.model.Role

data class UserCreateRequest(
    val name: String,
    val username: String,
    val password: String,
    val role: List<Role>
)
