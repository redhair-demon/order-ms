package com.example.orderms.controller

import com.example.orderms.model.User
import com.example.orderms.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @GetMapping("/admin/all")
    fun getAllUsers() = userService.getAllUsers()

    @DeleteMapping("/admin/delete")
    fun deleteUser(@RequestParam username: String) = userService.deleteUser(username)

    @GetMapping
    fun getUser(@RequestParam username: String) = userService.getUserByUsername(username)

    @PostMapping
    fun createUser(@RequestBody user: User) = userService.createUser(user)

    @PatchMapping("/edit")
    fun editUser(
        @RequestParam username: String,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
    ): User {
        val user = getUser(username)
        if (!firstName.isNullOrBlank()) user.firstName = firstName
        if (!lastName.isNullOrBlank()) user.lastName = lastName
        return createUser(user)
    }

}
