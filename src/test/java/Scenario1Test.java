import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Scenario1Test {
    private WebDriver driver;
    private String email = "testemailepam@inbox.ru";
    private String pass = "123pass";
    private LoginPage loginPage;
    private AccPageMain accPageMain;
    private AccPageNewEmail accPageNewMail;
    private AccPageDrafts accPageDrafts;
    private String toEmail = "testemailepam@inbox.ru";
    private String subject = "testemail";

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "./webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://mail.ru");
    }

    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    // Действие: Авторизоваться в системе пользователем
    // Ожидаемый результат: Отображается на экране почта пользователя
    @Test
    public void step1() {
        loginPage = new LoginPage(driver);
        accPageMain = loginPage.loginIn(email, pass);
    }

    // Действие: Нажать кнопку «Написать письмо».
    // Ожидаемый результат: Открылась форма создания нового письма
    @Test
    public void step2() {
        accPageNewMail = accPageMain.clickNewEmail();
    }

    // Действие: Заполнить поля «Кому», «Тема» и «Тело» и сохранить письмо как черновик.
    // Ожидаемый результат: Письмо сохранилось как черновик
    @Test
    public void step3() {
        accPageNewMail.fillNewEmail(toEmail, subject);
        accPageNewMail.clickSaveDraftButton();
    }

    // Действие: Открыть папку с черновиками и проверить поля «Кому», «Тема» и «Тело» созданного письма
    // Ожидаемый результат: «Кому», «Тема» и «Тело» письма заполнены данными, которые использовались в сценарии №3
    @Test
    public void step4() throws InterruptedException {
        Thread.sleep(10000);
        accPageDrafts = accPageMain.clickDrafts();
        accPageDrafts.setDateLastDraft(accPageNewMail.getDateLastDraft());
        accPageDrafts.clickLastDraftEmail(toEmail, subject);
    }

    // Действие: Выход из системы с помощью нажатия «Выход»
    // Ожидаемый результат: На странице появилось поле для ввода логина и/или пароля.
    @Test
    public void step5() {
        accPageMain.clickLogOut();
        Assert.assertTrue(loginPage.checkLoginPage(), "There is no login page");
    }
}
