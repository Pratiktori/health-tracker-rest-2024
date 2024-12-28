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
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class DeleteSleepTest {
    private var driver: WebDriver? = null
    private var js: JavascriptExecutor? = null
    private var wait: WebDriverWait? = null

    @Before
    fun setUp() {
        driver = ChromeDriver()
        js = driver as JavascriptExecutor
        wait = WebDriverWait(driver!!, Duration.ofSeconds(10))
    }

    @After
    fun tearDown() {
        driver!!.quit()
    }

    @Test
    fun deleteSleep() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(784, 816)
        driver!!.findElement(By.cssSelector(".container-fluid > .col:nth-child(2) .btn")).click()
        driver!!.findElement(By.cssSelector(".list-group-item:nth-child(6) .fas")).click()

        // Verify confirmation alert
        val confirmationAlert = driver!!.switchTo().alert()
        Assert.assertThat(
            confirmationAlert.text,
            CoreMatchers.`is`("Are you sure you want to delete this sleep record? This action cannot be undone.")
        )
        confirmationAlert.accept()

        // Verify deletion failure alert
        wait!!.until(ExpectedConditions.alertIsPresent())
        val failureAlert = driver!!.switchTo().alert()
        Assert.assertThat(
            failureAlert.text,
            CoreMatchers.`is`("Failed to delete sleep record. Please try again.")
        )
        failureAlert.accept()
    }
}
