import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class EmailGroupTest {
    private static WebDriver driver;

    private static LoginPage loginPage;
    private static AccPageMain accPageMain;
    private static AccPageEmail accPageNewMail;
    private static AccPageDrafts accPageDrafts;
    private static AccPageSent accPageSent;

    // Действие: Авторизоваться в системе пользователем
    // Ожидаемый результат: Отображается на экране почта пользователя
    @Test (groups={"testDraft","testSent"})
    public void step1_loginMailRu() {
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.checkLoginPage(),"This wasn't login page");
        accPageMain = loginPage.loginIn();
        Assert.assertTrue(accPageMain.checkAccPageMain(), "This wasn't new email page");
    }

    // Действие: Нажать кнопку «Написать письмо».
    // Ожидаемый результат: Открылась форма создания нового письма
    @Test (groups={"testDraft","testSent"})
    public void step2_clickNewEmail() {
        accPageNewMail = accPageMain.clickNewEmail();
        Assert.assertTrue(accPageNewMail.checkNewEmailPage(), "This wasn't new email page");
    }

    // Действие: Заполнить поля «Кому», «Тема» и «Тело» и сохранить письмо как черновик.
    // Ожидаемый результат: Письмо сохранилось как черновик
    @Test (groups={"testDraft"})
    public void step3_fillEmailDraft() {
        accPageNewMail.fillNewEmail();
        //Assert.assertTrue(accPageNewMail.clickSaveDraftButton(), "The message wasn't saved in drafts");
        accPageNewMail.clickSaveDraftButton();
    }

    // Действие: Заполнить поля «Кому», «Тема» и «Тело» корректными/допустимыми данными и отправить письмо.
    // Ожидаемый результат: Письмо отправилось успешно
    @Test (groups={"testSent"})
    public void step3_fillEmailSent() {
        accPageNewMail.fillNewEmail();
        //Assert.assertTrue(accPageNewMail.clickSaveDraftButton(), "The message wasn't saved in drafts");
        accPageNewMail.clickSendButton();
    }

    // Действие: Открыть папку с черновиками и проверить поля «Кому», «Тема» и «Тело» созданного письма
    // Ожидаемый результат: «Кому», «Тема» и «Тело» письма заполнены данными, которые использовались в сценарии №3
    @Test (groups={"testDraft"})
    public void step4_openDrafts() throws InterruptedException {
        Thread.sleep(10000);
        accPageDrafts = accPageMain.clickDrafts();
        Assert.assertTrue(accPageDrafts.checkAccPageDrafts(), "This wasn't drafts page");
        Assert.assertTrue(accPageDrafts.clickLastDraftEmail(), "The email wasn't found");
        Assert.assertTrue(accPageNewMail.getToFieldAfter().contains(DataForTst.getToEmail()),"To field was filled incorrectly");
        Assert.assertEquals(accPageNewMail.getSubjectField(), DataForTst.getSubject(), "Subject field was filled incorrectly");
        Assert.assertTrue(EmailText.checkEmailText(accPageNewMail.getEmailText()), "Body of email was filled incorrectly");
    }

    // Действие: Открыть папку с отправленными письмами и проверить поля «Кому», «Тема» и «Тело» отправленного письма
    // Ожидаемый результат: «Кому», «Тема» и «Тело» письма заполнены данными, которые использовались в сценарии №3
    @Test (groups={"testSent"})
    public void step4_openSent() throws InterruptedException {
        Thread.sleep(10000);
        accPageSent = accPageMain.clickSent();
        Assert.assertTrue(accPageSent.checkAccPageSent(), "This wasn't sent page");
        Assert.assertTrue(accPageSent.clickLastSentEmail(), "The email wasn't found");
        String[] allSentEmailField = accPageNewMail.getSentAllField();
        Assert.assertTrue(allSentEmailField[2].contains(DataForTst.getToEmail()),"To field was filled incorrectly");
        Assert.assertEquals(allSentEmailField[0], DataForTst.getSubject(), "Subject field was filled incorrectly");
        Assert.assertTrue(EmailText.checkEmailText(accPageNewMail.getSentEmailText()), "Body of email was filled incorrectly");
    }

    // Действие: Выход из системы с помощью нажатия «Выход»
    // Ожидаемый результат: На странице появилось поле для ввода логина и/или пароля.
    @Test (groups={"testDraft","testSent"})
    public void step5_logOut() {
        accPageMain.clickLogOut();
        Assert.assertTrue(loginPage.checkLoginPage(), "There is no login page");
    }

    @BeforeClass (groups={"testDraft","testSent"})
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./webdrivers/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://mail.ru");
    }

    @AfterClass (groups={"testDraft","testSent"})
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
