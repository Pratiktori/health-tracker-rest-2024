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

class DeleteUserTest {
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
    fun deleteUser() {
        driver!!.get("http://localhost:7001/")
        driver!!.manage().window().size = Dimension(783, 816)

        // Navigate to user details
        driver!!.findElement(By.linkText("More Details...")).click()

        // Click delete button for a specific user
        driver!!.findElement(By.cssSelector(".list-group-item:nth-child(10) > .p2 > .btn")).click()

        // Confirm alert dialog
        val alertText = driver!!.switchTo().alert().text
        assertThat(alertText, CoreMatchers.`is`("Are you sure you want to delete this user? This action cannot be undone."))
        driver!!.switchTo().alert().accept()
    }
}
