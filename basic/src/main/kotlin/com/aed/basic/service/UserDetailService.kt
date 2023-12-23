package com.aed.basic.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userService: UserService): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getByUsername(username)
        return user.takeUnless { it == null } ?: run {
            throw Exception("User not found")
        }

    }
}