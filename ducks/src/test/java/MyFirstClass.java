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

  public void setTextIntoInput(By locator, String text) {
    wd.findElement(locator).click();
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

  public void adminLogIn(String login, String password) {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
    setTextIntoInput(By.cssSelector("input[type='text']"), login);
    setTextIntoInput(By.cssSelector("input[type='password']"), password);
    wd.findElement(By.cssSelector("button")).click();
  }

  @Test
  public void logInCheck() {
    adminLogIn("admin", "admin");
    Assert.assertTrue(wd.findElement(By.cssSelector("div[class='notice success']")).isDisplayed(), "Мы не залогинились!");
    wd.quit();
  }

  @Test(description = "Добавление нового продукта без фотографии")
  public void addNewProductNoImg() {
    adminLogIn("admin", "admin");
    wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product']")).click();
    wd.findElement(By.cssSelector("input[value='1']")).click();
    setTextIntoInput(By.cssSelector("input[name='name[en]']"), "NewDuckNoImg");
    setTextIntoInput(By.cssSelector("input[name='code']"), "123321");
    wd.findElement(By.cssSelector("button[name='save']")).click();
    wd.quit();
  }

  @Test(description = "Добавление нового пользака на сайт и ассерт что он залогинился, + тест по удалению этого пользака для стабильности что бы тест был не одноразовы а бегал бесконечно")
  public void newCustomerAndLogin() {
    wd = new ChromeDriver();
    wd.get("http://localhost/litecart/en/");
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/create_account']")).click();
    setTextIntoInput(By.cssSelector("input[name='firstname']"), "Shipalka");
    setTextIntoInput(By.cssSelector("input[name='lastname']"), "Ivanov");
    setTextIntoInput(By.cssSelector("input[name='address1']"), "Pushkina");
    setTextIntoInput(By.cssSelector("input[name='postcode']"), "295000");
    setTextIntoInput(By.cssSelector("input[name='city']"), "Shipalkacity");
    setTextIntoInput(By.cssSelector("input[name='email']"), "23456@mail.ru");
    setTextIntoInput(By.cssSelector("input[name='phone']"), "9787777777");
    setTextIntoInput(By.cssSelector("input[name='password']"), "123456");
    setTextIntoInput(By.cssSelector("input[name='confirmed_password']"), "123456");
    wd.findElement(By.cssSelector("button[name='create_account']")).click();
    wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/logout']")).click();
    setTextIntoInput(By.cssSelector("input[name='email']"), "123456@mail.ru");
    setTextIntoInput(By.cssSelector("input[name='password']"), "123456");
    wd.findElement(By.cssSelector("button[name='login']")).click();
    Assert.assertTrue(wd.findElement(By.cssSelector("a[href='http://localhost/litecart/en/logout']")).isDisplayed(), "Мы не залогинились!");
    adminLogIn("admin", "admin");
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
    adminLogIn("admin", "admin");
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
    adminLogIn("admin", "admin");
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
    adminLogIn("admin", "admin");
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
    adminLogIn("admin", "admin");
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
    adminLogIn("admin", "admin");
    wd.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    wd.findElement(By.xpath("//label //input[@value='1']")).click();
    setTextIntoInput(By.xpath("//input[@name='name[en]']"), "testItem");
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.get("http://localhost/litecart/en/");
    setTextIntoInput(By.xpath("//input[@type='search']"), "testItem");
    wd.findElement(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
    List<WebElement> searchResult = wd.findElements(By.xpath("//div[@class='name']"));
    Assert.assertTrue(searchResult.size() > 0, "Товара нет такого!");
    wd.quit();
  }

  @Test(description = "Тест по созданию уникального названия для каждого товара")
  public void newItemWithUniqueName() {
    adminLogIn("admin", "admin");
    wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=0");
    List<WebElement> itemsCatalog = wd.findElements(By.xpath("//table[@class='dataTable']//a[contains(text(), 'newDuck')]"));
    int i = itemsCatalog.size();
    wd.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    wd.findElement(By.xpath("//label //input[@value='1']")).click();
    setTextIntoInput(By.xpath("//input[@name='name[en]']"), "newDuck" + i);
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.quit();
  }

  @Test(description = "После каждого запуска создает уникальную утку с уникальным названием и проверят что такая утка отображается 1 в выборке поиска по сайту")
  public void newUniqueItemAndSearchThisItem() {
    adminLogIn("admin", "admin");
    wd.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=0");
    List<WebElement> itemsCatalog = wd.findElements(By.xpath("//table[@class='dataTable']//a[contains(text(), 'newDuck')]"));
    int i = itemsCatalog.size();
    wd.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    wd.findElement(By.xpath("//label //input[@value='1']")).click();
    setTextIntoInput(By.xpath("//input[@name='name[en]']"), "newDuck" + i);
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.get("http://localhost/litecart/en/");
    setTextIntoInput(By.xpath("//input[@type='search']"), "newDuck" + i);
    wd.findElement(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
    List<WebElement> searchResult = wd.findElements(By.xpath("//div[@id='box-product']"));
    Assert.assertTrue(searchResult.size() == 1, "Товара нет такого!");
    wd.quit();
  }

  @Test(description = "Создает товар заполняя несколько инпутов, затем ищет его и сверяяет что нашет тот который искал, и товар содержит все те поля которые есть")
  public void newItemSearchThisItemAndEqualsStringov() {
    adminLogIn("admin", "admin");
    String productName = "newDucknew";
    String productDescription = "newDucknewKeywords";
    wd.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    wd.findElement(By.xpath("//label //input[@value='1']")).click();
    setTextIntoInput(By.xpath("//input[@name='name[en]']"), productName);
    wd.findElement(By.xpath("//a[@href='#tab-information']")).click();
    setTextIntoInput(By.xpath("//div[@class='trumbowyg-editor']"), productDescription);
    wd.get("http://localhost/litecart/en/");
    setTextIntoInput(By.xpath("//input[@type='search']"), productName);
    wd.findElement(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
    Assert.assertEquals(wd.findElement(By.xpath("//h1")).getText(), productName);
    Assert.assertEquals(wd.findElement(By.xpath("//div[@class='tab']")).getText(), productDescription);
    wd.quit();
  }

  @Test(description = "Создается пользователь В админке, ищется, Ассертятся все поля созданного пользователя")
  public void newAdminUserSearchThisUserAndAssertInputs() {
    adminLogIn("admin", "admin");
    String userName = "Nikitka";
    String userPassword = "Nikita";
    String userDateAndTimeBlockedUntil = "14.05.199333 14:55";
    String userDateAndTimeExpires = "14.05.199333 15:55";
    wd.get("http://localhost/litecart/admin/?app=users&doc=edit_user&page=1");
    wd.findElement(By.xpath("//input[@name='status']")).click();
    setTextIntoInput(By.xpath("//input[@name='username']"), userName);
    setTextIntoInput(By.xpath("//input[@name='password']"), userPassword);
    setTextIntoInput(By.xpath("//input[@name='confirmed_password']"), userPassword);
    wd.findElement(By.xpath("//input[@name='date_blocked']")).sendKeys(userDateAndTimeBlockedUntil);
    wd.findElement(By.xpath("//input[@name='date_expires']")).sendKeys(userDateAndTimeExpires);
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.findElement(By.xpath("//tbody //a[contains(text(), 'Nikitka')]")).click();
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='username']")).getAttribute("Value"), userName);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='date_blocked']")).getAttribute("Value"), userDateAndTimeBlockedUntil);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='date_expires']")).getAttribute("Value"), userDateAndTimeExpires);
    // ой вэй короче поля не сохранябтся с датами, но зато вроде разобрался как работать с полями типа "input type="datetime-local""
    wd.quit();
  }

  @Test(description = "Создается пользак(покупатель), затем сверяются заполненные поля на корректность сведений")
  public void newCustomerAndAssertHisInputs() {
    adminLogIn("admin", "admin");
    //UserData createdUser = new UserData();
    String code = "295051";
    String emailAddress = "privet@mai.ru";
    String taxId = "100000";
    String company = "Doka2";
    String firstName = "Shipalka";
    String lastName = "Shipalkinov";
    String address1 = "lenina str.";
    String address2 = "leksina str.";
    String city = "Sain Simferopol";
    String postCode = "295000";
    String phone = "80652111111";
    String mobilePhone = "89992222222";
    wd.get("http://localhost/litecart/admin/?app=customers&doc=edit_customer&page=1");
    setTextIntoInput(By.xpath("//input[@name='code']"), code);
    setTextIntoInput(By.xpath("//input[@name='email']"), emailAddress);
    setTextIntoInput(By.xpath("//input[@name='tax_id']"), taxId);
    setTextIntoInput(By.xpath("//input[@name='company']"), company);
    setTextIntoInput(By.xpath("//input[@name='firstname']"), firstName);
    setTextIntoInput(By.xpath("//input[@name='lastname']"), lastName);
    setTextIntoInput(By.xpath("//input[@name='address1']"), address1);
    setTextIntoInput(By.xpath("//input[@name='address2']"), address2);
    setTextIntoInput(By.xpath("//input[@name='city']"), city);
    setTextIntoInput(By.xpath("//input[@name='postcode']"), postCode);
    setTextIntoInput(By.xpath("//input[@name='phone']"), phone);
    setTextIntoInput(By.xpath("//input[@name='mobile']"), mobilePhone);
    wd.findElement(By.xpath("//input[@name='new_password']")).sendKeys("123123");
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.findElement(By.xpath("//table //a[contains(text(), 'Shipalka Shipalkinov')]")).click();
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='code']")).getAttribute("Value"), code);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='email']")).getAttribute("Value"), emailAddress);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='tax_id']")).getAttribute("Value"), taxId);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='company']")).getAttribute("Value"), company);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='firstname']")).getAttribute("Value"), firstName);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='lastname']")).getAttribute("Value"), lastName);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='address1']")).getAttribute("Value"), address1);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='address2']")).getAttribute("Value"), address2);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='city']")).getAttribute("Value"), city);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='postcode']")).getAttribute("Value"), postCode);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='phone']")).getAttribute("Value"), phone);
    Assert.assertEquals(wd.findElement(By.xpath("//input[@name='mobile']")).getAttribute("Value"), mobilePhone);
    wd.quit();
  }

  @Test
  public void tryCreateAlreadyExistedCustomer() {
    adminLogIn("admin", "admin");
    wd.get("http://localhost/litecart/admin/?app=customers&doc=customers"); // переписать через .click();
    wd.findElement(By.xpath("//tr[@class='row'][1] //td[5]//a[1]")).click();
    UserData existedCustomer = getUserDataFromAdminPanel();
    wd.get("http://localhost/litecart/en/");
    wd.findElement(By.cssSelector("form[name='login_form'] a[href*='create_account']")).click();
    fillRegistrationForm(existedCustomer);
    wd.findElement(By.xpath("//input[@name='password']")).sendKeys("123123");
    wd.findElement(By.xpath("//input[@name='confirmed_password']")).sendKeys("123123");
    wd.findElement(By.xpath("//button[@type='submit']")).click();
    Assert.assertTrue(wd.findElement(By.xpath("//div[@class='notice errors']")).isDisplayed(),
            "Пользователя создать невозможно, такой пользователь уже зарегистрирован");
    wd.quit();
  }

  private UserData getUserDataFromAdminPanel() {
    UserData existedUser = new UserData();
    existedUser.setTaxId(wd.findElement(By.xpath("//input[@name='tax_id']")).getAttribute("Value"));
    existedUser.setCompany(wd.findElement(By.xpath("//input[@name='company']")).getAttribute("Value"));
    existedUser.setFirstName(wd.findElement(By.xpath("//input[@name='firstname']")).getAttribute("Value"));
    existedUser.setLastName(wd.findElement(By.xpath("//input[@name='lastname']")).getAttribute("Value"));
    existedUser.setAddress1(wd.findElement(By.xpath("//input[@name='address1']")).getAttribute("Value"));
    existedUser.setAddress2(wd.findElement(By.xpath("//input[@name='address2']")).getAttribute("Value"));
    existedUser.setPostCode(wd.findElement(By.xpath("//input[@name='postcode']")).getAttribute("Value"));
    existedUser.setCity(wd.findElement(By.xpath("//input[@name='city']")).getAttribute("Value"));
    existedUser.setEmailAddress(wd.findElement(By.xpath("//input[@name='email']")).getAttribute("Value"));
    existedUser.setPhone(wd.findElement(By.xpath("//input[@name='phone']")).getAttribute("Value"));
    return existedUser;
  }

  private void fillRegistrationForm(UserData registration) {
    wd.findElement(By.xpath("//input[@name='tax_id']")).sendKeys(registration.getTaxId());
    wd.findElement(By.xpath("//input[@name='company']")).sendKeys(registration.getCompany());
    wd.findElement(By.xpath("//input[@name='firstname']")).sendKeys(registration.getFirstName());
    wd.findElement(By.xpath("//input[@name='lastname']")).sendKeys(registration.getLastName());
    wd.findElement(By.xpath("//input[@name='address1']")).sendKeys(registration.getAddress1());
    wd.findElement(By.xpath("//input[@name='address2']")).sendKeys(registration.getAddress2());
    wd.findElement(By.xpath("//input[@name='postcode']")).sendKeys(registration.getPostCode());
    wd.findElement(By.xpath("//input[@name='city']")).sendKeys(registration.getCity());
    wd.findElement(By.xpath("//input[@name='email']")).sendKeys(registration.getEmailAddress());
    wd.findElement(By.xpath("//input[@name='phone']")).sendKeys(registration.getPhone());
  }

  @Test(description = "")
  public void newCustomerSecondTest() {
    adminLogIn("admin", "admin");
    UserData createdUser = new UserData();
    createdUser.setCode("2951051");
    createdUser.setEmailAddress("privset@mai.ru");
    createdUser.setTaxId("1001000");
    createdUser.setCompany("Doksa2");
    createdUser.setFirstName("Shipsalka");
    createdUser.setLastName("Shipsalkinov");
    createdUser.setAddress1("lensina str.");
    createdUser.setAddress2("lekssina str.");
    createdUser.setCity("Saints Simferopol");
    createdUser.setPostCode("295001");
    createdUser.setPhone("80652111222");
    createdUser.setMobilePhone("8999111222");
    wd.get("http://localhost/litecart/admin/?app=customers&doc=edit_customer&page=1");
    fillUserDataInAdmin(createdUser);
    System.out.println(createdUser);
    wd.findElement(By.xpath("//input[@name='new_password']")).sendKeys("123123");
    wd.findElement(By.xpath("//button[@name='save']")).click();
    wd.findElement(By.xpath("//table //a[contains(text(), 'Shipsalka Shipsalkinov')]")).click();
    UserData existedUser = getExistedUserDate();
    System.out.println(existedUser);
    Assert.assertEquals(createdUser, existedUser);
    wd.quit();
  }

  private UserData getExistedUserDate() {
    UserData existedUser = new UserData();
    existedUser.setCode(wd.findElement(By.xpath("//input[@name='code']")).getAttribute("Value"));
    existedUser.setEmailAddress(wd.findElement(By.xpath("//input[@name='email']")).getAttribute("Value"));
    existedUser.setTaxId(wd.findElement(By.xpath("//input[@name='tax_id']")).getAttribute("Value"));
    existedUser.setCompany(wd.findElement(By.xpath("//input[@name='company']")).getAttribute("Value"));
    existedUser.setFirstName(wd.findElement(By.xpath("//input[@name='firstname']")).getAttribute("Value"));
    existedUser.setLastName(wd.findElement(By.xpath("//input[@name='lastname']")).getAttribute("Value"));
    existedUser.setAddress1(wd.findElement(By.xpath("//input[@name='address1']")).getAttribute("Value"));
    existedUser.setAddress2(wd.findElement(By.xpath("//input[@name='address2']")).getAttribute("Value"));
    existedUser.setCity(wd.findElement(By.xpath("//input[@name='city']")).getAttribute("Value"));
    existedUser.setPostCode(wd.findElement(By.xpath("//input[@name='postcode']")).getAttribute("Value"));
    existedUser.setPhone(wd.findElement(By.xpath("//input[@name='phone']")).getAttribute("Value"));
    existedUser.setMobilePhone(wd.findElement(By.xpath("//input[@name='mobile']")).getAttribute("Value"));
    return existedUser;
  }

  private void fillUserDataInAdmin(UserData createdUser) {
    setTextIntoInput(By.xpath("//input[@name='code']"), createdUser.getCode());
    setTextIntoInput(By.xpath("//input[@name='email']"), createdUser.getEmailAddress());
    setTextIntoInput(By.xpath("//input[@name='tax_id']"), createdUser.getTaxId());
    setTextIntoInput(By.xpath("//input[@name='company']"), createdUser.getCompany());
    setTextIntoInput(By.xpath("//input[@name='firstname']"), createdUser.getFirstName());
    setTextIntoInput(By.xpath("//input[@name='lastname']"), createdUser.getLastName());
    setTextIntoInput(By.xpath("//input[@name='address1']"), createdUser.getAddress1());
    setTextIntoInput(By.xpath("//input[@name='address2']"), createdUser.getAddress2());
    setTextIntoInput(By.xpath("//input[@name='city']"), createdUser.getCity());
    setTextIntoInput(By.xpath("//input[@name='postcode']"), createdUser.getPostCode());
    setTextIntoInput(By.xpath("//input[@name='phone']"), createdUser.getPhone());
    setTextIntoInput(By.xpath("//input[@name='mobile']"), createdUser.getMobilePhone());

  }

  }

