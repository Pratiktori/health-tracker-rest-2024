package ie.setu.domain.repository

import ie.setu.domain.Nutrition

import ie.setu.domain.db.Diet
import ie.setu.domain.db.Sleeping
import ie.setu.utils.mapToNutrition

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NutritionDAO {

    //Get all the nutrition in the database regardless of user id
    fun getAll(): ArrayList<Nutrition> {
        val nutritionList: ArrayList<Nutrition> = arrayListOf()
        transaction {
            Diet.selectAll().map {
                nutritionList.add(mapToNutrition(it)) }
        }
        return nutritionList
    }


    //Find all nutrition for a specific user id
    fun findByUserId(userId: Int): List<Nutrition>{
        return transaction {
            Diet
                .selectAll().where { Sleeping.id eq userId}
                .map { mapToNutrition(it) }
        }
    }

    //Save an nutrition to the database
    fun save(nutrition: Nutrition){
        transaction {
            Diet.insert {
                it[id] = nutrition.id
                it[foodName] = nutrition.foodName
                it[calories] = nutrition.calories
                it[consumedAt] = nutrition.consumedAt

            }
        }
    }
    fun updateByNutritionId(nutritionId: Int, nutritionToUpdate: Diet) : Int{
        return transaction {
            Diet.update ({
                Diet.id eq nutritionId}) {
                it[id] = nutritionToUpdate.id
                it[foodName] = nutritionToUpdate.foodName
                it[calories] = nutritionToUpdate.calories
                it[consumedAt] = nutritionToUpdate.consumedAt
            }
        }
    }

    fun deleteByUserId (userId: Int): Int{
        return transaction{
            Sleeping.deleteWhere { Sleeping.id eq userId }
        }
    }

}