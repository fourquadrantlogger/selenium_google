package google.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by timeloveboy on 16-7-6.
 */
public class ResultCount {
    public static int Num=100;
    public static int get(WebDriver driver){
        WebElement div = driver.findElement(By.id("resultStats"));
        String allresultStats=div.getText();

        Pattern pattern = Pattern.compile(" [0-9,]+ ");
        Matcher matcher = pattern.matcher(allresultStats);
        List<String> strs=new ArrayList();
        while (matcher.find()){
            strs.add(matcher.group());
        }

        String resultStat="";
        if(strs.size()>0){
            resultStat=strs.get(0);
        }
        resultStat=resultStat.replace(",","");
        resultStat=resultStat.trim();
        int total=Integer.parseInt(resultStat);
        return total;
    }
}
