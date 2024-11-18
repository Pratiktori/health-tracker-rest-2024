package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Diet : Table("nutrition") {
    val id = integer("id").autoIncrement()
    val foodName = varchar("food_name", 100)
    val calories = integer("calories")
    val consumedAt = datetime("consumed_at")
    val userId = integer("user_id")

    override val primaryKey = PrimaryKey(id, name = "PK_Nutrition_ID")
}