package ie.setu.domain.repository

import ie.setu.domain.Workout
import ie.setu.domain.db.Workouts
import ie.setu.utils.mapToWorkout
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class WorkoutDAO {

    // Get all the workouts in the database regardless of user id
    fun getAll(): ArrayList<Workout> {
        val workoutList: ArrayList<Workout> = arrayListOf()
        transaction {
            Workouts.selectAll().map {
                workoutList.add(mapToWorkout(it))
            }
        }
        return workoutList
    }

    // Find all workouts for a specific user id
    fun findByUserId(userId: Int): List<Workout> {
        return transaction {
            Workouts
                .selectAll().where { Workouts.userId eq userId }
                .map { mapToWorkout(it) }
        }
    }

    // Save a workout to the database
    fun save(workout: Workout): Int {
        return transaction {
            Workouts.insert {
                it[name] = workout.name
                it[duration] = workout.duration
                it[caloriesBurned] = workout.caloriesBurned
                it[performedAt] = workout.performedAt
                it[userId] = workout.userId
            } get Workouts.id
        }
    }

    // Update a workout in the database
    fun updateByWorkoutId(workoutId: Int, workoutToUpdate: Workout): Int {
        return transaction {
            Workouts.update({ Workouts.id eq workoutId }) {
                it[name] = workoutToUpdate.name
                it[duration] = workoutToUpdate.duration
                it[caloriesBurned] = workoutToUpdate.caloriesBurned
                it[performedAt] = workoutToUpdate.performedAt
                it[userId] = workoutToUpdate.userId
            }
        }
    }

    // Delete workouts by user id
    fun deleteByUserId(userId: Int): Int {
        return transaction {
            Workouts.deleteWhere { Workouts.userId eq userId }
        }
    }

    // Delete a specific workout by its id
    fun deleteByWorkoutId(workoutId: Int): Int {
        return transaction {
            Workouts.deleteWhere { Workouts.id eq workoutId }
        }
    }

    // Find a workout by its id
    fun findByWorkoutId(workoutId: Int): Workout? {
        return transaction {
            Workouts
                .selectAll().where { Workouts.id eq workoutId }
                .map { mapToWorkout(it) }
                .firstOrNull()
        }
    }
}