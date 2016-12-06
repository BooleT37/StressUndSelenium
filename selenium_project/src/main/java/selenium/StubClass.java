package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class StubClass {
	public static void main(String[] args) throws InterruptedException {
		// Создаем новый webdriver с указанием на банарник firefox
		WebDriver wd = new FirefoxDriver(new FirefoxBinary(new File(
				"C:\\Program Files\\Mozilla Firefox 46\\firefox.exe")), new FirefoxProfile());
		// Открываем гугл
		wd.get("http://localhost:8080");
		final String messageString = "Test message";
		final String authorString = "Test_author";
		// Находим поисковую строку и ищем "selenium"

		WebElement messageBox = wd.findElement(By.id("formText"));
		WebElement authorBox = wd.findElement(By.id("formAuthor"));
		WebElement submitButton = wd.findElement(By.id("addMessageBtn"));

		messageBox.sendKeys(messageString);
		authorBox.sendKeys(authorString);
		submitButton.submit();
		// т.к. google асинхронный - ждем поисковой выдачи
		(new WebDriverWait(wd, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				List<WebElement> rows = d.findElements(By.xpath("html/body/table/tbody/tr"));
				WebElement lastRow = rows.get(rows.size() - 1);
				List<WebElement> columns = lastRow.findElements(By.tagName("td"));
				return columns.get(1).getText().equals(authorString) && columns.get(2).getText().equals(messageString);
			}
		});
		// закрываем webdriver
		wd.quit();
	}
}
