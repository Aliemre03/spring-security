package com.aed.inMemory.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build()
        val admin = User.withUsername("admin")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .headers {
                it.frameOptions {
                    it.disable()
                }
            }
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers("/public/**", "/auth/**").permitAll()
                it.requestMatchers("/private/user/**").hasRole("USER")
                it.requestMatchers("/private/admin/**").hasRole("ADMIN")
                it.anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }


}