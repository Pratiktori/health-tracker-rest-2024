package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.User

object HealthTrackerController {

    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email").toString())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context) {
        val user = userDao.delete(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun updateUser(ctx: Context) {
        try {
            val userId = ctx.pathParam("user-id").toInt()
            val mapper = jacksonObjectMapper()
            val updatedUser = mapper.readValue<User>(ctx.body())

            // Call the update method on the DAO
            if (userDao.update(userId, updatedUser)) {
                ctx.status(200)
            } else {
                ctx.status(204)
                ctx.json(mapOf("message" to "User not found"))
            }
        } catch (e: NumberFormatException) {
            ctx.status(400)
            ctx.json(mapOf("message" to "Invalid user ID format"))
        } catch (e: Exception) {
            ctx.status(500)
            ctx.json(mapOf("message" to "An error occurred during update"))
        }
    }
}