package mobile.android.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.android.pages.LoginPage;

public class LoginTest {
	private AndroidDriver<AndroidElement> driver;
	private ExtentReports extent;
	private ExtentTest test;
	private String SUBDIR;
	private static int WAIT = 5;
	private static Boolean TAKE_SS = true;

	@Parameters({ "suite" })
	@BeforeSuite
	public void configExtentReports(String suite) {
		// ExtentReports config
		this.SUBDIR = suite + "\\";
		this.extent = new ExtentReports("ExtentReports/" + suite + ".html", true);
		this.extent.addSystemInfo("Host Name", "Desafio Ripley");
		this.extent.addSystemInfo("Enviroment", "Automation Mobile");

		File conf = new File("src/test/resources/extentReports/extent-config.xml");
		this.extent.loadConfig(conf);

	}

	@Parameters({ "device", "application", "url_server" })
	@BeforeMethod
	public void configSelenium(String device, String application, String url_server) throws MalformedURLException {

		// Configuracion del dispositivo.
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, device);

		// Configuracion de la app.
		// para una apk o una app ya instalada.
		if (!application.equals("none")) {
			File app = new File("src/test/resources/apks/" + application);
			cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		} else {
			// Esta capability permite no resetear la configuracion que ya tengamos de la
			// app.
			cap.setCapability("noReset", "true");
		}

		// Creamos el driver del servidor appium.
		driver = new AndroidDriver<>(new URL(url_server), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Parameters({ "user", "password" })
	@Test
	public void loginOK(String user, String clave) {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();

		// Configuracion del reporte
		test = extent.startTest("Prueba Android", "Login");
		test.log(LogStatus.INFO, "Automatizacion Mobile.-");

		// Seleccionar Iniciar sesion
		LoginPage login = new LoginPage(driver, test, TAKE_SS, WAIT);
		login.LoginCorrecto(user, clave, subDir);
		login.assertLoginCorrecto();
		login.cerrarSession(subDir);
		login.assertCierreCorrecto();
	}

	@Parameters({ "user", "claveInval" })
	@Test
	public void loginNOK(String user, String claveInval) {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();

		// Configuracion del reporte
		test = extent.startTest("Prueba Android", "Login");
		test.log(LogStatus.INFO, "Automatizacion Mobile.-");

		// Seleccionar Iniciar sesion
		LoginPage loginNo = new LoginPage(driver, test, TAKE_SS, WAIT);
		loginNo.LoginCorrecto(user, claveInval, subDir);
		loginNo.assertLoginIncorrecto();
	}

	@Parameters({ "user", "password", "name", "newUser", "mail", "numberC" })
	@Test
	public void transferir(String user, String clave, String name, String newUser, String mail, String numberC)
			throws InterruptedException {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();

		// Configuracion del reporte
		test = extent.startTest("Prueba Android", "Login");
		test.log(LogStatus.INFO, "Automatizacion Mobile.-");

		// Transferencia nuevo ingreso
		LoginPage login = new LoginPage(driver, test, TAKE_SS, WAIT);
		login.LoginCorrecto(user, clave, subDir);
		login.tranfNueva(name, newUser, mail, numberC, subDir);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test failed.- <br>" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test skipped.- <br>" + result.getThrowable());
		} else {
			test.log(LogStatus.PASS, "Test passed.-");
		}
		// driver.closeApp();
		extent.endTest(test);
	}

	@AfterSuite
	public void closeExtentReports() {
		extent.flush();
	}
}