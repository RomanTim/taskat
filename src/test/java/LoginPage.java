import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy (id = "mailbox:login")
    private WebElement emailField;
    @FindBy (id = "mailbox:password")
    private WebElement passField;
    @FindBy (id = "mailbox:submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public AccPageMain loginIn(){
        emailField.sendKeys(DataForTst.getEmail());
        passField.sendKeys(DataForTst.getPass());
        submitButton.click();

        return new AccPageMain(driver);
    }

    public boolean checkLoginPage() {

        return (emailField.isDisplayed() && passField.isDisplayed());
    }
}
