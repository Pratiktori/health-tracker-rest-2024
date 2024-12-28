import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class AddWorkoutTest {
    private var driver: WebDriver? = null
    private var vars: Map<String, Any>? = null
    private var js: JavascriptExecutor? = null

    @Before
    fun setUp() {
        driver = ChromeDriver()
        js = driver as JavascriptExecutor
        vars = HashMap()
    }

    @After
    fun tearDown() {
        driver!!.quit()
    }

    @Test
    fun addWorkout() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(788, 816)

        // Navigate to Add Workout Page
        driver!!.findElement(By.cssSelector(".col:nth-child(4) .btn")).click()
        driver!!.findElement(By.cssSelector(".fa-plus")).click()

        // Fill Workout Form
        driver!!.findElement(By.name("name")).click()
        driver!!.findElement(By.name("name")).sendKeys("Evening yoga")

        driver!!.findElement(By.name("duration")).click()
        driver!!.findElement(By.name("duration")).sendKeys("20")

        driver!!.findElement(By.name("caloriesBurned")).click()
        driver!!.findElement(By.name("caloriesBurned")).sendKeys("10")

        driver!!.findElement(By.name("performedAt")).click()
        driver!!.findElement(By.name("performedAt")).sendKeys("2024-12-28T19:54")

        // Submit Form
        driver!!.findElement(By.cssSelector(".btn-primary")).click()

        // Assert Success Message
        Assert.assertThat(driver!!.switchTo().alert().text, CoreMatchers.`is`("Workout added successfully"))
    }
}
