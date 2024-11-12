package ie.setu.domain.repository

import ie.setu.domain.User

class UserDAO {

    private val users = arrayListOf<User>(
        User(name = "Alice", email = "alice@wonderland.com", id = 0),
        User(name = "Bob", email = "bob@cat.ie", id = 1),
        User(name = "Mary", email = "mary@contrary.com", id = 2),
        User(name = "Carol", email = "carol@singer.com", id = 3)
    )

    fun getAll() : ArrayList<User>{
        return users
    }

    fun findById(id: Int): User?{
        return users.find {it.id == id}
    }

    fun save(user: User){
        users.add(user)
    }

    fun findByEmail(email: String) :User?{
        return users.find {it.email == email}
    }

    fun delete(id: Int): Int {
        val userToRemove = users.find { it.id == id }
        return if (userToRemove != null) {
            users.remove(userToRemove)
            1 // Return 1 to indicate that one user was deleted
        } else {
            0 // Return 0 to indicate that no user was found to delete
        }
    }

//    fun update(id: Int, user: User){
//        return users.update(id,user)
//    }
    fun update(id: Int, updatedUser: User): Boolean {
        val index = users.indexOfFirst { it.id == id }
        return if (index != -1) {
            // Update the user at the found index
            users[index] = updatedUser.copy(id = id) // Ensure the ID remains the same
            true // Indicate that the update was successful
        } else {
            false // Indicate that no user was found to update
        }
    }

}