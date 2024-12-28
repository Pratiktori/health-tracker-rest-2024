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

class AddNutritionTest {
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
    fun addNutrition() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(788, 816)

        // Navigate to Add Nutrition Page
        driver!!.findElement(By.cssSelector(".col:nth-child(3) .btn")).click()
        driver!!.findElement(By.cssSelector(".fa")).click()

        // Interact with the page elements
        run {
            val element = driver!!.findElement(By.cssSelector(".fa"))
            val builder = Actions(driver)
            builder.moveToElement(element).perform()
        }

        run {
            val element = driver!!.findElement(By.tagName("body"))
            val builder = Actions(driver)
            builder.moveToElement(element, 0, 0).perform()
        }

        // Fill Nutrition Form
        driver!!.findElement(By.name("foodName")).click()
        driver!!.findElement(By.name("foodName")).sendKeys("Pizza")

        driver!!.findElement(By.name("calories")).click()
        driver!!.findElement(By.name("calories")).sendKeys("500")

        driver!!.findElement(By.name("consumedAt")).click()
        driver!!.findElement(By.name("consumedAt")).sendKeys("2024-12-28T19:49")

        // Submit Form
        driver!!.findElement(By.id("addNutrition")).click()
        driver!!.findElement(By.cssSelector(".btn-primary")).click()

        // Scroll to the top of the page
        js!!.executeScript("window.scrollTo(0,0)")

        // Assert Success Message
        Assert.assertThat(driver!!.switchTo().alert().text, CoreMatchers.`is`("Nutrition entry added successfully"))
    }
}
