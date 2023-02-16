package org.veiset.kotlinspringflywayrestexample

import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.sql.DataSource

@Repository
class UserRepository(val dataSource: DataSource) {

    fun getUsers(): List<User> =
        dataSource.connection.use { connection ->
            val sql = "SELECT * FROM Users"
            val resultSet = connection.createStatement().executeQuery(sql)
            val users = mutableListOf<User>()
            while (resultSet.next()) {
                users.add(resultSet.userFromResultSet())
            }
            users
        }

    fun getUser(id: Int): User? =
        dataSource.connection.use { connection ->
            val resultSet = connection
                .prepareStatement("SELECT * FROM Users where id = ?")
                .also { it.setInt(1, id) }
                .executeQuery()

            if (resultSet.next()) resultSet.userFromResultSet() else null
        }

    fun addUser(user: User) {
        dataSource.connection.use { connection ->
            connection
                .prepareStatement("INSERT INTO Users (id, title, username) VALUES ( ?, ?, ? )")
                .also {
                    it.setInt(1, user.id)
                    it.setString(2, user.title)
                    it.setString(3, user.username)
                }
                .executeUpdate()
        }
    }

    fun removeUser(id: Int) {
        dataSource.connection.use { connection ->
            connection
                .prepareStatement("DELETE FROM Users where id = ?")
                .also { it.setInt(1, id) }
                .executeUpdate()
        }
    }

    fun updateUser(user: User) {
        dataSource.connection.use { connection ->
            connection
                .prepareStatement("UPDATE Users SET title = ?, username = ? WHERE id = ?")
                .also {
                    it.setString(1, user.title)
                    it.setString(2, user.username)
                    it.setInt(3, user.id)
                }
                .executeUpdate()
        }
    }

    private fun ResultSet.userFromResultSet(): User = User(
        id = this.getInt("id"),
        username = this.getString("username"),
        title = this.getString("title"),
    )
}