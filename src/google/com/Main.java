package google.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Main {
    public static String Filetype="doc";
    public static String SearchTags="大学 试卷";
    static WebDriver driver;
    public static void main(String[] args) throws Exception{
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        String os = System.getProperties().getProperty("os.name");
        switch (os){
            case "Mac OS X":System.setProperty("webdriver.chrome.driver", "chromedriver.app");
            case "Window":System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }
        System.setProperty("webdriver.chrome.driver", "chromedriver.app");
        driver= new ChromeDriver();

        int start=0;
        String url="https://www.google.com.hk/search?safe=strict&q=filetype:"+ Filetype +"+"+ SearchTags +"&cad=h&start="+start;

        driver.get(url);

        int count=ResultCount.get(driver);
        if(count>1000)count=1000;//谷歌似乎不会返回超过1000条结果

        for(;start<count;start+=ResultCount.Num){
            url="https://www.google.com.hk/search?safe=strict&q=filetype:"+ Filetype +"+"+ SearchTags +"&cad=h&start="+start+"&num="+ResultCount.Num;
            ResultPage.get(driver,url);
            Thread.sleep(1000);
        }

        driver.close();
    }
}
