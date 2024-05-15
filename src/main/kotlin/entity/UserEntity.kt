package org.example.entity

import java.util.UUID

data class UserEntity(var id: UUID = UUID.randomUUID(),var name: String,var email: String)