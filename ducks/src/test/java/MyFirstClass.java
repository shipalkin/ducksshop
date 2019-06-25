import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyFirstClass {
  public WebDriver wd;

  @Test
  public void adminLogIn() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
    wd.findElement(By.cssSelector("input[type='text']")).click();
    wd.findElement(By.cssSelector("input[type='text']")).clear();
    wd.findElement(By.cssSelector("input[type='text']")).sendKeys("admin");
    wd.findElement(By.cssSelector("input[type='password']")).click();
    wd.findElement(By.cssSelector("input[type='password']")).clear();
    wd.findElement(By.cssSelector("input[type='password']")).sendKeys("admin");
    wd.findElement(By.cssSelector("button")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("div[class='notice success']")).isDisplayed(), "Мы не залогинились!");
  }


}
