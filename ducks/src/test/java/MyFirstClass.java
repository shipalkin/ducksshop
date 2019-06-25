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

  @Test
  public void addNewProductNoImg() {
    adminLogIn();
    wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product']")).click();
    wd.findElement(By.cssSelector("input[value='1']")).click();
    wd.findElement(By.cssSelector("input[name='name[en]']")).click();
    wd.findElement(By.cssSelector("input[name='name[en]']")).clear();
    wd.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("NewDuckNoImg");
    wd.findElement(By.cssSelector("button[name='save']")).click();
    wd.quit();
  }


}
