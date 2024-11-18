package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Workout
import ie.setu.domain.db.Workouts
import ie.setu.domain.repository.WorkoutDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object WorkoutController {

    private val userDao = UserDAO()
    private val workoutDAO = WorkoutDAO()

    fun getAllWorkouts(ctx: Context) {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(workoutDAO.getAll()))
    }

    fun getWorkoutsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val workouts = workoutDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (workouts.isNotEmpty()) {
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(workouts))
            }
        }
    }

    fun addWorkout(ctx: Context) {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val workout = mapper.readValue<Workout>(ctx.body())
        workoutDAO.save(workout)
        ctx.json(workout)
    }

    fun deleteWorkoutByUserId(ctx: Context) {
        workoutDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }

    fun updateWorkout(ctx: Context) {
        val workout: Workout = jsonToObject(ctx.body())
        if (workoutDAO.updateByWorkoutId(
                workoutId = ctx.pathParam("workout-id").toInt(),
                workoutToUpdate = workout
            ) != 0
        )
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun getWorkoutById(ctx: Context) {
        val workout = workoutDAO.findByWorkoutId(ctx.pathParam("workout-id").toInt())
        if (workout != null) {
            val mapper = jacksonObjectMapper()
                .registerModule(JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            ctx.json(mapper.writeValueAsString(workout))
        } else {
            ctx.status(404)
        }
    }

    fun deleteWorkoutById(ctx: Context) {
        if (workoutDAO.deleteByWorkoutId(ctx.pathParam("workout-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}