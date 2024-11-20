package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Sleep
import ie.setu.domain.db.Sleeping
import ie.setu.domain.repository.SleepDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context


object SleepController {

    private val userDao = UserDAO()
    private val sleepDAO = SleepDAO()

    fun getAllSleepcycle(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(sleepDAO.getAll()))
    }

    fun getSleepByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val sleeping = sleepDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (sleeping.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(sleeping))
            }
        }
    }

    fun addSleep(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val sleep = mapper.readValue<Sleep>(ctx.body())
        sleepDAO.save(sleep)
        ctx.json(sleep)
    }

    fun deleteSleepByUserId(ctx: Context) {
        sleepDAO.deleteBySleepId(ctx.pathParam("user-id").toInt())
    }


    fun updateSleep(ctx: Context) {
        val sleep: Sleep = jsonToObject(ctx.body())
        if (sleepDAO.updateBySleepId(
                sleepId = ctx.pathParam("sleep-id").toInt(),
                sleepToUpdate = sleep
            ) != 0
        )
            ctx.status(204)
        else
            ctx.status(404)
    }
}