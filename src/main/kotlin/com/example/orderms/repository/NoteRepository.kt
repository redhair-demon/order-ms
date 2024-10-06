package com.example.orderms.repository

import com.example.orderms.model.Note
import com.example.orderms.model.NoteStatus
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface NoteRepository : JpaRepository<Note, Long> {

    fun findAllByAuthorUsername(authorUsername: String, sort: Sort): List<Note>

    @Transactional
    @Modifying
    @Query(value = "update Note n set n.status = :status, n.updatedAt = CURRENT_TIMESTAMP where n.expiresAt <= CURRENT_TIMESTAMP and n.status != :status")
    fun updateExpired(status: NoteStatus = NoteStatus.EXPIRED): Int

    @Transactional
    @Modifying
    @Query(value = "update Note n set n.status = :status where n.id = :id")
    fun pinNote(id: Long, status: NoteStatus): Int

}
