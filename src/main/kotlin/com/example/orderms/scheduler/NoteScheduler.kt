package com.example.orderms.scheduler

import com.example.orderms.repository.NoteRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class NoteScheduler(private val noteRepository: NoteRepository) {

    @Scheduled(fixedRate = 15000)
    fun updateExpired() {
        val updated = noteRepository.updateExpired()
        if (updated > 0) logger.info { "Updated $updated expired notes" }
    }

}
