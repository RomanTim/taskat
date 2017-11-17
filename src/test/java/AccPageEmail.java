import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccPageEmail {

    private WebDriver driver;

    @FindBy (xpath = "//div[@data-name = 'send']")
    private WebElement sendButton;
    @FindBy (xpath = "//div[@data-name='saveDraft']")
    private WebElement saveButton;
    @FindBy (xpath = "//textarea[@data-original-name='To']")
    private WebElement toField;
    @FindBy (name = "To")
    private WebElement toFieldAfter;
    @FindBy (xpath = "//input[@name='Subject']")
    private WebElement subjectField;
    @FindBy (xpath = "//iframe[contains(@id,'composeEditor_ifr')]")
    private WebElement frameArea;
    @FindBy (id = "tinymce")
    private WebElement emailField;
    @FindBy (xpath= "//div[@data-mnemo = 'saveStatus']/a[@href='/messages/drafts']")
    private WebElement savedLabel;
    @FindBy (xpath= "//div[@class= 'message-sent__title']")
    private WebElement sentLabel;
    @FindBy (xpath = "//div[@class='b-letter__head b-letter__head_threads']")
    private WebElement emailSentAllFields;
    @FindBy (xpath = "//div[@class='js-helper js-readmsg-msg']")
    private WebElement emailSentText;

    public AccPageEmail(WebDriver driver){
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Новое письмо"));
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

    public String getSentEmailText() {
        return emailSentText.getText();
    }

    public String[] getSentAllField(){
        String[] allFields = emailSentAllFields.getText().split("\n");
        return allFields;
    }

    public void clickSaveDraftButton(){
        saveButton.click();
        //save time for draft identification
        DataForTst.setDateLastDraft(new SimpleDateFormat("H:mm").format(new Date()));
        //check successful draft
        savedLabel.getText();
    }

    public void clickSendButton(){
        sendButton.click();
        //save time for sent identification
        DataForTst.setDateLastSent(new SimpleDateFormat("H:mm").format(new Date()));
        //check successful draft
        sentLabel.getText();
    }

    public boolean checkNewEmailPage(){
        return (sendButton.isDisplayed() && saveButton.isDisplayed() && toField.isDisplayed());
    }

    public String getToFieldAfter() {
        return toFieldAfter.getAttribute("value");
    }

    public String getSubjectField() {

        return driver.findElement(By.xpath("//a[@href='"+driver.getCurrentUrl()+"']")).getAttribute("data-subject");
    }
}
