import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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

  @Test
  public void newCustomerAndLogin() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/create_account']")).click();
    wd.findElement(By.cssSelector("input[name='firstname']")).click();
    wd.findElement(By.cssSelector("input[name='firstname']")).clear();
    wd.findElement(By.cssSelector("input[name='firstname']")).sendKeys("Shipalka");
    wd.findElement(By.cssSelector("input[name='lastname']")).click();
    wd.findElement(By.cssSelector("input[name='lastname']")).clear();
    wd.findElement(By.cssSelector("input[name='lastname']")).sendKeys("Ivanov");
    wd.findElement(By.cssSelector("input[name='address1']")).click();
    wd.findElement(By.cssSelector("input[name='address1']")).clear();
    wd.findElement(By.cssSelector("input[name='address1']")).sendKeys("Pushkina");
    wd.findElement(By.cssSelector("input[name='postcode']")).click();
    wd.findElement(By.cssSelector("input[name='postcode']")).clear();
    wd.findElement(By.cssSelector("input[name='postcode']")).sendKeys("295000");
    wd.findElement(By.cssSelector("input[name='city']")).click();
    wd.findElement(By.cssSelector("input[name='city']")).clear();
    wd.findElement(By.cssSelector("input[name='city']")).sendKeys("Shipalkacity");
    wd.findElement(By.cssSelector("input[name='email']")).click();
    wd.findElement(By.cssSelector("input[name='email']")).clear();
    wd.findElement(By.cssSelector("input[name='email']")).sendKeys("123456@mail.ru");
    wd.findElement(By.cssSelector("input[name='phone']")).click();
    wd.findElement(By.cssSelector("input[name='phone']")).clear();
    wd.findElement(By.cssSelector("input[name='phone']")).sendKeys("9787777777");
    wd.findElement(By.cssSelector("input[name='password']")).click();
    wd.findElement(By.cssSelector("input[name='password']")).clear();
    wd.findElement(By.cssSelector("input[name='password']")).sendKeys("123456");
    wd.findElement(By.cssSelector("input[name='confirmed_password']")).click();
    wd.findElement(By.cssSelector("input[name='confirmed_password']")).clear();
    wd.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys("123456");
    wd.findElement(By.cssSelector("button[name='create_account']")).click();
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/logout']")).click();
    wd.findElement(By.cssSelector("input[name='email']")).click();
    wd.findElement(By.cssSelector("input[name='email']")).clear();
    wd.findElement(By.cssSelector("input[name='email']")).sendKeys("123456@mail.ru");
    wd.findElement(By.cssSelector("input[name='password']")).click();
    wd.findElement(By.cssSelector("input[name='password']")).clear();
    wd.findElement(By.cssSelector("input[name='password']")).sendKeys("123456");
    wd.findElement(By.cssSelector("button[name='login']")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/logout']")).isDisplayed(), "Мы не залогинились!");
    wd.quit();
    adminLogIn();
    wd.get("http://localhost/litecart/admin/?app=customers&doc=customers");
    wd.findElement(By.cssSelector("a[title='Edit']")).click();
    wd.findElement(By.cssSelector("button[name='delete']")).click();
    wd.switchTo().alert().accept();
    Assert.assertTrue(wd.findElement(By.cssSelector("form[name='customers_form']")).isDisplayed(), "Пользователь НЕ удалился!");
    wd.quit();
  }

  @Test
  public void assertSticker() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> ducks = wd.findElements(By.cssSelector("div[class='sticker sale']"));
    ducks.contains(By.cssSelector("div[class='sticker sale']"));
    System.out.println(ducks.size());
    wd.quit();
    // чушь он уже собирает коллекцию по локатору, смысл проверять что данная коллекция содержит эти локаторы. Это очевидно
  }

  @Test
  public void assertSticker2() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> ducks2 = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    ducks2.contains(By.cssSelector("div[class='sticker sale']"));
    wd.quit();
    //тут берет всех уточек, и проверяет что в списке уточек есть плашка сэйлс, тоже немного не то.
  }

  @Test
  public void assertSticker3() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> ducks2 = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    if (ducks2.contains(By.cssSelector("div[class='sticker sale']"))) {
      System.out.println("Товары со скидкой есть!");
    } else if (ducks2.contains(By.cssSelector("div[class='sticker new']"))) {
      System.out.println("Новые товары есть!");
    }
    wd.quit();
    // проверяются отображения плашек Нью и Сэйлс и если они есть то выведит в консоль сообщения соответствующие
  }

  @Test
  public void assertStickerInOneItem() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/rubber-ducks-c-1/purple-duck-p-5']")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("a[class='main-image fancybox zoomable shadow'] div[class='sticker new']")).isDisplayed(), "Плашки нет!");
    wd.quit();
  }
}
