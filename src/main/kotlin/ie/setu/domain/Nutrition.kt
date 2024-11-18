package ie.setu.domain

import org.joda.time.DateTime


data class Nutrition(
    var id: Int,
    var foodName: String,
    var calories: Int,
    var consumedAt: DateTime,
    var userId: Int
)