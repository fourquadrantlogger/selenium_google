import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

public class Main {
    static WebDriver driver;
    public static void main(String[] args) throws Exception{
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver= new ChromeDriver();
        String Filetype="doc";
        String SearchTags="武汉大学 试卷";
        int start=0;
        String url="https://www.google.com.hk/search?safe=strict&q=filetype:"+ Filetype +"+"+ SearchTags +"&cad=h&start="+start;

        driver.get(url);

        int count=ResultCount.get(driver);
        if(count>1000)count=1000;//谷歌似乎不会返回超过1000条结果
        Map<String, Object> result = new HashMap<>();
        for(;start<count;start+=ResultCount.Num){
            url="https://www.google.com.hk/search?safe=strict&q=filetype:"+ Filetype +"+"+ SearchTags +"&cad=h&start="+start+"&num="+ResultCount.Num;
            Map<String, Object> s=ResultPage.get(driver,url);

            result.putAll(s);
            Thread.sleep(1000);
        }
    }
}
