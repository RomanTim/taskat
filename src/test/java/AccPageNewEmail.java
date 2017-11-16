import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccPageNewEmail {

    private WebDriver driver;
    private static String dateLastDraft;

    @FindBy(xpath = "//div[@data-name = 'send']")
    private WebElement sendButton;
    @FindBy(xpath = "//div[@data-name='saveDraft']")
    private WebElement saveButton;
    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private WebElement toField;
    @FindBy(xpath = "//input[@name='Subject']")
    private WebElement subjectField;
    @FindBy(xpath = "//iframe[contains(@id,'composeEditor_ifr')]")
    private WebElement frameArea;
    @FindBy(id = "tinymce")
    private WebElement emailField;
    @FindBy(xpath= "//div[@data-mnemo = 'saveStatus']/a[@href='/messages/drafts']")
    private WebElement savedLabel;

    public String getDateLastDraft(){
        return dateLastDraft;
    }

    public AccPageNewEmail(WebDriver driver){
        this.driver = driver;

        if (!driver.getTitle().contains("Новое письмо")){
            throw new IllegalStateException("This is not the new email page");
        }
        PageFactory.initElements(this.driver, this);
    }

    public void fillNewEmail(String toEmail, String subject) {
        toField.sendKeys(toEmail);
        subjectField.sendKeys(subject);
        driver.switchTo().frame(frameArea);
        emailField.clear();
        emailField.sendKeys(EmailText.getEmailText());
        driver.switchTo().defaultContent();
    }

    public void checkFilledEmail(String toEmail, String subject) {
        Assert.assertEquals(toField.getText(), toEmail, "To field is filled incorrectly");
        Assert.assertEquals(subjectField.getText(), subject, "Subject field is filled incorrectly");
        driver.switchTo().frame(frameArea);
        Assert.assertTrue(EmailText.checkEmailText(emailField.getText()), "Body of email is filled incorrectly");
        driver.switchTo().defaultContent();
    }

    public void clickSaveDraftButton(){
        saveButton.click();
        this.dateLastDraft = new SimpleDateFormat("HH:mm").format(new Date());
        Assert.assertTrue(savedLabel.isDisplayed(), "The message wasn't saved in drafts");
    }

}
