package idv.code.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Objects;

public class FirstSelenium {
    public static void main(String[] args) throws InterruptedException {
        File file = new File(Objects.requireNonNull(FirstSelenium.class.getClassLoader().getResource("chromedriver.exe")).getFile());
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://www.baidu.com");
        WebElement searchInput = driver.findElement(By.name("wd"));
        searchInput.sendKeys("极客时间");
        searchInput.submit();

        Thread.sleep(3000);
        System.out.println(driver.getTitle());
        Assert.assertEquals("极客时间_百度搜索", driver.getTitle());
        driver.quit();
    }
}
