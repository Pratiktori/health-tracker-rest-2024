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
import org.openqa.selenium.interactions.Actions

class AddActivityTest {
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
    fun addActivity() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(783, 816)

        // Navigate to Add Activity Page
        driver!!.findElement(By.cssSelector(".row > .col:nth-child(2) .btn")).click()
        driver!!.findElement(By.cssSelector(".fa-plus")).click()

        // Fill Activity Form
        driver!!.findElement(By.name("description")).click()
        driver!!.findElement(By.name("description")).sendKeys("outdoor")

        driver!!.findElement(By.name("duration")).click()
        driver!!.findElement(By.name("duration")).sendKeys("60")

        driver!!.findElement(By.name("calories")).click()
        driver!!.findElement(By.name("calories")).sendKeys("500")

        driver!!.findElement(By.name("started")).click()
        driver!!.findElement(By.name("started")).sendKeys("2024-12-19T19:17")

        // Submit Form
        driver!!.findElement(By.cssSelector(".container-fluid")).click()
        driver!!.findElement(By.cssSelector(".btn-primary")).click()

        // Verify Activity Added
        run {
            val element = driver!!.findElement(By.linkText("Yoga, Calories: 150, Started: 11/22/2023, 6:30:00 PM (60 minutes)"))
            val builder = Actions(driver)
            builder.moveToElement(element).perform()
        }

        // Assert Success Message
        Assert.assertThat(driver!!.switchTo().alert().text, CoreMatchers.`is`("Activity added successfully"))
    }
}
