package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Nutrition

import ie.setu.domain.db.Diet

import ie.setu.domain.repository.NutritionDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context


object NutritionController {

    private val userDao = UserDAO()
    private val nutritionDAO = NutritionDAO()

    fun getAllNutrition(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(nutritionDAO.getAll()))
    }

    fun getNutritionByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = nutritionDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }

    fun addNutrition(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val nutrition = mapper.readValue<Nutrition>(ctx.body())
        nutritionDAO.save(nutrition)
        ctx.json(nutrition)
    }

    fun deleteNutritionByUserId(ctx: Context) {
        nutritionDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }


    fun updateNutrition(ctx: Context) {
        val nutrition: Diet = jsonToObject(ctx.body())
        if (nutritionDAO.updateByNutritionId(
                nutritionId = ctx.pathParam("activity-id").toInt(),
                nutritionToUpdate = nutrition
            ) != 0
        )
            ctx.status(204)
        else
            ctx.status(404)
    }
}