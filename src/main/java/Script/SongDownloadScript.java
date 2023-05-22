package Script;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class SongDownloadScript {
    WebDriver driver;


    public SongDownloadScript() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\shawn\\Documents\\Java Projects\\SeleniumScript\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("load-extension=C:\\Users\\shawn\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cjpalhdlnbpafiamejdnhcphjbkeiagm\\1.49.2_0");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        this.driver = new ChromeDriver(options);
        driver.get("https://free-mp3-download.net/");
    }

    public void downloadSong(String songToDownload){
        WebElement searchBox = driver.findElement(By.id("q"));
        searchBox.sendKeys(songToDownload + Keys.ENTER);
        //Wait timer for elements to show up after selected.
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);


        WebElement downloadButton = wait.until(webDriver -> webDriver.findElement(By.cssSelector("#results_t > tr:nth-child(1) > td:nth-child(3) > a")));
        new Actions(driver).doubleClick(downloadButton).perform();


        WebElement flacRadioButton = wait.until(webDriver -> webDriver.findElement(By.id("flac")));
        new Actions(driver)
                .click(flacRadioButton)
                .perform();

        WebElement footer = driver.findElement(By.tagName("footer"));

        int deltaY = footer.getRect().y;
        new Actions(driver)
                .scrollByAmount(5000, deltaY)
                .perform();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@role='presentation']")));
        WebElement point = driver.findElement(By.className("recaptcha-checkbox-checkmark"));
        new Actions(driver)
                .click(point)
                .perform();
        driver.switchTo().defaultContent();
        WebElement downloadSongButton = driver.findElement(By.cssSelector("body > main > div > div > div > div > div.card-action > button"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        new Actions(driver).doubleClick(downloadSongButton).perform();
        System.out.println("Song Faked Downloaded");
    }
    public void tearDown(){
        driver.quit();
    }

}
