package com.example.orderms.initializer

import com.example.orderms.model.Note
import com.example.orderms.model.NoteStatus
import com.example.orderms.model.User
import com.example.orderms.repository.NoteRepository
import com.example.orderms.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val noteRepository: NoteRepository
    ) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val users = listOf(
            User(username = "jdoe", firstName = "John", lastName = "Doe"),
            User(username = "asmith", firstName = "Alice", lastName = "Smith"),
            User(username = "bwayne", firstName = "Bruce", lastName = "Wayne"),
        )
        val now = Date().time
        val notes = listOf(
            Note(text = "expired note 1, 0 sec", expiresAt = Date(), author = users[0]),
            Note(text = "note 1, 10 sec", expiresAt = Date(now + 10_000), author = users[0]),
            Note(text = "note 2, 10 min", expiresAt = Date(now + 600_000), author = users[0]),
            Note(text = "pinned note 1, 10 min", expiresAt = Date(now + 600_000), author = users[0], status = NoteStatus.PINNED),
            Note(text = "note 3, 2 min", expiresAt = Date(now + 120_000), author = users[1]),
            Note(text = "note 4, 5 min", expiresAt = Date(now + 300_000), author = users[1]),
        )
        userRepository.saveAllAndFlush(users)
        noteRepository.saveAllAndFlush(notes)
    }
}
