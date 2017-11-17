import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccPageMain {

    private WebDriver driver;

    @FindBy(partialLinkText = "Написать письмо")
    private WebElement newEmailButton;
    @FindBy(partialLinkText = "Отправленные")
    private WebElement sentButton;
    @FindBy(partialLinkText = "Черновики")
    private WebElement draftsButton;
    @FindBy(xpath = "//a[@href='/messages/inbox/']")
    private WebElement inboxLable;
    @FindBy(id = "PH_authMenu_button")
    private WebElement emailLabel;
    @FindBy(id = "PH_logoutLink")
    private WebElement logOutButton;

    public AccPageMain(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public AccPageEmail clickNewEmail(){
        newEmailButton.click();
        return new AccPageEmail(driver);
    }

    public AccPageDrafts clickDrafts() {
        draftsButton.click();
        return new AccPageDrafts(driver);
    }

    public AccPageSent clickSent() {
        sentButton.click();
        return new AccPageSent(driver);
    }

    public void clickLogOut() {
        logOutButton.click();
    }

    public boolean checkAccPageMain(){
        return (inboxLable.isDisplayed() && logOutButton.isDisplayed());
    }
}
