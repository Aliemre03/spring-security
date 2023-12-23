package com.aed.basic

import com.aed.basic.model.Role
import com.aed.basic.model.UserCreateRequest
import com.aed.basic.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BasicApplication(private val userService: UserService): CommandLineRunner {
	override fun run(vararg args: String?) {
		println("Hello World!")
		createData()
	}

	fun createData() {
		val admin =UserCreateRequest("admin", "admin", "Admin", listOf(Role.ADMIN))
		val user = UserCreateRequest("user", "user", "User", listOf(Role.USER))
		userService.createUser(admin)
		userService.createUser(user)
	}


}

fun main(args: Array<String>) {
	runApplication<BasicApplication>(*args)
}
