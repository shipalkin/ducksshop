import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class MyFirstClass {
  public WebDriver wd;

  @Test(description = "Метод логина в админку")
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

  @Test(description = "Добавление нового продукта без фотографии")
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

  @Test(description = "Добавление нового пользака на сайт и ассерт что он залогинился, + тест по удалению этого пользака для стабильности что бы тест был не одноразовы а бегал бесконечно")
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

  @Test(description = "Создание коллекции со всеми продуктами и ассерт что есть стикер на товаре")
  public void assertStickersInItems() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> ducks = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    for (WebElement stickersNew : ducks) {
      Assert.assertTrue(stickersNew.findElement(By.cssSelector("div.sticker")).isDisplayed(), "Стикер НЕ отображается");
    }
    wd.quit();
  }

  @Test(description = "Проверка что у всех товаров есть приставка DUCK")
  public void assertDucksName() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    List<WebElement> duck = wd.findElements(By.cssSelector("ul[class='listing-wrapper products'] a[class='link']"));
    for (WebElement nameDucks : duck) {
      Assert.assertTrue(nameDucks.findElement(By.cssSelector("div.name")).getText().contains("Duck"), "У товара нет приставки Duck");
    }
    wd.quit();
  }

  @Test(description = "Проверка на наличие плашки в 1 конкретной утке")
  public void assertStickerInOneItem() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/rubber-ducks-c-1/");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/rubber-ducks-c-1/purple-duck-p-5']")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("a[class='main-image fancybox zoomable shadow'] div[class='sticker new']")).isDisplayed(), "Плашки нет!");
    wd.quit();
  }

  @Test(description = "Наличие фотки или картинки в каждом товаре и наличие  ACME corp. на всем товаре")
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

  @Test(description = "Проверка наличия всех элементов бокового меню")
  public void firstHomeWork0() {
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

  @Test(description = "Наличие всех строк в таблице статистики таблица на ГС админки http://localhost/litecart/admin/")
  public void firstHomeWork1() {
    adminLogIn();
    List<WebElement> statistic = wd.findElements(By.cssSelector("li[id='widget-stats'] tr[class]"));
    Assert.assertEquals(statistic.size(), 7);
    ArrayList<String> stat = new ArrayList<String>(Arrays.asList("Statistics", "Total Sales: $7.20", "Total Sales 2019: $7.20", "Total Sales July: $0.00", "Average Order Amount: $7.20", "Number of Customers: 0", "Number of Products: 7"));
    for (int i = 0; i < statistic.size(); i++) {
      Assert.assertEquals(statistic.get(i).getText(), stat.get(i));
    }
    wd.quit();
  }

  @Test(description = "проклацивание каждого элемента бокового меню и проверка наличия у них загловоков")
  public void secondHomeWork() throws InterruptedException {
    adminLogIn();
    List<WebElement> menuItemlist = wd.findElements(By.cssSelector("div[id='box-apps-menu-wrapper'] li[id] a span[class='fa-stack fa-lg icon-wrapper']"));
    Assert.assertEquals(menuItemlist.size(), 17, "Пунктов меньше 17");
    for (int i = 1; i <= menuItemlist.size(); i++) {
      String locator = "//ul[@id='box-apps-menu']/li[@id='app-'][" + i + "]//a";
      wd.findElement(By.xpath(locator)).click();
      sleep(500);
      Assert.assertTrue(wd.findElement(By.cssSelector("td[id='content'] h1")).isDisplayed());
      List<WebElement> menuPodMenu = wd.findElements(By.xpath("//li[contains(@id, 'doc-')]//a"));
      if (menuPodMenu.size() > 0) {
        for (int j = 1; j <= menuPodMenu.size(); j++) {
          String locatorPodMenu = "//li[contains(@id, 'doc-')][" + j + "]//a";
          wd.findElement(By.xpath(locatorPodMenu)).click();
          sleep(500);
          Assert.assertTrue(wd.findElement(By.cssSelector("td[id='content'] h1")).isDisplayed());
        }
      }
    }
    wd.quit();
  }

  @Test(description = "наглядный пример склеивания и сложения стриговое значение+цифра == склеивание 1цифра+2цифра == сумма")
  public void countTest() {
    int i = 1;
    String g = "1";
    int k = 1;
    System.out.println(i + g);
    System.out.println(i + k);
  }

  @Test(description = "Проверка соответствия названия пункта меню - заголовку страницы")
  public void secondHomeWork1() throws InterruptedException {
    adminLogIn();
    List<WebElement> menuItemlist = wd.findElements(By.cssSelector("div[id='box-apps-menu-wrapper'] li[id] a span[class='fa-stack fa-lg icon-wrapper']"));
    Assert.assertEquals(menuItemlist.size(), 17, "Пунктов меньше 17");
    for (int i = 1; i <= menuItemlist.size(); i++) {
      String locatorMenu = "//ul[@id='box-apps-menu']/li[@id='app-'][" + i + "]//a";
      wd.findElement(By.xpath(locatorMenu)).click();
      sleep(500);
      Assert.assertTrue(wd.findElement(By.cssSelector("td[id='content'] h1")).isDisplayed());
      if (!wd.findElement(By.xpath(locatorMenu)).getText().equals("Appearence")
              || !wd.findElement(By.xpath(locatorMenu)).getText().equals("Modules")
              || !wd.findElement(By.xpath(locatorMenu)).getText().equals("Reports")
              || !wd.findElement(By.xpath(locatorMenu)).getText().equals("Tax")
              || !wd.findElement(By.xpath(locatorMenu)).getText().equals("Translations")) {
      }
      Assert.assertEquals(wd.findElement(By.xpath(locatorMenu)).getText(), wd.findElement(By.cssSelector("td[id='content'] h1")).getText());
      List<WebElement> menuPodMenu = wd.findElements(By.xpath("//li[contains(@id, 'doc-')]//a"));
      if (menuPodMenu.size() > 0) {
        for (int j = 1; j <= menuPodMenu.size(); j++) {
          String locatorPodMenu = "//li[contains(@id, 'doc-')][" + j + "]//a";
          wd.findElement(By.xpath(locatorPodMenu)).click();
          sleep(500);
          Assert.assertTrue(wd.findElement(By.cssSelector("td[id='content'] h1")).isDisplayed());
          Assert.assertEquals(wd.findElement(By.xpath(locatorPodMenu)).getText(), wd.findElement(By.cssSelector("td[id='content'] h1")).getText());
        }
      }
      wd.quit();
    }

  }

  @Test(description = "Создание нового товара и поиск его по строке поиска")
  public void newItemAndSearch() {
    adminLogIn();
    wd.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    wd.findElement(By.xpath("//label //input[@value='1']")).click();
    wd.findElement(By.xpath("//input[@name='name[en]']")).click();
    wd.findElement(By.xpath("//input[@name='name[en]']")).clear();
    wd.findElement(By.xpath("//input[@name='name[en]']")).sendKeys("testItem");
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.get("http://localhost/litecart/en/");
    wd.findElement(By.xpath("//input[@type='search']")).click();
    wd.findElement(By.xpath("//input[@type='search']")).clear();
    wd.findElement(By.xpath("//input[@type='search']")).sendKeys("testItem");
    wd.findElement(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
    Assert.assertTrue(wd.findElement(By.xpath("//img[@class='image']")).isDisplayed(), "Товара нет такого!");
    wd.quit();
  }
  }

