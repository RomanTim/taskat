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

    public AccPageNewEmail(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void fillNewEmail(){
        toField.sendKeys(DataForTst.getToEmail());
        subjectField.sendKeys(DataForTst.getSubject());
        driver.switchTo().frame(frameArea);
        emailField.clear();
        emailField.sendKeys(EmailText.getEmailText());
        driver.switchTo().defaultContent();
    }

    public String getEmailText() {
        driver.switchTo().frame(frameArea);
        String message = emailField.getText();
        driver.switchTo().defaultContent();
        return message;
    }

    public boolean clickSaveDraftButton(){
        saveButton.click();
        //save time for draft identification
        DataForTst.setDateLastDraft(new SimpleDateFormat("HH:mm").format(new Date()));
        //check successful draft
        return savedLabel.isDisplayed();
    }

    public boolean checkNewEmailPage(){
        return (sendButton.isDisplayed() && saveButton.isDisplayed() && toField.isDisplayed());
    }

    public String getToField() {
        return toField.getText();
    }

    public String getSubjectField() {
        return subjectField.getText();
    }
}
