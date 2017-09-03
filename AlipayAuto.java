

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AlipayAuto {
	private static WebDriver driver;

	public static void main(String[] args) {
		login();
		//登录后可以爬去账单信息等
	}

	// 定义自己的休眠方法，精简代码量
	private static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	// 登录操作，负责将界面跳转到交易记录界面
	private static void login() {
		
		// -------------启动火狐浏览器--------------------
		 System.setProperty("webdriver.firefox.bin", "D:\\firfox\\firefox.exe");
		 WebDriver driver = new FirefoxDriver();
		 
		// -------------启动Chrome浏览器------------------
		//System.setProperty("webdriver.chrome.driver", "E:\\javafiles\\NjuOnSale\\chromedriver.exe");
		//driver = new ChromeDriver();
		
		// 获取登录页面
		driver.get("https://auth.alipay.com/login/index.htm");
		
		// 获取用户名输入框
		String username = "15****9620";
		driver.findElement(By.id("J-input-user")).clear();
		for(int i = 0, len = username.length(); i < len; i++){//输入用户名，每隔500ms输入一个字符，过快输入会登录失败
			driver.findElement(By.id("J-input-user")).sendKeys(username.charAt(i) + "");
			sleep(500);			
		}		
		sleep(500);
		
		// 获取密码输入框
		driver.findElement(By.id("password_rsainput")).clear();
		sleep(1000);
		String password = "123****";
		for(int i = 0, len = password.length(); i < len; i++){//防止过快输入
			driver.findElement(By.id("password_rsainput")).sendKeys(password.charAt(i) + "");
			sleep(500);			
		}
		sleep(2000);

		//获取登录按钮
		WebElement login_button = driver.findElement(By.id("J-login-btn"));
		sleep(2000); //在获取登录按钮和点击登录按钮之间间隔2s
		login_button.click();
		
		//等待20s，用于输入手机验证码
		sleep(20000);//用于输入验证码
		
		// 跳转到交易记录界面
		driver.get("https://consumeprod.alipay.com/record/index.htm");
		sleep(1000);
	}

	// 获取交易对方信息
	private static String getOppositeUser(String transactionNo) {
		// 获取关键字对应的下拉框
		WebElement keywordInput = driver.findElement(By.id("J-keyword"));
		keywordInput.clear();
		keywordInput.sendKeys(transactionNo);
		WebElement keywordSelect = driver.findElement(By.id("keyword"));
		List<WebElement> options = keywordSelect.findElements(By.tagName("option"));
		// until方法表示直到可点再点
		// WebElement selectElement = wait.until(ExpectedConditions
		// .visibilityOfElementLocated(By.id("keyword")));
		// 需要执行JavaScript语句，所以强转driver
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// 也可以这么用setAttribute("style","");
		js.executeScript("document.getElementById('keyword').style.display='list-item';");
		js.executeScript("document.getElementById('keyword').removeAttribute('smartracker');");
		js.executeScript("document.getElementById('keyword').options[1].selected = true;");
		js.executeScript("document.getElementById('J-select-range').style.display='list-item';");
		// 设置交易时间选项
		Select selectTime = new Select(driver.findElement(By.id("J-select-range")));
		selectTime.selectByIndex(3);// 选中的是最近三个月
		System.out.println("selectTime.isMultiple() : " + selectTime.isMultiple());
		// 设置关键字选项
		Select selectKeyword = new Select(driver.findElement(By.id("keyword")));
		// selectKeyword.selectByValue("bizInNo");//此处的value填写<option>标签中的value值
		selectKeyword.selectByIndex(1);// 选中的是交易号
		System.out.println("selectKeyword.isMultiple() : " + selectKeyword.isMultiple());
		WebElement queryButton = driver.findElement(By.id("J-set-query-form"));// 拿到搜索按钮
		// 点击搜索按钮
		queryButton.submit();
		WebElement tr = driver.findElement(By.id("J-item-1"));// 先获取tr
		WebElement td = tr.findElement(By.xpath("//*[@id=\"J-item-1\"]/td[5]/p[1]"));
		return td.getText();
	}
}
