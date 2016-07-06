import com.sun.org.apache.regexp.internal.RE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timeloveboy on 16-7-6.
 */
public class ResultPage {
    public static Map<String,Object> get(WebDriver driver,String url){
        driver.get(url);
        Map<String, Object> result = new HashMap<>();
        List<WebElement> divs = driver.findElements(By.className("r"));
        for(int i=0;i<divs.size();i++) {
            try {
                String filetype = divs.get(i).findElement(By.tagName("span")).getText();
                String title = divs.get(i).findElement(By.tagName("a")).getText();
                String href = divs.get(i).findElement(By.tagName("a")).getAttribute("href");
                String data_href = divs.get(i).findElement(By.tagName("a")).getAttribute("data-href");

                Map<String, Object> o = new HashMap<String, Object>();
                o.put("filetype", filetype);
                o.put("title", title);

                if (href == null) {
                    if (data_href == null) {
                        break;
                    } else {
                        result.put(data_href, o);
                    }
                } else {
                    result.put(href, o);
                }
                System.out.print(href+"\t");
                System.out.print(o);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }
}
