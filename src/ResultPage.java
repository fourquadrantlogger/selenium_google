import com.mongodb.BasicDBObject;
import org.bson.*;
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
                filetype=filetype.replace('[',' ').replace(']',' ');
                String title = divs.get(i).findElement(By.tagName("a")).getText();
                String href = divs.get(i).findElement(By.tagName("a")).getAttribute("href");
                String data_href = divs.get(i).findElement(By.tagName("a")).getAttribute("data-href");

                BsonDocument o = new BsonDocument();
                o.put("filetype",new BsonString(filetype));
                o.put("title", new BsonString(title));

                if (href == null) {
                    if (data_href == null) {
                        break;
                    } else {
                        result.put(data_href, o);
                    }
                } else {
                    result.put("_id",href);

                }
                BasicDBObject dbObject=new BasicDBObject();
                dbObject.put("_id",href);
                dbObject.put("filetype",new BsonString(filetype));
                dbObject.put("title", new BsonString(title));
                dbObject.put("SearchTags", new BsonString(Main.SearchTags));
                mgoDB.insertData( dbObject);
                System.out.println(href+"\t");
                System.out.print(o);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }
}
