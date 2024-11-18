package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Workouts : Table("workouts") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val duration = integer("duration")  // Duration in minutes
    val caloriesBurned = integer("calories_burned")
    val performedAt = datetime("performed_at")
    val userId = integer("user_id")

    override val primaryKey = PrimaryKey(id, name = "PK_Workouts_ID")
}