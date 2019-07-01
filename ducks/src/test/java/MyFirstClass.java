import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    wd.findElement(By.cssSelector("input[name='code']")).click();
    wd.findElement(By.cssSelector("input[name='code']")).clear();
    wd.findElement(By.cssSelector("input[name='code']")).sendKeys("123321");

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
  public void assertStickersInItems() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> ducks = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    for (WebElement stickersNew : ducks) {
      Assert.assertTrue(stickersNew.findElement(By.cssSelector("div.sticker")).isDisplayed(), "Стикер НЕ отображается");
      //System.out.println(stickersNew.findElement(By.cssSelector("div.name")).getText());
    }
    wd.quit();
  }

  @Test
  public void assertDucksName() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> duck = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    for (WebElement nameDucks : duck) {
      Assert.assertTrue(nameDucks.findElement(By.cssSelector("div.name")).getText().contains("Duck"), "У товара нет приставки Duck");
    }
    wd.quit();
  }

  @Test
  public void assertStickerInOneItem() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/rubber-ducks-c-1/purple-duck-p-5']")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("a[class='main-image fancybox zoomable shadow'] div[class='sticker new']")).isDisplayed(), "Плашки нет!");
    wd.quit();
  }

  @Test
  public void assertDucksPhoto() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> duck = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    for (WebElement photoDucks : duck) {
      Assert.assertTrue(photoDucks.findElement(By.cssSelector("img.image")).isDisplayed(), "Фото нет");
      Assert.assertTrue(photoDucks.findElement(By.cssSelector("div.manufacturer")).getText().contains("ACME Corp."), "Нет текста ACME corp.");
    }
    wd.quit();
  }
  // 1. Тест, который проверяет, что в админке все пункты меню на месте
  //- авторизоваться в админку
  //- посчитать количество пунктов меню слева
  //- проверить, что их 17 (кажется их там 17)
  //- проверить название каждого пункта меню (тут тебе нужно будет вручную создать список ожидаемых пунктов меню, поищешь, как это делать) и потом собрав список элементов пунктов меню через цикл фор - проверишь навзание каждого пункта.
  //сразу дам подсказку - тут нужно будет использовать цикл фор такого типа
  //for(int i = 0; i < собранныйСписокПунктовМеню.size; i++)

  @Test
  public void firstHomeWork() {
    wd = new ChromeDriver();
    adminLogIn();
    List<WebElement> elements = wd.findElements(By.cssSelector("div[id='box-apps-menu-wrapper'] li[id]"));
    Assert.assertEquals(elements.size(), 17, "Элементов не 17, ошибка!");
    ArrayList<String> menu = new ArrayList<String>(Arrays.asList("Appearence", "Catalog", "Countries", "Currencies", "Customers", "Geo Zones", "Languages",
            "Modules", "Orders", "Pages", "Reports", "Settings", "Slides", "Tax", "Translations", "Users", "vQmods"));
    for (int i = 0; i < elements.size(); i++) {
      Assert.assertEquals(elements.get(i).getText(), menu.get(i), "количество элементов не совпадает с эталонным!");
    }
    wd.quit();
  }
}
