package ie.setu.domain

import org.joda.time.DateTime

data class Workout(
    var id: Int,
    var name: String,
    var duration: Int,  // Duration in minutes
    var caloriesBurned: Int,
    var performedAt: DateTime,
    var userId: Int
)