package com.aed.jwt.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    private val username: String,
    private val password: String,
    private val name: String,

    private val accountNonExpired: Boolean = true,
    private val isEnabled: Boolean = true,
    val accountNonLocked: Boolean = true,
    val credentialsNonExpired: Boolean = true,
    val roles: List<Role>
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> {
        return roles//convert roles to GrantedAuthority
    }
    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }
}