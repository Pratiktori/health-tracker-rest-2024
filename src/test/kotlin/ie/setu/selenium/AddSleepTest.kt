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

class AddSleepTest {
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
    fun addSleep() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(786, 816)

        // Navigate to Add Sleep Page
        driver!!.findElement(By.cssSelector(".container-fluid > .col:nth-child(2) .btn")).click()
        driver!!.findElement(By.cssSelector(".fa-plus")).click()

        // Fill Sleep Form
        driver!!.findElement(By.name("name")).click()
        driver!!.findElement(By.name("name")).sendKeys("Deep Sleep")

        driver!!.findElement(By.name("duration")).click()
        driver!!.findElement(By.name("duration")).sendKeys("12")

        driver!!.findElement(By.name("started")).click()
        driver!!.findElement(By.name("started")).sendKeys("2024-12-27T19:23")

        // Submit Form
        driver!!.findElement(By.id("addSleep")).click()
        driver!!.findElement(By.cssSelector(".btn-primary")).click()

        // Verify Sleep Record Added
        run {
            val element = driver!!.findElement(By.linkText("Morning Sleep, Duration: 7.5 hours, Started: 11/18/2023, 7:00:00 AM"))
            val builder = Actions(driver)
            builder.moveToElement(element).perform()
        }

        // Assert Success Message
        Assert.assertThat(driver!!.switchTo().alert().text, CoreMatchers.`is`("Sleep record added successfully"))
    }
}
