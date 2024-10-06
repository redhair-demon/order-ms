package com.example.orderms.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "Users")
class User(
    @Id
    var username: String,

    var firstName: String,

    var lastName: String,

    @UpdateTimestamp
    var updatedAt: Date? = null,

    @CreationTimestamp
    var createdAt: Date? = null
) {
    override fun toString(): String {
        return "User(username='$username', firstName='$firstName', lastName='$lastName', updatedAt=$updatedAt, createdAt=$createdAt)"
    }
}
