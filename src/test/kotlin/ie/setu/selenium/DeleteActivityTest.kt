import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class DeleteActivityTest {
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
    fun deleteActivity() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(784, 816)

        // Navigate to activities list
        driver!!.findElement(By.cssSelector(".row > .col:nth-child(2) .btn")).click()

        // Click delete button for a specific activity
        driver!!.findElement(By.cssSelector(".list-group-item:nth-child(5) > .p2 > .btn")).click()

        // Confirm the first alert dialog
        val deleteAlertText = driver!!.switchTo().alert().text
        assertThat(deleteAlertText, CoreMatchers.`is`("Are you sure you want to delete this activity? This action cannot be undone."))
        driver!!.switchTo().alert().accept()

        // Confirm the second alert dialog
        val successAlertText = driver!!.switchTo().alert().text
        assertThat(successAlertText, CoreMatchers.`is`("Activity deleted successfully"))
        driver!!.switchTo().alert().accept()
    }
}
