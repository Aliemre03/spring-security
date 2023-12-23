package com.aed.basic.service

import com.aed.basic.model.User
import com.aed.basic.model.UserCreateRequest
import com.aed.basic.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun getByUsername(username: String) = userRepository.findByUsername(username)

    fun createUser(request: UserCreateRequest): User {
        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User(
            name = request.name,
            username = request.username,
            password = encodedPassword,
            roles = request.role,
        )
       return  userRepository.save(user)
    }

}