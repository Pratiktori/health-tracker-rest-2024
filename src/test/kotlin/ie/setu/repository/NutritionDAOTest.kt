package ie.setu.repository


import ie.setu.domain.Nutrition
import ie.setu.domain.db.Diet
import ie.setu.domain.repository.NutritionDAO
import ie.setu.helpers.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NutritionDAOTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    //retrieving some test data from Fixtures
    private val nutrition1 = diet.get(0)
    private val nutrition2 = diet.get(1)
    private val nutrition3 = diet.get(2)

    @Nested
    inner class CreateDiet {

        @Test
        fun `multiple diet entries added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three diet entries
                val userDAO = populateUserTable()
                val dietDAO = populateNutritionTable()

                //Act & Assert
                assertEquals(nutrition1, dietDAO.findByNutritionId(nutrition1.id))
                assertEquals(nutrition2, dietDAO.findByNutritionId(nutrition2.id))
                assertEquals(nutrition3, dietDAO.findByNutritionId(nutrition3.id))
            }
        }
    }

    @Nested
    inner class ReadDiet {

        @Test
        fun `getting all diet from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()
                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
            }
        }

        @Test
        fun `get diet by user id that has no diet, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()
                //Act & Assert
                assertEquals(0, nutritionDAO.findByUserId(3).size)
            }
        }



//        val diet1 = nutritionDAO.findByUserId(1).get(0)
//        val diet2 = nutritionDAO.findByUserId(1).get(1)
        @Test
        fun `get diet by user id that exists , results in a correct nutrition(s ) returned`() {

            transaction {
                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()
                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
                assertEquals(nutrition1, nutritionDAO.findByUserId(1).get(0))
                assertEquals(nutrition2, nutritionDAO.findByUserId(1).get(1))
                assertEquals(nutrition3, nutritionDAO.findByUserId(2).get(0))
            }
        }

        @Test
        fun `get all diet over empty table returns none`() {
            transaction {

                //Arrange - create and setup nutritionDAO object
                SchemaUtils.create(Diet)
                val nutritionDAO = NutritionDAO()

                //Act & Assert
                assertEquals(0, nutritionDAO.getAll().size)
            }
        }

        @Test
        fun `get diet by diet id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()
                //Act & Assert
                assertEquals(null, nutritionDAO.findByNutritionId(4))
            }
        }

        @Test
        fun `get diet by diet id that exists, results in a correct diet returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()
                //Act & Assert
                assertEquals(nutrition1, nutritionDAO.findByNutritionId(1))
                assertEquals(nutrition3, nutritionDAO.findByNutritionId(3))
            }
        }
    }

    @Nested
    inner class UpdateDiet {

        @Test
        fun `updating existing diet in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                val nutrition3updated = Nutrition(
                    id = 3,
                    foodName = "Updated Salad",
                    calories = 300,
                    consumedAt = DateTime.now(),
                    userId = 2
                )
                nutritionDAO.updateByNutritionId(nutrition3updated.id, nutrition3updated)
                assertEquals(nutrition3updated, nutritionDAO.findByNutritionId(3))
            }
        }

        @Test
        fun `updating non-existant diet in table results in no updates`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                val nutrition4updated = Nutrition(id = 4, foodName = "Updated Salad",
                    calories = 220, consumedAt = DateTime.now(), userId = 2)
                nutritionDAO.updateByNutritionId(4, nutrition4updated)
                assertEquals(null, nutritionDAO.findByNutritionId(4))
                assertEquals(3, nutritionDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteDiet {

        @Test
        fun `deleting a non-existant diet (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
                nutritionDAO.deleteByNutritionId(4)
                assertEquals(3, nutritionDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing diet (by id ) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
                nutritionDAO.deleteByNutritionId(nutrition3.id)
                assertEquals(2, nutritionDAO.getAll().size)
            }
        }


        @Test
        fun `deleting diet when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
                nutritionDAO.deleteByUserId(3)
                assertEquals(2, nutritionDAO.getAll().size)
            }
        }

        @Test
        fun `deleting diet when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diet
                val userDAO = populateUserTable()
                val nutritionDAO = populateNutritionTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAll().size)
                nutritionDAO.deleteByUserId(2)
                assertEquals(2, nutritionDAO.getAll().size)
            }
        }
    }
}