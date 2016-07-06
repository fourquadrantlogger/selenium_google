## 一、下载文件

先要去官网（http://seleniumhq.org/download/）下载必需的文件：

Selenium IDE （专门用于 FireFox 测试的独立界面，可以录制测试步骤，但我更倾向于写代码做标准的功能测试）
Selenium Server （可以输入指令控制、可以解决跨域的 js 问题，等到后面学到了再讲吧）
The Internet Explorer Driver Server （专门用于IE测试的）
Selenium Client Drivers （可以找到你熟悉的语言，例如我选择的 Java）
Third Party Browser Drivers NOT SUPPORTED/DEVELOPED by seleniumhq（第三方开发的 Selenium 插件，第一个就是 Chrome 的，否则你就没办法测试 Chrome 了）
其他的，就根据你自己的需要寻找吧，目前这些足够我用了。

## 二、安装 & 运行

貌似摆弄新东西时，只有 “Hello World” 蹦出来以后，我们这些初学者才会感到情绪稳定，那就赶紧开始吧。

对于初期打算直接用编程方式制作测试用例的情况来说，Selenium IDE、Selenium Server 都可以不用安装执行。
英语好的朋友可以直接看官网的文档（http://seleniumhq.org/documentation/）就能够开始使用了。
看中文的，就继续听我唠叨：

【1. 建立 Maven 工程】
Selenium 支持 maven 工程，这会让你的工作更加简便。
用 Eclipse 建个 Maven 的工程，建成后，直接修改 pom.xml，（参考：http://seleniumhq.org/docs/03_webdriver.html#setting-up-a-selenium-webdriver-project）

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>Selenium2Test</groupId>
	<artifactId>Selenium2Test</artifactId>
	<version>1.0</version>
	<dependencies>
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>2.25.0</version>
		</dependency>
		<dependency>
			<groupId>com.opera</groupId>
			<artifactId>operadriver</artifactId>
		</dependency>
	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>com.opera</groupId>
				<artifactId>operadriver</artifactId>
				<version>0.16</version>
				<exclusions>
					<exclusion>
						<groupId>org.seleniumhq.selenium</groupId>
						<artifactId>selenium-remote-driver</artifactId>
					</exclusion>
				</exclusions>
			</dependency>
		</dependencies>
	</dependencyManagement>
</project>

pom.xml 修改保存后，eclipse 会自动把需要的 jar 包下载完成。


【2. 测试 FireFox】
Selenium 最初就是在 FireFox 上做起来的插件，所以我们先来搭建 FireFox 的环境。
确保你正确安装了 FireFox 后，就可以直接编写 java 代码测试喽。

在 lesson1 目录下建立 ExampleForFireFox.java
（因为国内不少朋友访问 google 的时候会出问题，所以我就把代码中的 google 变成 baidu 了）

package lesson1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExampleForFireFox  {
    public static void main(String[] args) {
    	// 如果你的 FireFox 没有安装在默认目录，那么必须在程序中设置
//    	System.setProperty("webdriver.firefox.bin", "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
    	// 创建一个 FireFox 的浏览器实例
        WebDriver driver = new FirefoxDriver();

        // 让浏览器访问 Baidu
        driver.get("http://www.baidu.com");
        // 用下面代码也可以实现
        // driver.navigate().to("http://www.baidu.com");

        // 获取 网页的 title
        System.out.println("1 Page title is: " + driver.getTitle());

        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.id("kw"));

        // 输入关键字
        element.sendKeys("zTree");

        // 提交 input 所在的  form
        element.submit();

        // 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().endsWith("ztree");
            }
        });

        // 显示搜索结果页面的 title
        System.out.println("2 Page title is: " + driver.getTitle());

        //关闭浏览器
        driver.quit();
    }
}

普通情况下，直接运行代码就可以看到会自动弹出 FireFox 窗口，访问 baidu.com，然后输入关键字并查询，一切都是自动完成的。

错误提醒：
1）Exception in thread "main" org.openqa.selenium.WebDriverException: Cannot find firefox binary in PATH. Make sure firefox is installed.
出现这个错误，是说明你的 FireFox 文件并没有安装在默认目录下，这时候需要在最开始执行：System.setProperty 设置环境变量  "webdriver.firefox.bin" 将自己机器上 FireFox 的正确路径设置完毕后即可。

2）Exception in thread "main" org.openqa.selenium.UnsupportedCommandException: Bad request
出现这个错误，很有意思。 查了一下 有人说应该是 hosts 出现了问题，加上一个 127.0.0.1  localhost 就行了，但我的 hosts 上肯定有这个玩意，为啥也会出现这个问题呢？

经过调试，发现 127.0.0.1 localhost 的设置必须要在 hosts 文件的最开始，而且如果后面有其他设置后，也不要再出现同样的 127.0.0.1 localhost ，只要有就会出错。（因为我为了方便访问 google 的网站，专门加入了 smarthosts 的内容，导致了 localhost 的重复）

【3. 测试 Chrome】
Chrome 虽然不是 Selenium 的原配，但是没办法，她太火辣了，绝对不能抛下她不管的。
把 ExampleForFireFox.java 稍微修改就可以制作出一个 ExampleForChrome.java ，直接把 new FireFoxDriver() 修改为 new ChromeDriver() 你会发现还是行不通。

错误如下：
1）Exception in thread "main" java.lang.IllegalStateException: The path to the driver executable must be set by the webdriver.chrome.driver system property; for more information, see http://code.google.com/p/selenium/wiki/ChromeDriver. The latest version can be downloaded from http://code.google.com/p/chromedriver/downloads/list
这应该是找不到 chrome 的文件，好吧，利用 System.setProperty 方法添加路径，这里要注意，是 “webdriver.chrome.driver” 可不是“webdriver.chrome.bin”

设置路径后还是会报错：
2）[6416:4580:1204/173852:ERROR:gpu_info_collector_win.cc(91)] Can't retrieve a valid WinSAT assessment.
这个貌似是因为 Selenium 无法直接启动 Chrome 导致的，必须要通过前面咱们下载 Chrome 的第三方插件 ChromeDriver，去看第一个错误中提示给你的 网址：http://code.google.com/p/selenium/wiki/ChromeDriver
按照人家给的例子来修改我们的测试代码吧：

package lesson1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExampleForChrome {
	public static void main(String[] args) throws IOException {
		// 设置 chrome 的路径
		System.setProperty(
				"webdriver.chrome.driver",
				"C:\\Documents and Settings\\sq\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe");
		// 创建一个 ChromeDriver 的接口，用于连接 Chrome
		@SuppressWarnings("deprecation")
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingChromeDriverExecutable(
						new File(
								"E:\\Selenium WebDriver\\chromedriver_win_23.0.1240.0\\chromedriver.exe"))
				.usingAnyFreePort().build();
		service.start();
		// 创建一个 Chrome 的浏览器实例
		WebDriver driver = new RemoteWebDriver(service.getUrl(),
				DesiredCapabilities.chrome());

		// 让浏览器访问 Baidu
		driver.get("http://www.baidu.com");
		// 用下面代码也可以实现
		// driver.navigate().to("http://www.baidu.com");

		// 获取 网页的 title
		System.out.println("1 Page title is: " + driver.getTitle());

		// 通过 id 找到 input 的 DOM
		WebElement element = driver.findElement(By.id("kw"));

		// 输入关键字
		element.sendKeys("zTree");

		// 提交 input 所在的 form
		element.submit();

		// 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().endsWith("ztree");
			}
		});

		// 显示搜索结果页面的 title
		System.out.println("2 Page title is: " + driver.getTitle());

		// 关闭浏览器
		driver.quit();
		// 关闭 ChromeDriver 接口
		service.stop();

	}
}

运行一下看看，是不是一切OK了？

补充：仔细看了一下官网的介绍：Chrome Driver is maintained / supported by the Chromium project iteslf.  看来如果使用 new ChromeDriver() 的话，应该要安装 Chromium 而不是 Chrome，我现在懒得折腾了，有兴趣的童鞋可以试验一下。