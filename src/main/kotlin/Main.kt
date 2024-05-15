package org.example

import org.example.dao.UserDAO
import org.example.dbConnection.DataSourceFactory
import org.example.entity.UserEntity
import org.example.output.Console
import org.example.service.UserServiceImpl

fun main() {

    val consola = Console()

    // Creamos la instancia de la base de datos
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    // Creamos la instancia de UserDAO
    val userDao = UserDAO(dataSource,consola)

    // Creamos la instancia de UserService
    val userService = UserServiceImpl(userDao)

    // Creamos un nuevo usuario
    val newUser = UserEntity(name = "John Doe", email = "johndoe@example.com")
    var createdUser = userService.create(newUser)
    consola.showMessage("Created user: ${createdUser ?: "error"}")

    // Obtenemos un usuario por su ID
    val foundUser =
        if (createdUser != null) userService.getById(createdUser.id)
        else null
    consola.showMessage("Found user: ${foundUser ?: "error"}")

    // Actualizamos el usuario
    val updatedUser = foundUser!!.copy(name = "Jane Doe")
    val savedUser =
        if (updatedUser != null) userService.update(updatedUser)
        else null
    consola.showMessage("Updated user: ${savedUser ?: "error"}")

    val otherUser = UserEntity(name = "Eduardo Fernandez", email = "eferoli@gmail.com")
    createdUser = userService.create( otherUser)
    consola.showMessage("Created user: $createdUser")


    // Obtenemos todos los usuarios
    var allUsers = userService.getAll()
    if (allUsers != null) {
        consola.show(allUsers)
    }

    // Eliminamos el usuario
    if (savedUser != null) {
         if (userService.delete(otherUser.id)) {
             consola.showMessage("User deleted")
         } else {
             consola.showMessage("User deleted")
         }

    }



    // Obtenemos todos los usuarios
    allUsers = userService.getAll()
    consola.showMessage("All users: $allUsers")

    // Eliminamos el usuario
    userService.delete(otherUser.id)
    consola.showMessage("User deleted")
}