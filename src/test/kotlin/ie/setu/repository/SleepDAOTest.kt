package ie.setu.repository

import ie.setu.domain.Sleep
import ie.setu.domain.db.Sleeping

import ie.setu.domain.repository.SleepDAO
import ie.setu.helpers.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class SleepDAOTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    //retrieving some test data from Fixtures
    private val sleep1 = sleeping.get(0)
    private val sleep2 = sleeping.get(1)
    private val sleep3 = sleeping.get(2)


    @Nested
    inner class CreateSleeping {

        @Test
        fun `multiple Sleeping added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                assertEquals(sleep1, sleepDAO.findBySleepId(sleep1.id), )
                assertEquals(sleep2, sleepDAO.findBySleepId(sleep2.id))
                assertEquals(sleep3, sleepDAO.findBySleepId(sleep3.id))
            }
        }
    }

    @Nested
    inner class Sleeping {

        @Test
        fun `getting all Sleeping from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `get sleep by user id that has no Sleeping, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(0, sleepDAO.findByUserId(3).size)
            }
        }

        @Test
        fun `get sleep by user id that exists , results in a correct  sleeping (s ) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(sleep1, sleepDAO.findByUserId(1).get(0))
                assertEquals(sleep2, sleepDAO.findByUserId(1).get(1))
                assertEquals(sleep3, sleepDAO.findByUserId(2).get(0))
            }
        }

        @Test
        fun `get all Sleeping over empty table returns none`() {
            transaction {

                //Arrange - create and setup sleepDAO object
                SchemaUtils.create(Sleeping)
                val sleepDAO = SleepDAO()

                //Act & Assert
                assertEquals(0, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `get sleep by sleep id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(null, sleepDAO.findBySleepId(4))
            }
        }

        @Test
        fun `get sleep by sleep id that exists, results in a correct sleep returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()
                //Act & Assert
                assertEquals(sleep1, sleepDAO.findBySleepId(1))
                assertEquals(sleep3, sleepDAO.findBySleepId(3))
            }
        }
    }

    @Nested
    inner class UpdateSleeping {

        @Test
        fun `updating existing sleep in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                val sleep3updated = Sleep(id = 3, name = "Night Sleep", duration = 7.50,
                     started = DateTime.now(), userId = 2)
                sleepDAO.updateBySleepId(user3.id, sleep3updated)
                assertEquals(sleep3updated, sleepDAO.findBySleepId(3))
            }
        }

        @Test
        fun `updating non-existant sleep in table results in no updates`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                val sleep4updated = Sleep(id = 4, name = "Night Sleep", duration = 7.50,
                    started = DateTime.now(), userId = 2)
                sleepDAO.updateBySleepId(4, sleep4updated)
                assertEquals(null, sleepDAO.findBySleepId(4))
                assertEquals(3, sleepDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteSleeping {

        @Test
        fun `deleting a non-existant sleep (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.deleteBySleepId(4)
                assertEquals(3, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing sleep (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.deleteBySleepId(sleep3.id)
                assertEquals(2, sleepDAO.getAll().size)
            }
        }


        @Test
        fun `deleting Sleeping when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.deleteBySleepId(3)
                assertEquals(2, sleepDAO.getAll().size)
            }
        }

        @Test
        fun `deleting Sleeping when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three Sleeping
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                //Act & Assert
                assertEquals(3, sleepDAO.getAll().size)
                sleepDAO.deleteBySleepId(1)
                assertEquals(2, sleepDAO.getAll().size)
            }
        }
    }
}