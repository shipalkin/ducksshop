import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TTTemp {
  public WebDriver driver;

  public void developLogInAM() {
    driver = new ChromeDriver();
    driver.get("https://tt-develop.quality-lab.ru");
    driver.findElement(By.xpath("//input[@id='username']")).click();
    driver.findElement(By.xpath("//input[@id='username']")).clear();
    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("Автотестам");
    driver.findElement(By.xpath("//input[@id='password']")).click();
    driver.findElement(By.xpath("//input[@id='password']")).clear();
    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
    driver.findElement(By.xpath("//input[@id='_submit']")).click();
  }

  public void developLogInManager() {
    driver = new ChromeDriver();
    driver.get("https://tt-develop.quality-lab.ru");
    driver.findElement(By.xpath("//input[@id='username']")).click();
    driver.findElement(By.xpath("//input[@id='username']")).clear();
    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("Автотестменеджер");
    driver.findElement(By.xpath("//input[@id='password']")).click();
    driver.findElement(By.xpath("//input[@id='password']")).clear();
    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
    driver.findElement(By.xpath("//input[@id='_submit']")).click();
  }

  public void developLogIn() {
    driver = new ChromeDriver();
    driver.get("https://tt-develop.quality-lab.ru");
    driver.findElement(By.xpath("//input[@id='username']")).click();
    driver.findElement(By.xpath("//input[@id='username']")).clear();
    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("Никита Крат");
    driver.findElement(By.xpath("//input[@id='password']")).click();
    driver.findElement(By.xpath("//input[@id='password']")).clear();
    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Shais6qu");
    driver.findElement(By.xpath("//input[@id='_submit']")).click();
  }

  @Test(description = "Проверка что пользователь успешно залогинился на девелопе")
  public void checkInLogIn() {
    developLogIn();
    Assert.assertTrue(driver.findElement(By.xpath("//i[@class='flaticon-users']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Ассерты на наличие составных частей профилья пользователя")
  public void categoryAndSubcategoryProfileAsserts() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='m-stack__item m-stack__item--center m-stack__item--middle']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//span[@id='headerName']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='m-stack__item m--pull-right']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//span[@class='m-list-search__result-item-text post-description']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//span[@id='headerName']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//span[@class='m-list-search__result-item-text post-description']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='stack-schedule']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='m_datatable projects-table-list m-datatable m-datatable--default m-datatable--loaded']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'Контакты')]]")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Проверка что аватарка пользователя находится слева вверху над блоком Резюме")
  public void checkAvatarPositionRegardingResumeBlock() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    int highestPointPhoto = driver.findElement(By.xpath("//div[@class='m-stack__item m-stack__item--center m-stack__item--middle']")).getLocation().y;
    int highestResumeBlock = driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'Резюме')]]")).getLocation().y;
    Assert.assertTrue(highestPointPhoto < highestResumeBlock);
    driver.quit();
  }

  @Test(description = "Тест проверяет наличие элементов бокового меню для роли Менеджер-админ")
  public void managerSideBarCheck() {
    developLogInManager();
    driver.findElement(By.id("m_aside_left_minimize_toggle")).click();
    List<WebElement> sidebars = driver.findElements(By.xpath("//a[@class='m-menu__link m-menu__toggle']//span[@class='m-menu__link-wrap']//span[@class='m-menu__link-text']"));
    ArrayList<String> stringSideBars = new ArrayList<>();
    stringSideBars.add("Пользователи");
    stringSideBars.add("Проекты");
    stringSideBars.add("Отчеты");
    stringSideBars.add("KPI");
    stringSideBars.add("Активности");
    stringSideBars.add("Графики работы");
    stringSideBars.add("Моя зарплата");
    stringSideBars.add("Благодарности");
    stringSideBars.add("Навыки");
    stringSideBars.add("Карта ЛК");
    Assert.assertEquals(sidebars.size(), 10);
    List<String> actualSideBars = new ArrayList<>();
    for (WebElement text : sidebars) {
      actualSideBars.add(text.getText());
    }
    Assert.assertEquals(actualSideBars, stringSideBars);
    driver.quit();
  }

  @Test(description = "Тест проверяет наличие элементов меню в профиле для роли Менеджер-админ")
  public void managerProfileMenu() {
    developLogInManager();
    driver.get("https://tt-develop.quality-lab.ru/user/352/show/profile");
    List<WebElement> managerProfileMenu = driver.findElements(By.xpath("//div[@class='m-stack__item m--pull-right']//li[@class='nav-item m-tabs__item']"));
    ArrayList<String> stringsProfileMenu = new ArrayList<>();
    stringsProfileMenu.add("Профиль");
    stringsProfileMenu.add("Навыки");
    stringsProfileMenu.add("Опыт работы");
    stringsProfileMenu.add("Проекты");
    stringsProfileMenu.add("Аккаунт-менеджмент");
    stringsProfileMenu.add("Образование");
    stringsProfileMenu.add("Иностранные языки");
    stringsProfileMenu.add("Учетная запись");
    stringsProfileMenu.add("Связи");
    Assert.assertTrue(driver.findElement(By.xpath("//a[@class='btn btn-brand m-btn']")).isDisplayed());
    ArrayList<String> actualProfileMenu = new ArrayList<>();
    for (WebElement menu : managerProfileMenu) {
      actualProfileMenu.add(menu.getText());
    }
    Assert.assertEquals(actualProfileMenu, stringsProfileMenu);
    driver.quit();
  }

  @Test(description = "Тест проверяет наличие элементов меню в профиле для группы АМ роли Пользователь")
  public void amUserProfileMenu() {
    developLogInAM();
    driver.get("https://tt-develop.quality-lab.ru/user/353/show/profile");
    List<WebElement> amProfileMenu = driver.findElements(By.xpath("//div[@class='m-stack__item m--pull-right']//li[@class='nav-item m-tabs__item']"));
    ArrayList<String> stringProfileMenu = new ArrayList<>();
    stringProfileMenu.add("Профиль");
    stringProfileMenu.add("Навыки");
    stringProfileMenu.add("Опыт работы");
    stringProfileMenu.add("Проекты");
    stringProfileMenu.add("Аккаунт-менеджмент");
    stringProfileMenu.add("Образование");
    stringProfileMenu.add("Иностранные языки");
    stringProfileMenu.add("Связи");
    ArrayList<String> actualProfileMenu = new ArrayList<>();
    for (WebElement menu : amProfileMenu) {
      actualProfileMenu.add(menu.getText());
    }
    Assert.assertEquals(actualProfileMenu, stringProfileMenu);
    driver.quit();
  }

  @Test(description = "Проверка цвета элемента меню профиля 'Профиль' и его подчеркивания")
  public void colorOfProfileInUserProfile() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    Assert.assertEquals(driver.findElement(By.xpath("//ul[@class='nav nav-tabs  m-tabs-line m-tabs-line--2x m-tabs-line--success']//a[@class='nav-link m-tabs__link active']")).getCssValue("color"), "rgba(114, 110, 201, 1)");
    Assert.assertEquals(driver.findElement(By.xpath("//ul[@class='nav nav-tabs  m-tabs-line m-tabs-line--2x m-tabs-line--success']//a[@class='nav-link m-tabs__link active']")).getCssValue("border-bottom"), "4px solid rgb(114, 110, 201)");
    driver.quit();
  }

  @Test(description = "При наведении на фотографию в профиле, курсор меняет вид на руку(поинтер)")
  public void pointerCursorInAvatarInProfile() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    Assert.assertEquals(driver.findElement(By.xpath("//img[@class='avatar']")).getCssValue("cursor"), "pointer");
    driver.quit();
  }

  @Test(description = "Кнопка “Сформировать резюме” (фиолетовая заливка) #716ACA")
  public void buttonCreateResumeIsPurpleColor() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    Assert.assertEquals(driver.findElement(By.xpath("//a[@class='btn btn-brand m-btn']")).getCssValue("background-color"), "rgba(113, 106, 202, 1)");
    driver.quit();
  }

  @Test(description = "По нажатию на кнопку “Сформировать резюме” на новой вкладке открывается превью резюме")
  public void previewResumeOpenInNewTab() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//a[@class='btn btn-brand m-btn']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
    driver.switchTo().window(tabs2.get(1));
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='page']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Блок “Резюме” состоит из:" + "1. Заголовок" + "2. Должности" + "3. Краткого резюме" + "4. Кнопка “Редактировать резюме” (АТ)")
  public void elementsOfBlockResumeInProfile() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    WebElement resumeBlockInProfile = driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'Резюме')]]"));
    Assert.assertTrue(resumeBlockInProfile.findElement(By.xpath(".//div[@class='m-portlet__head']")).isDisplayed());
    Assert.assertTrue(resumeBlockInProfile.findElement(By.xpath(".//div[@class='btn-group m-btn-group']")).isDisplayed());
    Assert.assertTrue(resumeBlockInProfile.findElement(By.xpath(".//div[@class='m-list-search__result-item'][1]")).isDisplayed());
    Assert.assertTrue(resumeBlockInProfile.findElement(By.xpath(".//div[@class='m-list-search__result-item'][2]")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Блок “График работы” состоит из" + "1. Заголовок" + "2. Таблица с графиком работ" + "3. Кнопка “Редактировать график” (АТ)")
  public void elementsOfBlockScheduleInProfile() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    WebElement scheduleBlockInProfile = driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'График работы')]]"));
    Assert.assertTrue(scheduleBlockInProfile.findElement(By.xpath(".//div[@class='m-portlet__head']")).isDisplayed());
    Assert.assertTrue(scheduleBlockInProfile.findElement(By.xpath(".//div[@class='btn-group m-btn-group']")).isDisplayed());
    Assert.assertTrue(scheduleBlockInProfile.findElement(By.xpath(".//div[@class='m-portlet__body']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Блок “Контакты” состоит из:" + "1. E-mail корпоративный" + "2. E-mail дополнительный" +
          "3. Скайп" + "4. Телефон основной" + "5. Телефон дополнительный" + "6. Адрес" + "7. Кнопка “Редактировать контакты” (АТ)")
  public void elementsOfBlockContactsInProfile() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    WebElement contactsBlockInProfile = driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'Контакты')]]"));
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text corporate-email']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text additional-email']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text skype']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text phone']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text additional-phone']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//span[@class='m-list-search__result-item-text city']")).isDisplayed());
    Assert.assertTrue(contactsBlockInProfile.findElement(By.xpath(".//div[@class='btn-group m-btn-group']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Блок “Окружения” состоит из:" + "1. Заголовок" + "2. Таблица окружений" + "3. Кнопка “+ Добавить устройство” (АТ)")
  public void elementsOfBlockEnvironment() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    WebElement environmentBlockInProfile = driver.findElement(By.xpath("//div[@id='m_tabs_6_1']//div[@class='m-portlet'][.//h3[contains(text(), 'Окружения')]]"));
    Assert.assertTrue(environmentBlockInProfile.findElement(By.xpath(".//div[@class='m-portlet__head']")).isDisplayed());
    Assert.assertTrue(environmentBlockInProfile.findElement(By.xpath(".//div[@class='btn-group m-btn-group']")).isDisplayed());
    Assert.assertTrue(environmentBlockInProfile.findElement(By.xpath(".//div[@class='m-portlet__body']")).isDisplayed());
    driver.quit();
  }

}
