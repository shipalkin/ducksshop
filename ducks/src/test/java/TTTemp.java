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
    List<WebElement> sidebars = driver.findElements(By.xpath("//a[@class='m-menu__link m-menu__toggle']"));
    ArrayList<String> stringsidebars = new ArrayList<>();
    stringsidebars.add("Пользователи");
    stringsidebars.add("Проекты");
    stringsidebars.add("Отчеты");
    stringsidebars.add("KPI");
    stringsidebars.add("Активности");
    stringsidebars.add("Графики работы");
    stringsidebars.add("Моя зарплата");
    stringsidebars.add("Благодарности");
    stringsidebars.add("Навыки");
    stringsidebars.add("Карта ЛК");
    Assert.assertEquals(sidebars.size(), 10);
    driver.quit();
  }
}
