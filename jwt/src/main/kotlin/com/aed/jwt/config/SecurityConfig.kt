package com.aed.jwt.config

import com.aed.jwt.model.Role
import com.aed.jwt.security.JwtAuthFilter
import com.aed.jwt.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(private val userService: UserService, private val jwtFilter: JwtAuthFilter){

@Bean
fun passwordEncoder() = BCryptPasswordEncoder()

@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .requestMatchers("/auth/addNewUser/**", "/auth/generateToken/**").permitAll()
                .requestMatchers("/auth/user/**").hasRole(Role.USER.name)
                .requestMatchers("/auth/admin/**").hasRole(Role.ADMIN.name)
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    return http.build()
}

@Bean
fun authenticationProvider(): AuthenticationProvider {
    return DaoAuthenticationProvider().apply {
        setPasswordEncoder(passwordEncoder())
        setUserDetailsService(userService)
    }
}

    @Bean
    fun authenticationManager(authentication: AuthenticationConfiguration): AuthenticationManager? {
        return authentication.authenticationManager
    }


}