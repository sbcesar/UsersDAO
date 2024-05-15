package org.example.dao

import org.example.entity.UserEntity
import java.util.*

interface IUserDAO {
    fun create(user: UserEntity):UserEntity?
    fun getAll(): List<UserEntity>?
    fun getById(id: UUID): UserEntity?
    fun update(user: UserEntity):UserEntity?
    fun delete(id: UUID): Boolean
}