package com.example.orderms.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "Notes")
class Note(

    var text: String,

    var expiresAt: Date,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var author: User? = null,

    @Enumerated(value = EnumType.ORDINAL)
    var status: NoteStatus = NoteStatus.DEFAULT,

    @UpdateTimestamp
    var updatedAt: Date? = null,

    @CreationTimestamp
    var createdAt: Date? = null,

    @Id
    @GeneratedValue
    var id: Long? = null
) {
    @JsonProperty
    fun getAuthorUsername() = author?.username

    override fun toString(): String {
        return "Note(text='$text', expiresAt=$expiresAt, author=$author, status=$status, updatedAt=$updatedAt, createdAt=$createdAt, id=$id)"
    }
}
