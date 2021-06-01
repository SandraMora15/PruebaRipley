package mobile.android.helpers;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public abstract class MobilePage {
	// atributos
	protected AndroidDriver<AndroidElement> driver;
	protected ExtentTest test;
	protected Boolean TAKE_SS;
	protected WebDriverWait wait;

	public MobilePage(AndroidDriver<AndroidElement> driver, ExtentTest test, Boolean TAKE_SS, int seconds) {
		this.driver = driver;
		this.test = test;
		this.TAKE_SS = TAKE_SS;
		this.wait = new WebDriverWait(driver, seconds);
	}

}
