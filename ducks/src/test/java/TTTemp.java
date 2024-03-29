import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
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

  @Test(description = "По нажатию на кнопку “Редактировать резюме” открывается окно “Изменение краткого резюме сотрудника” (AT) ПРГЛ 23")
  public void popUpOfEditResume() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Assert.assertTrue(driver.findElement(By.xpath("//form[@class='post-info']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Окно состоит из:" + "1. Название “Изменение краткого резюме сотрудника" + "2. Поле “Должность" +
          "3. Поле “Краткое резюме" + "4. Описание к полю “Краткое резюме" + "5. Кнопка “Закрыть без сохранения” (без заливки)" +
          "6. Кнопка “Сохранить” (фиолетовая заливка)" + "7. Кнопка крестик “Закрыть” (AT), ПРГЛ 24 P.S да тут проще и красивее было сделать" +
          " просто через Assert.assertEquals и геттекст но я захотел потренироваться в циклах и коллекциях")
  public void isElementsPresentInEditResumeForm() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    Assert.assertEquals(driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-dialog modal-lg']//h5[@class='modal-title']")).getText(), "Изменение краткого резюме сотрудника");
    List<WebElement> elementsOfEditeResumeForm = driver.findElements(By.xpath("//div[@class='form-group']//label[@class='form-control-label']"));
    ArrayList<String> elementsStrings = new ArrayList<>();
    elementsStrings.add("Должность");
    elementsStrings.add("Краткое резюме");
    ArrayList<String> actualTextInElements = new ArrayList<>();
    for (WebElement elementsInDiv : elementsOfEditeResumeForm) {
      actualTextInElements.add(elementsInDiv.getText());
    }
    Assert.assertEquals(actualTextInElements, elementsStrings);
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal-body']//p[contains(text(), 'Опишите себя в 2-3 предложениях (когда начался и как развивался путь в тестировании, что вы умеете делать круче других, какие ваши сильные стороны, что считаете самым главным в работе).')]")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-footer']//button[@class='btn btn-secondary']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//button[@class='btn btn-primary update-post-info']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal fade show']//button[@class='close']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Название “Изменение краткого резюме сотрудника" + "Выравнивание по левому краю ПРГЛ 25")
  public void headOfEditeResumeForm() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-header']//h5")).getCssValue("text-align:").isEmpty());
    driver.quit();
  }

  @Test(description = "Под заголовком присутствует горизонтальная полоса(бордер).ПРГЛ 26")
  public void borderOfHeaderEditResumeForm() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    Assert.assertEquals(driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-header']")).getCssValue("border-bottom"), "1px solid rgb(233, 236, 239)");
    driver.quit();
  }

  @Test(description = "Под названием расположено поле “Должность” (АТ)(Заголовком). ПРГЛ 27")
  public void checkFieldPostEditResumeForm() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    int fieldPostPosition = driver.findElement(By.xpath("//div[@class='form-group']//select")).getLocation().y;
    int headerPosition = driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-header']")).getLocation().y;
    Assert.assertTrue(fieldPostPosition > headerPosition);
    driver.quit();
  }

  @Test(description = "Поле “Должность” – выпадающий список с 22 элементами: ПРГЛ 28, ПРГЛ 30")
  public void elementsOfPostFieldInResumeForm() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    WebElement selectElements = driver.findElement(By.xpath("//div[@class='form-group']//select"));
    List<WebElement> selectedAllOptions = selectElements.findElements(By.xpath("./option"));
    List<String> textOfElements = new ArrayList<>();
    for (WebElement nameOfElement : selectedAllOptions) {
      textOfElements.add(nameOfElement.getText().trim());
    }
    List<String> stringsOfElements = new ArrayList();
    stringsOfElements.add("HR");
    stringsOfElements.add("Административный директор");
    stringsOfElements.add("Аккаунт-менеджер");
    stringsOfElements.add("Аналитик");
    stringsOfElements.add("Ведущий специалист по тестированию");
    stringsOfElements.add("Директор по развитию бизнеса");
    stringsOfElements.add("Заместитель генерального директора");
    stringsOfElements.add("Куратор образовательных проектов");
    stringsOfElements.add("Маркетолог");
    stringsOfElements.add("Менеджер по продажам");
    stringsOfElements.add("Преподаватель английского языка");
    stringsOfElements.add("Разработчик");
    stringsOfElements.add("Руководитель департамента");
    stringsOfElements.add("Руководитель отдела автоматизации тестирования");
    stringsOfElements.add("Системный администратор");
    stringsOfElements.add("Специалист по автоматизированному тестированию");
    stringsOfElements.add("Специалист по нагрузочному тестированию");
    stringsOfElements.add("Специалист по тестированию");
    stringsOfElements.add("Тест-менеджер");
    stringsOfElements.add("Технический директор");
    stringsOfElements.add("Фея");
    stringsOfElements.add("Финансист");
    Assert.assertEquals(textOfElements, stringsOfElements);
    driver.quit();
  }

  @Test(description = "В выпадающем списке можно выбрать только один вариант ПРГЛ 29 этот тест включает в себя неолько проверок" +
          "1. что атрибут мулльтиплай есть и 2. что выбор нескольких вариантов невозможен")
  public void multiplineSelectInDropList() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    WebElement dropList = driver.findElement(By.xpath("//div[@class='form-group']//select"));
    Select selectElements = new Select(dropList);
    Assert.assertFalse(selectElements.isMultiple());
    //Конец первой проверки
    List<WebElement> variantsInDropList = driver.findElements(By.xpath("//div[@class='form-group']//option"));
    for (WebElement variantGetText : variantsInDropList) {
      if (variantGetText.getText().equalsIgnoreCase("HR")) ;
      variantGetText.getText().equalsIgnoreCase("Административный директор");
      {
        variantGetText.click();
      }
    }
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[@class='btn btn-primary update-post-info']")).click();
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Assert.assertFalse(driver.findElement(By.xpath("//div[@class='form-group']//option[@value='6']")).isSelected());
    Assert.assertFalse(driver.findElement(By.xpath("//div[@class='form-group']//option[@value='9']")).isSelected());
    driver.quit();
  }

  @Test(description = "Под полем “Должность” расположено поле “Краткое резюме” (AT), ПРГЛ 31")
  public void placeOfFieldResume() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    int postField = driver.findElement(By.xpath("//form[@class='post-info']//select[@class='form-control m-input']")).getLocation().getY();
    int resumeField = driver.findElement(By.xpath("//div[@class='form-group']//textarea[@class='form-control']")).getLocation().getY();
    Assert.assertTrue(postField < resumeField);
    driver.quit();
  }

  @Test(description = "Поле “Краткое резюме” – текстовое поле для ввода (AT), ПРГЛ 32")
  public void resumeFieldIsTextArea() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='form-group']//textarea[@class='form-control']")).isDisplayed());
    driver.quit();
  }

  @Test(description = "Поведение при наведении курсора на поле “Краткое резюме” (меняет вид на текстовый) ПРГЛ 33")
  public void resumeFieldHasTextCursor() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Assert.assertEquals(driver.findElement(By.xpath("//div[@class='form-group']//textarea[@class='form-control']")).getCssValue("cursor"), "text");
    driver.quit();
  }

  @Test(description = "Поле Краткое резюме (можно вручную расширять), ПРГЛ 34")
  public void resumeFieldCanBeResized() {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Assert.assertEquals(driver.findElement(By.xpath("//div[@class='form-group']//textarea[@class='form-control']")).getCssValue("resize"), "vertical");
    driver.quit();
  }

  @Test(description = "При нажатии на кнопку “Закрыть без сохранения” форма закрывается изменения не вносятся (АТ) ПРГЛ 35")
  public void noChangesToTheFieldWhenCloseButtonClicked() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    String textBeforeQuit = driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).getText();
    driver.findElement(By.xpath("//div[@class='modal fade show']//button[contains(text(), 'Закрыть без сохранения')]")).click();
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    String textAfterQuit = driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).getText();
    Assert.assertEquals(textAfterQuit, textBeforeQuit);
    driver.quit();
  }

  @Test(description = "При нажатии на кнопку “Закрыть без сохранения” изменения в форме не сохраняются.(АТ) ПРГЛ 36(БАГ ЕСТЬ НЕ ДОЛЖЕН РАБОТАТЬ ТЕСТ на 17.12.19")
  public void noChangesToTheFieldsInsideFormWhenCloseButtonClicked() throws InterruptedException {
    developLogIn();
    driver.get("https://tt-develop.quality-lab.ru/user/274/show/profile");
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    String textBeforeQuit = driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).getText();
    driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).click();
    driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).clear();
    driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).sendKeys("123123Test");
    Thread.sleep(3000);
    driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-footer']//button[contains(text(), 'Закрыть без сохранения')]")).click();
    driver.findElement(By.xpath("//div[@class='modal fade show']//div[@class='modal-footer']//button[contains(text(), 'Закрыть без сохранения')]")).click();
    driver.findElement(By.xpath("//button[@class='btn btn-brand m-btn m-btn--icon btn-outline-second']")).click();
    Thread.sleep(2000);
    String textAfterQuit = driver.findElement(By.xpath("//div[@class='modal-body']//textarea[@id='post-description']")).getText();
    Assert.assertEquals(textBeforeQuit, textAfterQuit);
    driver.quit();
  }
}
