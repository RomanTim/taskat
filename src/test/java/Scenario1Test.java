import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Scenario1Test {
    private static WebDriver driver;

    private static LoginPage loginPage;
    private static AccPageMain accPageMain;
    private static AccPageNewEmail accPageNewMail;
    private static AccPageDrafts accPageDrafts;

    // Действие: Авторизоваться в системе пользователем
    // Ожидаемый результат: Отображается на экране почта пользователя
    @Test
    public void step1_loginMailRu() {
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.checkLoginPage(),"This wasn't login page");
        accPageMain = loginPage.loginIn();
    }

    // Действие: Нажать кнопку «Написать письмо».
    // Ожидаемый результат: Открылась форма создания нового письма
    @Test
    public void step2_clickNewEmail() {
        accPageNewMail = accPageMain.clickNewEmail();
        Assert.assertTrue(accPageNewMail.checkNewEmailPage(), "This wasn't new email page");
    }

    // Действие: Заполнить поля «Кому», «Тема» и «Тело» и сохранить письмо как черновик.
    // Ожидаемый результат: Письмо сохранилось как черновик
    @Test
    public void step3_fillEmailForm() {
        accPageNewMail.fillNewEmail();
        Assert.assertTrue(accPageNewMail.clickSaveDraftButton(), "The message wasn't saved in drafts");
    }

    // Действие: Открыть папку с черновиками и проверить поля «Кому», «Тема» и «Тело» созданного письма
    // Ожидаемый результат: «Кому», «Тема» и «Тело» письма заполнены данными, которые использовались в сценарии №3
    @Test
    public void step4_openDrafts() throws InterruptedException {
        Thread.sleep(10000);
        accPageDrafts = accPageMain.clickDrafts();
        Assert.assertTrue(accPageDrafts.checkAccPageDrafts(), "This wasn't drafts page");
        Assert.assertTrue(accPageDrafts.clickLastDraftEmail(), "The email wasn't found");
        Assert.assertEquals(accPageNewMail.getToField(), DataForTst.getToEmail(),"To field was filled incorrectly");
        Assert.assertEquals(accPageNewMail.getSubjectField(), DataForTst.getSubject(), "Subject field was filled incorrectly");
        Assert.assertTrue(EmailText.checkEmailText(accPageNewMail.getEmailText()), "Body of email was filled incorrectly");
    }

    // Действие: Выход из системы с помощью нажатия «Выход»
    // Ожидаемый результат: На странице появилось поле для ввода логина и/или пароля.
    @Test
    public void step5_logOut() {
        accPageMain.clickLogOut();
        Assert.assertTrue(loginPage.checkLoginPage(), "There is no login page");
    }

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
}
