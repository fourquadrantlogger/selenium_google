package www.bilibili.com;

import org.jsoup.Jsoup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.Log;

/**
 * Created by timeloveboy on 16-12-18.
 */
public class Main {
    static WebDriver driver;

    public static void main(String[] args) throws Exception {
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        String os = System.getProperties().getProperty("os.name");
        switch (os) {
            case "Mac OS X":
                System.setProperty("webdriver.chrome.driver", "chromedriver.app");
                break;
            case "Window":
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                break;
            case "Linux":
                System.setProperty("webdriver.chrome.driver", "chromedriver");
                break;
        }

        driver = new ChromeDriver();

        int ID = 1;
        for (; ID < 1000000000; ID++) {
            String url = "http://space.bilibili.com/" + ID + "/#!/index";

            driver.get(url);
            Thread.sleep(5000);
            Log.v(driver.manage().getCookies());
            Log.v(Jsoup.parse(driver.getPageSource()).select(".friend-data"));
        }


        driver.close();
    }
}
