package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Sleeping : Table("sleeping") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val duration = double("duration")
    val started = datetime("started")
    val userId = integer("user_id")
    override val primaryKey = PrimaryKey(id, name = "PK_Sleeping_ID")
}

