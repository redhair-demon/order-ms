package com.example.orderms

import com.example.orderms.configuration.ErrorResponse
import com.example.orderms.model.Note
import com.example.orderms.model.User
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(
    classes = [OrderMsApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderMsApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @BeforeAll
    fun setup() {
        restTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
    }

    @Test
    fun contextLoads() {
    }

    // User tests
    @Test
    fun `GET valid, non-valid user`() {
        val result1 = restTemplate.getForEntity("/api/user?username=jdoe", User::class.java)

        assertNotNull(result1)
        assertEquals(HttpStatus.OK, result1.statusCode)

        val result2 = restTemplate.getForEntity("/api/user?username=peterparker", ErrorResponse::class.java)

        assertNotNull(result2)
        assertEquals(HttpStatus.NOT_FOUND, result2.statusCode)
    }

    @Test
    fun `POST user`() {
        val user = User(username = "wsmith", firstName = "Will", lastName = "Smith")
        val result = restTemplate.postForEntity("/api/user", user, User::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun `PATCH user change firstName`() {
        val old = restTemplate.getForEntity("/api/user?username=jdoe", User::class.java)
        assertNotNull(old)
        assertEquals("John", old.body?.firstName)

        val result = restTemplate.patchForObject("/api/user/edit?username=jdoe&firstName=Jonathan", null, User::class.java)
        assertNotNull(result)
        assertEquals("Jonathan", result.firstName)
    }

    @Test
    fun `Admin GET all users`() {
        val result1 = restTemplate.getForEntity("/api/user/admin/all", ErrorResponse::class.java)

        assertNotNull(result1)
        assertEquals(HttpStatus.UNAUTHORIZED, result1.statusCode)

        val result2 = restTemplate.withBasicAuth("admin", "password").getForEntity("/api/user/admin/all", List::class.java)

        assertNotNull(result2)
        assertEquals(HttpStatus.OK, result2.statusCode)
    }

    @Test
    fun `Admin DELETE user`() {
        val result1 = restTemplate.exchange(
            "/api/user/admin/delete?username=bwayne",
            HttpMethod.DELETE,
            null,
            ErrorResponse::class.java
        )

        assertNotNull(result1)
        assertEquals(HttpStatus.UNAUTHORIZED, result1.statusCode)

        val result2 = restTemplate
            .withBasicAuth("admin", "password")
            .exchange(
                "/api/user/admin/delete?username=bwayne",
                HttpMethod.DELETE,
                null,
                User::class.java
            )

        assertNotNull(result2)
        assertEquals(HttpStatus.OK, result2.statusCode)
    }

    // Note tests
    @Test
    fun `GET notes by user`() {
        val result = restTemplate.getForEntity("/api/note/user?username=jdoe", List::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun `GET note by id`() {
        val result1 = restTemplate.getForEntity("/api/note?username=jdoe&id=1", Note::class.java)

        assertNotNull(result1)
        assertEquals(HttpStatus.OK, result1.statusCode)

        val result2 = restTemplate.getForEntity("/api/note?username=bwayne&id=1", ErrorResponse::class.java)

        assertNotNull(result2)
        assertEquals(HttpStatus.FORBIDDEN, result2.statusCode)
    }

    @Test
    fun `PATCH note toggle pin`() {
        val result = restTemplate.patchForObject("/api/note/pin?username=jdoe&id=1&pinned=true", null, Boolean::class.java)

        assertNotNull(result)
        assert(result)
    }

    @Test
    fun `POST valid, non-valid user note`() {
        val result1 = restTemplate.postForEntity("/api/note?username=bwayne", Note("test", Date()), Note::class.java)

        assertNotNull(result1)
        assertEquals(HttpStatus.OK, result1.statusCode)

        val result2 = restTemplate.postForEntity("/api/note?username=peterparker", Note("test", Date()), ErrorResponse::class.java)

        assertNotNull(result2)
        assertEquals(HttpStatus.BAD_REQUEST, result2.statusCode)
    }

    @Test
    fun `PATCH edit note`() {
        val result1 = restTemplate.patchForObject("/api/note/edit?username=jdoe&id=1&text='edited text for note'", null, Note::class.java)

        assertNotNull(result1)
    }

    @Test
    fun `DELETE note`() {
        val result = restTemplate.exchange(
            "/api/note/delete?username=jdoe&id=2",
            HttpMethod.DELETE,
            null,
            Note::class.java
        )

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(2L, result.body?.id)
    }

    @Test
    fun `Admin GET all notes`() {
        val result1 = restTemplate.getForEntity("/api/note/admin/all", ErrorResponse::class.java)

        assertNotNull(result1)
        assertEquals(HttpStatus.UNAUTHORIZED, result1.statusCode)

        val result2 = restTemplate
            .withBasicAuth("admin", "password")
            .getForEntity("/api/note/admin/all", List::class.java)

        assertNotNull(result2)
        assertEquals(HttpStatus.OK, result2.statusCode)
    }

}
