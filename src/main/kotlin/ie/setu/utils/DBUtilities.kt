package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)

fun mapToSleep(it: ResultRow) = Sleep(
    id = it[Sleeping.id],
    name = it[Sleeping.name],
    duration = it[Sleeping.duration],
    started = it[Sleeping.started]
)

fun mapToNutrition(it: ResultRow) = Nutrition(
    id = it[Diet.id],
    foodName = it[Diet.foodName],
    calories = it[Diet.calories],
    consumedAt = it[Diet.consumedAt],
    userId = it[Diet.userId]
)

fun mapToWorkout(it: ResultRow) = Workout(
    id = it[Workouts.id],
    name = it[Workouts.name],
    duration = it[Workouts.duration],
    caloriesBurned = it[Workouts.caloriesBurned],
    performedAt = it[Workouts.performedAt],
    userId = it[Workouts.userId]
)