package com.aed.jwt.controller

import com.aed.jwt.dto.AuthRequest
import com.aed.jwt.dto.UserCreateRequest
import com.aed.jwt.service.JwtService
import com.aed.jwt.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class UserController(private val userService: UserService, private val jwtService: JwtService, private val authenticationManager: AuthenticationManager) {

    @PostMapping("/addNewUser")
    fun addNewUser(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<String> {
        userService.createUser(userCreateRequest)
        return ResponseEntity.ok("User created successfully")
    }

    @GetMapping("/user")
    fun user(): ResponseEntity<String> {
        return ResponseEntity.ok("User endpoint")
    }

    @GetMapping("/admin")
    fun admin(): ResponseEntity<String> {
        return ResponseEntity.ok("Admin endpoint")
    }

    @PostMapping("/generateToken")
    fun generateToken(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password))
        return if (auth.isAuthenticated) {
            ResponseEntity.ok(jwtService.generateToken(authRequest.username))
        } else {
            ResponseEntity.badRequest().body("Invalid username/password")
        }
    }
}