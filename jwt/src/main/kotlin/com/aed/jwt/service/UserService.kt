package com.aed.jwt.service

import com.aed.jwt.model.User
import com.aed.jwt.dto.UserCreateRequest
import com.aed.jwt.repository.UserRepository

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder):
    UserDetailsService {

    fun getByUsername(username: String) = userRepository.findByUsername(username)

    fun createUser(request: UserCreateRequest): User {
        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User(
            name = request.name,
            username = request.username,
            password = encodedPassword,
            roles = request.role,
        )
        return userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
        return user ?: throw Exception("User not found")
    }

}