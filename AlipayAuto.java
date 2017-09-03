package com.onsale.nju;

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
		String oppositeUser = getOppositeUser("20150826110500100010740029003925");
		System.out.println("交易方对方信息：" + oppositeUser);
		oppositeUser = getOppositeUser("20150720110500100010740025980311");
		System.out.println("交易方对方信息：" + oppositeUser);
		oppositeUser = getOppositeUser("2015081521001004740064396260");
		System.out.println("交易方对方信息：" + oppositeUser);

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
		// 启动火狐浏览器
		 System.setProperty("webdriver.firefox.bin", "D:\\firfox\\firefox.exe");
		 WebDriver driver = new FirefoxDriver();
		// 启动Chrome浏览器
//		System.setProperty("webdriver.chrome.driver", "E:\\javafiles\\NjuOnSale\\chromedriver.exe");
//		driver = new ChromeDriver();
		// 获取登录页面
		driver.get("https://auth.alipay.com/login/index.htm");
		// 获取用户名输入框
		
		String username = "15056919620";
		driver.findElement(By.id("J-input-user")).clear();
		for(int i = 0, len = username.length(); i < len; i++){
			driver.findElement(By.id("J-input-user")).sendKeys(username.charAt(i) + "");
			sleep(500);			
		}		
		//driver.findElement(By.id("J-input-user")).sendKeys("15056919620");
		// 休息500ms，否则，速度太快，会将密码内容填充到用户名输入框中
		sleep(500);
		// 获取密码输入框
		driver.findElement(By.id("password_rsainput")).clear();
		sleep(1000);
		String password = "123qwertyuiopp??";
		for(int i = 0, len = password.length(); i < len; i++){
			driver.findElement(By.id("password_rsainput")).sendKeys(password.charAt(i) + "");
			sleep(500);			
		}

		// 休息8秒等待用户输入验证码
		sleep(2000);
		// 当前URL 0
		// ：https://authsu18.alipay.com/login/certCheck.htm?goto=https%3A%2F%2Fwww.alipay.com%2F
		System.out.println("当前URL 0 ：" + driver.getCurrentUrl());
		//driver.get("https://www.alipay.com/");
		// 点击个人用户登录
		//driver.findElement(By.id("J-login-btn")).click();
		WebElement login_button = driver.findElement(By.id("J-login-btn"));
		sleep(2000); 
		login_button.click();
		sleep(20000);//用于输入验证码
		//driver.findElement(By.className("personal-login")).click();
		// 当前URL 1 ：https://www.alipay.com/
		System.out.println("当前URL 1 ：" + driver.getCurrentUrl());
		sleep(2000);
		//WebElement myAlipay = driver.findElement(By.className("am-button-innerNav,button-myalipay"));
		//System.out.println("myAlipay isSelected ：" + myAlipay.isSelected());// false
		//System.out.println("myAlipay isEnabled ：" + myAlipay.isEnabled());// true
		//System.out.println("myAlipay isDisplayed ：" + myAlipay.isDisplayed());// true
		// 点击进入我的支付宝按钮
		//driver.findElement(By.className("am-button-innerNav,button-myalipay")).click();
		// 当前URL 2 ：https://my.alipay.com/portal/i.htm
		System.out.println("当前URL 2 ：" + driver.getCurrentUrl());
		// boolean selected1 =
		// driver.findElement(By.className("fn-ml10")).isSelected();
		// System.out.println("收支明细是否选中：" + selected1);
		// org.openqa.selenium.NoSuchElementException: no such keyword: Element
		// was not in a form, so could not submit.
		// driver.findElement(By.className("fn-ml10")).submit();//跳转收支明细
		// Exception in thread "main" org.openqa.selenium.WebDriverException:
		// unknown error: Element is not clickable at point
		// driver.findElement(By.xpath("//*[@id=\"J-trend-consume\"]/div/div[1]/div/a[1]")).click();
		// driver.findElement(By.className("fn-ml10")).click();
		// 无反应，看样子一直不会让你点的了
		// WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
		// webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"J-trend-consume\"]/div/div[1]/div/a[1]")));
		// 跳转到收支明细界面
		// driver.get("https://xlab.alipay.com/consume/record/items.htm");
		// 跳转到交易记录界面
		driver.get("https://consumeprod.alipay.com/record/index.htm");
		String currentUrl = driver.getCurrentUrl();
		// 当前URL 3 ：https://consumeprod.alipay.com/record/advanced.htm
		System.out.println("当前URL 3 ：" + currentUrl);
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
