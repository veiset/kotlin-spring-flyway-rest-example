package org.veiset.kotlinspringflywayrestexample

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(val userRepository: UserRepository) {

    @GetMapping
    fun getUsers(): List<User> =
        userRepository.getUsers()

    @PostMapping
    fun addUser(@RequestBody user: User) =
        userRepository.addUser(user)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): User? =
        userRepository.getUser(id)

    @PutMapping("/{id}")
    fun updateUser(@RequestBody user: User) =
        userRepository.updateUser(user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int) =
        userRepository.removeUser(id)
}