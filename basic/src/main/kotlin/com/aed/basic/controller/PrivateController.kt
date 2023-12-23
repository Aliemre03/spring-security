package com.aed.basic.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/private")
class PrivateController {
    @GetMapping
    fun private() = "Hello from Private"

    @GetMapping("/user")
    fun privateUser() = "Private User"

    @GetMapping("/admin")
    fun privateAdmin() = "Private Admin"
}