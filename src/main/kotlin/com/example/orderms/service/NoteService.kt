package com.example.orderms.service

import com.example.orderms.model.Note
import com.example.orderms.model.NoteStatus
import com.example.orderms.repository.NoteRepository
import com.example.orderms.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NoteService(private val noteRepository: NoteRepository, private val userRepository: UserRepository) {

    fun getAllNotes(): List<Note> = noteRepository.findAll(Sort.by("status", "expiresAt"))

    fun getNoteById(id: Long): Note = noteRepository.findById(id).orElseThrow()

    fun getNotesByUser(username: String): List<Note> = noteRepository.findAllByAuthorUsername(username, Sort.by("status", "expiresAt"))

    fun pinNote(id: Long, status: NoteStatus): Int = noteRepository.pinNote(id, status)

    fun createNote(note: Note, username: String): Note {
        val user = userRepository.getReferenceById(username)
        note.author = user
        return noteRepository.save(note)
    }

    fun save(note: Note): Note = noteRepository.save(note)

}
