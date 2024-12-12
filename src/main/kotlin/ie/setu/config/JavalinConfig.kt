package ie.setu.config

import ie.setu.controllers.*
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent

class JavalinConfig {

fun startJavalinService(): Javalin {
    val app = Javalin.create{
        //added this jsonMapper for our integration tests - serialise objects to json
        it.jsonMapper(JavalinJackson(jsonObjectMapper()))
        it.staticFiles.enableWebjars()
        it.vue.vueInstanceNameInJs = "app" // only required for Vue 3, is defined in layout.html
    }.apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("404 : Not Found") }
    }.start(getRemoteAssignedPort())

    registerRoutes(app)
    return app
}

    private fun registerRoutes(app: Javalin) {
        app.get("/api/users", HealthTrackerController::getAllUsers)
        app.get("/api/users/{user-id}", HealthTrackerController::getUserByUserId)
        app.post("/api/users", HealthTrackerController::addUser)
        app.get("/api/users/email/{email}", HealthTrackerController::getUserByEmail)
        app.delete("/api/users/{user-id}", HealthTrackerController::deleteUser)
        app.patch("/api/users/{user-id}", HealthTrackerController::updateUser)
        // New activity endpoints
        app.get("/api/activities", ActivityController::getAllActivities)
        app.post("/api/activities", ActivityController::addActivity)
        app.get("/api/users/{user-id}/activities", ActivityController::getActivitiesByUserId)
        app.delete("/api/users/{user-id}/activities", ActivityController::deleteActivityByUserId)
        app.delete("/api/activities/{activity-id}", ActivityController::deleteActivityByActivityId)
        app.patch("/api/activities/{activity-id}", ActivityController::updateActivity)
        app.get("/api/activities/{activity-id}", ActivityController::getActivitiesByUserId)
        //New sleep endpoints
        app.get("/api/sleep", SleepController::getAllSleepcycle)
        app.get("/api/sleep/{user-id}", SleepController::getSleepByUserId)
        app.post("/api/sleep", SleepController::addSleep)
        app.delete("/api/users/{user-id}/sleep", SleepController::deleteSleepByUserId)
        app.patch("/api/sleep/{user-id}", SleepController::updateSleep)
        //New Nutrition endpoints
        app.get("/api/nutrition", NutritionController::getAllNutrition)
        app.get("/api/nutrition/{user-id}", NutritionController::getNutritionByUserId)
        app.post("/api/nutrition", NutritionController::addNutrition)
        app.delete("/api/users/{user-id}/nutrition", NutritionController::deleteNutritionByUserId)
        app.patch("/api/nutrition/{user-id}", NutritionController::updateNutrition)
        //New Workout endpoints
        app.get("/api/workout", WorkoutController::getAllWorkouts)
        app.get("/api/workout/{user-id}", WorkoutController::getWorkoutsByUserId)
        app.post("/api/workout", WorkoutController::addWorkout)
        app.delete("/api/users/{user-id}/workout", WorkoutController::deleteWorkoutByUserId)
        app.patch("/api/workout/{user-id}", WorkoutController::updateWorkout)
        // The @routeComponent that we added in layout.html earlier will be replaced
        // by the String inside the VueComponent. This means a call to / will load
        // the layout and display our <home-page> component.
        app.get("/", VueComponent("<home-page></home-page>"))
        app.get("/users", VueComponent("<user-overview></user-overview>"))
        app.get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
        app.get("/users/{user-id}/activities", VueComponent("<user-activity-overview></user-activity-overview>"))
        app.get("/sleep/{user-id}", VueComponent("<user-sleep-overview></user-sleep-overview>"))
        app.get("/nutrition/{user-id}", VueComponent("<user-nutrition-overview></user-nutrition-overview>"))
        app.get("/workout/{user-id}", VueComponent("<user-workout-overview></user-workout-overview>"))
    }
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
