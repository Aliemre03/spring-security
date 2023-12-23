package com.aed.basic.config

import com.aed.basic.model.Role
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity, interseptor: HandlerMappingIntrospector): SecurityFilterChain {

        val mvcRequestBuilder = MvcRequestMatcher.Builder(interseptor) //h2 console

        http
            .headers {
                it.frameOptions {
                    it.disable()
                }
            }
            .csrf {
                it.disable()
                it.ignoringRequestMatchers(mvcRequestBuilder.pattern("/public/**"))
                it.ignoringRequestMatchers(PathRequest.toH2Console())
            }
            .formLogin {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers(mvcRequestBuilder.pattern("/public/**")).permitAll()
                it.requestMatchers(mvcRequestBuilder.pattern("/private/**")).authenticated()
                it.requestMatchers(PathRequest.toH2Console()).hasRole(Role.ADMIN.name)
                it.anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }
}