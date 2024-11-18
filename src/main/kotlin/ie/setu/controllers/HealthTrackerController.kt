package ie.setu.controllers




import ie.setu.domain.User

import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context


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
        val user: User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        user.id = userId
        ctx.json(user)
        ctx.status(201)
    }

    fun getUserByEmail(ctx: Context){
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context){
        userDao.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context){
        val foundUser: User  = jsonToObject(ctx.body())
        if ((userDao.update(id = ctx.pathParam("user-id").toInt(), user = foundUser)) !=0 )
            ctx.status(204)
        else
            ctx.status(404)
    }


}
