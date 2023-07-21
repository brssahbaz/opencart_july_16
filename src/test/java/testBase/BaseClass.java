package testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import org.apache.logging.log4j.LogManager; //for logger
import org.apache.logging.log4j.Logger; //for logger

public class BaseClass {
	
	public Logger logger;// for Logging
	
	public static WebDriver driver;
	public ResourceBundle rb;
	
	
	@BeforeClass(groups= {"Sanity","Master","Regression"})
	@Parameters("browser")   // getting browser parameter from testng.xml
	public void setup(String br)
	{
		
		rb=ResourceBundle.getBundle("config"); //Load config.properties file
		logger = LogManager.getLogger(this.getClass());// for Logger  
		
		
		
		
		
		//launch right browser based on parameter
		if (br.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
		} else if (br.equals("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new EdgeDriver(options);
		} else  if (br.equals("firefox")){
			FirefoxOptions options = new FirefoxOptions();
			
			options.addArguments("--remote-allow-origins=*");
			driver = new FirefoxDriver(options);
		}
	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(rb.getString("appURL1"));
		//driver.get("http://localhost/opencart/upload/");
		driver.manage().window().maximize();
	}

	@AfterClass(groups= {"Sanity","Master","Regression"})
	public void teadDown() {
		driver.quit();
	}

	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

	public String randomeNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		return (generatedString2);
	}
	
	public String randomeAlphaNumeric() {
		String st = RandomStringUtils.randomAlphabetic(4);
		String num = RandomStringUtils.randomNumeric(3);
		return (st+"@"+num);
	}
	
	
	public String captureScreen(String tname) throws IOException {
		
	//	SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
	//	Date dt=new Date();
	//	String timestampString=df.format(dt);
		
		

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
