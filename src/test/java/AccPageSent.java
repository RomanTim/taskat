import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class AccPageSent {
    private WebDriver driver;

    @FindBy (xpath = "//div[@class = 'b-datalist__item__date']/span")
    private List<WebElement> listDates;

    public AccPageSent(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Отправленные"));
        PageFactory.initElements(this.driver, this);
    }

    public boolean clickLastSentEmail(){
        boolean foundFlag = false;
        for (WebElement item: listDates) {
            if (item.getText().equals(DataForTst.getDateLastSent())){
                item.click();
                foundFlag = true;
                break;
            }
        }
        return foundFlag;
    }

    public boolean checkAccPageSent(){
        return (driver.getTitle().contains("Отправленные"));
    }
}
