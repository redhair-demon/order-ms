package com.example.orderms.service

import com.example.orderms.model.User
import com.example.orderms.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserByUsername(username: String): User = userRepository.findById(username).orElseThrow()

    fun createUser(user: User): User {
        if (userRepository.existsById(user.username)) throw IllegalArgumentException("User already exists")
        return userRepository.save(user)
    }

    fun editUser(user: User): User = userRepository.save(user)

    fun deleteUser(username: String) = userRepository.deleteById(username)

}
