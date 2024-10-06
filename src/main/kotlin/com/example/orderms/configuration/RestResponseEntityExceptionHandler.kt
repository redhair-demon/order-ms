package com.example.orderms.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.ServletException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class RestResponseEntityExceptionHandler {
    @ExceptionHandler(value = [RuntimeException::class, ServletException::class])
    protected fun handleConflict(e: Exception): ResponseEntity<ErrorResponse> {
        return error(HttpStatus.BAD_REQUEST, e)
    }

    @ExceptionHandler(value = [NoSuchElementException::class])
    protected fun handle404(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return error(HttpStatus.NOT_FOUND, e)
    }

    @ExceptionHandler(value = [InvalidUserException::class])
    protected fun handle401(e: InvalidUserException): ResponseEntity<ErrorResponse> {
        return error(HttpStatus.UNAUTHORIZED, e)
    }

    @ExceptionHandler(value = [InvalidRightsException::class])
    protected fun handle403(e: InvalidRightsException): ResponseEntity<ErrorResponse> {
        return error(HttpStatus.FORBIDDEN, e)
    }

    private fun error(status: HttpStatus, e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e) { "Exception : " }
        return ResponseEntity.status(status).body(ErrorResponse(e.message))
    }

}

data class ErrorResponse (val reason: String? = null)

class InvalidUserException(message: String?) : RuntimeException(message)

class InvalidRightsException(message: String?) : RuntimeException(message)

