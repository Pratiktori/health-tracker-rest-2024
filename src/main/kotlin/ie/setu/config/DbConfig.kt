package ie.setu.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private val logger = KotlinLogging.logger {}
    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-csqb1qqj1k6c738fpj4g-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "health_tracker_app_2x36_user"
        val PGPASSWORD = "jY7aeUPXp7wK5UDOWASMoHK8jVQZPkZH"
        val PGDATABASE = "health_tracker_app_2x36"

        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        try {
            logger.info { "Starting DB Connection...$dbUrl" }
            dbConfig = Database.connect(
                url = dbUrl, driver = "org.postgresql.Driver",
                user = PGUSER, password = PGPASSWORD
            )
            logger.info { "DB Connected Successfully..." + dbConfig.url }
        } catch (e: PSQLException) {
            logger.info { "Error in DB Connection...${e.printStackTrace()}" }
        }
        return dbConfig

    }
}