import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccPageMain {
    private WebDriver driver;

    @FindBy(partialLinkText = "Написать письмо")
    private WebElement newEmailButton;
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
        //WebDriverWait wait = new WebDriverWait(this.driver, 10);
        //wait.until(ExpectedConditions.visibilityOf(this.));
        PageFactory.initElements(this.driver, this);
    }

    public AccPageNewEmail clickNewEmail(){
        newEmailButton.click();
        return new AccPageNewEmail(driver);
    }

    public AccPageDrafts clickDrafts() {
        draftsButton.click();
        return new AccPageDrafts(driver);
    }

    public void clickLogOut() {
        logOutButton.click();
    }
}
