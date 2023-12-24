package com.aed.jwt.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class JwtService {
    @Value("\${jwt.key}")
    private val SECRET: String? = null

    fun generateToken(username: String): String {
        val claims = HashMap<String, Any>()
        return  Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 2))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun extractClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
    }

    fun extractUsername(token: String): String {
        return extractClaims(token)["username"] as String
    }

    // Validate token expiration
    fun isTokenExpired(token: String): Boolean {
        val expirationDate: Date = extractClaims(token).expiration
        return expirationDate.before(Date())
    }

}