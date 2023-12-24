package com.aed.jwt.dto

data class AuthRequest(
    private val username: String,
    private val password: String
) {
}