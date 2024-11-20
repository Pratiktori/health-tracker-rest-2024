package ie.setu.helpers

import ie.setu.domain.*
import ie.setu.domain.db.*
import ie.setu.domain.repository.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)

val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 1),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 1),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 2)
)

val diet = arrayListOf<Nutrition>(
    Nutrition(id = 1, foodName = "Apple", calories = 95, consumedAt = DateTime.now(), userId = 1),
    Nutrition(id = 2, foodName = "Chicken Salad", calories = 350, consumedAt = DateTime.now(), userId = 1),
    Nutrition(id = 3, foodName = "Spaghetti Bolognese", calories = 600, consumedAt = DateTime.now(), userId = 2)
)

val sleeping = arrayListOf<Sleep>(
    Sleep(id = 1, name = "Night Sleep", duration = 7.50, started = DateTime.now(), userId = 1 ),
    Sleep(id = 2, name = "Power Nap", duration = 6.50, started = DateTime.now(), userId = 1 ),
    Sleep(id = 3, name = "Afternoon siesta", duration = 8.50, started = DateTime.now(), userId = 2 ),

)

val workouts = arrayListOf<Workout>(
    Workout(id = 1, name = "Morning Run", duration = 30, caloriesBurned = 300, performedAt = DateTime.now(), userId = 1 ),
    Workout(id = 2, name = "Evening Yoga", duration = 45, caloriesBurned = 150, performedAt = DateTime.now(), userId = 1 ),
    Workout(id = 3, name = "Weightlifting Session", duration = 60, caloriesBurned = 400, performedAt = DateTime.now(), userId = 2 ),
)
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"

fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users[0])
    userDAO.save(users[1])
    userDAO.save(users[2])
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities[0])
    activityDAO.save(activities[1])
    activityDAO.save(activities[2])
    return activityDAO
}
fun populateNutritionTable(): NutritionDAO {
    SchemaUtils.create(Diet)
    val nutritionDAO = NutritionDAO()
    nutritionDAO.save(diet[0])
    nutritionDAO.save(diet[1])
    nutritionDAO.save(diet[2])
    return nutritionDAO
}
fun populateSleepTable(): SleepDAO {
    SchemaUtils.create(Sleeping)
    val sleepDAO = SleepDAO()
    sleepDAO.save(sleeping[0])
    sleepDAO.save(sleeping[1])
    sleepDAO.save(sleeping[2])
    return sleepDAO
}
fun populateWorkoutTable(): WorkoutDAO {
    SchemaUtils.create(Workouts)
    val workoutDAO = WorkoutDAO()
    workoutDAO.save(workouts[0])
    workoutDAO.save(workouts[1])
    workoutDAO.save(workouts[2])
    return workoutDAO
}