import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        if (!driver.getTitle().contains("Mail.Ru")) {
            throw new IllegalStateException("This is not the Mail.ru page");
        }
            PageFactory.initElements(this.driver, this);
    }

    //log into mailru account
    public AccPageMain loginIn(String email, String pass){

        //enter email
        emailField.sendKeys(email);
        //enter pass
        passField.sendKeys(pass);
        //click submit
        submitButton.click();

        return new AccPageMain(driver);
    }

    public boolean checkLoginPage() {
        boolean result;
        if (emailField.isDisplayed() && passField.isDisplayed()) result = true;
        else result = false;
        return result;
    }
}
