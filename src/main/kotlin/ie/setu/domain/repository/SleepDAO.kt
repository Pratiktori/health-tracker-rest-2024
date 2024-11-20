package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.Sleep
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Sleeping
import ie.setu.utils.mapToActivity
import ie.setu.utils.mapToSleep
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class SleepDAO {

    //Get all the sleepcycle in the database regardless of user id
    fun getAll(): ArrayList<Sleep> {
        val sleepList: ArrayList<Sleep> = arrayListOf()
        transaction {
            Sleeping.selectAll().map {
                sleepList.add(mapToSleep(it)) }
        }
        return sleepList
    }

    //Find a specific sleep by sleep id
    fun findBySleepId(id: Int): Sleep?{
        return transaction {
            Sleeping
                .selectAll().where { Sleeping.id eq id}
                .map{ mapToSleep(it) }
                .firstOrNull()
        }
    }

    //Find all sleepcycle for a specific user id
    fun findByUserId(userId: Int): List<Sleep>{
        return transaction {
            Sleeping
                .selectAll().where { Sleeping.userId eq userId}
                .map { mapToSleep(it) }
        }
    }

    //Save an sleepcycle to the database
    fun save(sleep: Sleep){
        transaction {
            Sleeping.insert {
                it[id] = sleep.id
                it[duration] = sleep.duration
                it[name] = sleep.name
                it[started] = sleep.started
                it[userId] = sleep.userId
            }
        }
    }
    fun updateBySleepId(sleepId: Int, sleepToUpdate: Sleep) : Int{
        return transaction {
            Sleeping.update ({
                Sleeping.id eq sleepId}) {
                it[id] = sleepToUpdate.id
                it[duration] = sleepToUpdate.duration
                it[name] = sleepToUpdate.name
                it[started] = sleepToUpdate.started
                it[userId] = sleepToUpdate.userId
            }
        }
    }

    fun deleteBySleepId (userId: Int): Int{
        return transaction{
            Sleeping.deleteWhere { Sleeping.id eq userId }
        }
    }

}