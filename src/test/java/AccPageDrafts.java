import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AccPageDrafts {
    private WebDriver driver;
    private String dateLastDraft;

    @FindBy (xpath = "//div[@class = 'b-datalist__item__date']/span")
    private List<WebElement> listDates;

    public void setDateLastDraft(String dateLastDraft) {
        this.dateLastDraft = dateLastDraft;
    }

    public AccPageDrafts(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.titleContains("Черновики"));
        PageFactory.initElements(this.driver, this);
    }


    public void clickLastDraftEmail(String toEmail, String subject) {
        for (WebElement item: listDates) {
            if (item.getText().equals(dateLastDraft)) item.click();
        }


    }
}
