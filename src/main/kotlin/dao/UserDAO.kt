package org.example.dao

import org.example.entity.UserEntity
import org.example.output.IOutputInfo
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

class UserDAO(
    private val dataSource: DataSource,
    private val console: IOutputInfo
): IUserDAO {
    override fun create(user: UserEntity): UserEntity? {
        val sql = "INSERT INTO tuser (id, name, email) VALUES (?, ?, ?)"
        return try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, user.id.toString())
                    stmt.setString(2, user.name)
                    stmt.setString(3, user.email)
                    val rs = stmt.executeUpdate()   //Num de registros
                    if (rs == 1) {
                        user
                    } else {
                        console.showMessage("*Error* Insert query failed! ($rs records inserted)")
                        null
                    }
                    user
                }
            }
        } catch (e: SQLException) {
            console.showMessage("*Error* Insert query failed! ($e)")
            null
        }

    }

    override fun getById(id: UUID): UserEntity? {
        val sql = "SELECT * FROM tuser WHERE id = ?"
        return try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, id.toString())
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        UserEntity(
                            id = UUID.fromString(rs.getString("id")),
                            name = rs.getString("name"),
                            email = rs.getString("email")
                        )
                    } else {
                        null
                    }
                }
            }
        } catch (e:SQLException) {
            console.showMessage("*Error* Insert query failed! (${e.message})")
            null
        }
    }

    override fun getAll(): List<UserEntity>? {
        val sql = "SELECT * FROM tuser"
        return try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    val rs = stmt.executeQuery()
                    val users = mutableListOf<UserEntity>()
                    while (rs.next()) {
                        users.add(
                            UserEntity(
                                id = UUID.fromString(rs.getString("id")),
                                name = rs.getString("name"),
                                email = rs.getString("email")
                            )
                        )
                    }
                    users
                }
            }
        } catch (e: SQLException) {
            console.showMessage("*Error* Insert query failed! (${e.message})")
            null
        }
    }

    override fun update(user: UserEntity):UserEntity? {
        val sql = "UPDATE tuser SET name = ?, email = ? WHERE id = ?"
        return try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, user.name)
                    stmt.setString(2, user.email)
                    stmt.setString(3, user.id.toString())
                    stmt.executeUpdate()
                    user
                }
            }
        } catch (e: SQLException) {
            console.showMessage("*Error* Insert query failed! (${e.message})")
            null
        }
    }

    override fun delete(id: UUID): Boolean {
        val sql = "DELETE FROM tuser WHERE id = ?"
        return try {
            dataSource.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, id.toString())
                    stmt.executeUpdate()
                    return (stmt.executeUpdate() == 1)
                }
            }
        } catch (e: SQLException) {
            console.showMessage("*Error* Insert query failed! (${e.message})")
            false
        }
    }
}