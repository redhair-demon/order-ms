package com.example.orderms.controller

import com.example.orderms.configuration.InvalidRightsException
import com.example.orderms.model.Note
import com.example.orderms.model.NoteStatus
import com.example.orderms.service.NoteService
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("/api/note")
class NoteController(private val noteService: NoteService) {

    @GetMapping("/admin/all")
    fun getAllNotes() = noteService.getAllNotes()

    @GetMapping
    fun getNoteById(
        @RequestParam username: String,
        @RequestParam id: Long
    ): Note {
        val note = noteService.getNoteById(id)
        if (username != note.author?.username) throw InvalidRightsException("This note belongs to another user")
        return note
    }

    @GetMapping("/user")
    fun getNotesByUser(@RequestParam username: String): List<Note> = noteService.getNotesByUser(username)

    @PatchMapping("/pin")
    fun pinNote(
        @RequestParam username: String,
        @RequestParam id: Long,
        @RequestParam pinned: Boolean
    ): Boolean {
        if (getNoteById(username, id).status == NoteStatus.EXPIRED) throw IllegalArgumentException("Note is expired")
        return noteService.pinNote(id, if (pinned) NoteStatus.PINNED else NoteStatus.DEFAULT) > 0
    }

    @PostMapping
    fun createNote(
        @RequestParam username: String,
        @RequestBody note: Note
    ): Note = noteService.createNote(note, username)

    @PatchMapping("/edit")
    fun editNote(
        @RequestParam username: String,
        @RequestParam id: Long,
        @RequestParam(required = false) text: String?,
        @RequestParam(required = false) expiresAt: Date?
    ): Note {
        val note = getNoteById(username, id)
        if (!text.isNullOrBlank()) note.text = text
        if (expiresAt != null) note.expiresAt = expiresAt
        return noteService.save(note)
    }

}
