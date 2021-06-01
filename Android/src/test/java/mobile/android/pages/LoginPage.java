package mobile.android.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile.android.helpers.Helper;
import mobile.android.helpers.MobilePage;

public class LoginPage extends MobilePage {

	// variables inicio sesion
	private By usuario;
	private By password;
	private By btnIngresar;
	private By validadorLoginCorrecto;
	private By validadorLoginInvalido;

	// variables cierre sesion
	private By menuDespl;
	private By cerrar;
	private By validadorCierreCorrecto;

	// Atributos tranferencia
	private By transferir;
	private By nuevoIng;
	private By validateNuevoIng;
	private By nombre;
	private By user;
	private By email;
	private By banco;
	private By listaBanco;
	private By tipoCuenta;
	private By cuenta;
	private By nroCuenta;
	private By btnContinuar;

	public LoginPage(AndroidDriver<AndroidElement> driver, ExtentTest test, Boolean TAKE_SS, int seconds) {
		super(driver, test, TAKE_SS, seconds);

		// Constructor - Variables login
		btnIngresar = By.xpath("//android.widget.Button[@text='Ingresar']");
		usuario = By.xpath("//android.widget.EditText[@password='false']");
		password = By.xpath("//android.widget.EditText[@password='true']");
		// Constructor para validación de ingreso Correcto
		validadorLoginCorrecto = By.xpath("//android.view.View[@text='home']");
		validadorLoginInvalido = By.xpath("//android.view.View[@text='Usuario o clave incorrecta.']");

		// Constructor - variables cierre de sesion
		menuDespl = By.xpath("//android.widget.Button[@text='menu']");
		cerrar = By.xpath("//android.widget.Button[@text='Cerrar sesión']");
		// Constructor para validación de cierre de sesion Correcto
		validadorCierreCorrecto = By.xpath("//android.view.View[@text='¡Hola Marcelo!']");

		// Constructor - variables transferencia
		this.transferir = By.xpath("//android.widget.Button[@text='Transferir']");
		this.nuevoIng = By.xpath("//android.widget.Button[@text='Nuevo']");
		this.validateNuevoIng = By.xpath("//android.view.View[@text='Ingresa los datos del destinatario']");
		this.nombre = By.xpath("//android.widget.EditText[@index='1']");
		this.user = By.xpath("//android.widget.EditText[@index='3']");
		this.email = By.xpath("//android.widget.EditText[@index='5']");
		this.listaBanco = By.xpath("//android.widget.Spinner[@text='Selecciona el banco']");
		this.banco = By.xpath("//android.widget.CheckedTextView[@text='Banco Ripley']");
		this.tipoCuenta = By.xpath("//android.widget.Spinner[@index='9'");
		this.cuenta = By.xpath("//android.widget.CheckedTextView[@text='Cuenta Vista']");
		this.nroCuenta = By.xpath("//android.widget.EditText[@index='11']");
		this.btnContinuar = By.xpath("//android.widget.Button[@text='Continuar']");
	}

	public void LoginCorrecto(String user, String clave, String subDir) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(btnIngresar));

		// Ingresar usuario
		driver.findElement(usuario).sendKeys(user);
		Helper.waitSeconds(3);
		Helper.addEvidence(TAKE_SS, driver, test, "Ingresar usuario", subDir, "imagen_01");

		// Ingresar contraseña
		driver.findElement(password).sendKeys(clave);
		Helper.waitSeconds(3);
		Helper.addEvidence(TAKE_SS, driver, test, "Ingresar Password", subDir, "imagen_02");

		// Presionar boton Ingresar
		driver.findElement(btnIngresar).click();
		Helper.waitSeconds(10);
		Helper.addEvidence(TAKE_SS, driver, test, "Clic en Ingresar", subDir, "imagen_03");
	}

	public void cerrarSession(String subDir) {
		// Mostrar menu desplegable
		driver.findElement(menuDespl).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Seleccionar menu", subDir, "imagen_04");
		// Clic boton cerrar sesion
		driver.findElement(cerrar).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Cerrar Sesion", subDir, "imagen_05");
	}

	public void scrollMobile() {
		// to perform Scroll on application using Selenium
		// scrollToEnd (moves exactly by one view. 10 scrolls max)
		try {
			driver.findElement(
					MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(10)"));
		} catch (InvalidSelectorException e) {
			// ignore
		}

		// flingToEnd (performs quick swipes. 10 swipes max)
		try {
			driver.findElement(
					MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).flingToEnd(10)"));
		} catch (InvalidSelectorException e) {
			// ignore
		}
	}

	public void tranfNueva(String name, String newUser, String mail, String numberC, String subDir)
			throws InterruptedException {
		// Mostrar menu desplegable

		driver.findElement(menuDespl).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Seleccionar menu", subDir, "imagen_06");
		// Clic en boton transferir
		wait.until(ExpectedConditions.visibilityOfElementLocated(transferir));
		driver.findElement(transferir).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Transferir", subDir, "imagen_07");
		wait.until(ExpectedConditions.visibilityOfElementLocated(nuevoIng));

		// nueva transferencia
		driver.findElement(nuevoIng).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Nuevo Ingreso", subDir, "imagen_08");
		wait(10);

		// Ingresar usuario
		driver.findElement(nombre).sendKeys(name);
		driver.findElement(user).sendKeys(newUser);
		driver.findElement(email).sendKeys(mail);
		driver.findElement(listaBanco).click();
		driver.findElement(banco).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Ingresar usuario", subDir, "imagen_01");

		scrollMobile();
		wait.until(ExpectedConditions.visibilityOfElementLocated(btnContinuar));

		// continua llenado de campos
		driver.findElement(tipoCuenta).click();
		driver.findElement(cuenta).click();
		driver.findElement(nroCuenta).sendKeys(numberC);
		Helper.addEvidence(TAKE_SS, driver, test, "Ingresar usuario", subDir, "imagen_01");

	}

	// Validacion de Login Correcto
	public void assertLoginCorrecto() {
		Assert.assertTrue(driver.findElement(validadorLoginCorrecto).getText().equalsIgnoreCase("home"));
	}

	// Validacion de Cierre sesion Correcto
	public void assertCierreCorrecto() {
		Assert.assertTrue(driver.findElement(validadorCierreCorrecto).getText().equalsIgnoreCase("¡Hola Marcelo!"));
	}

	// Validacion de Login inCorrecto
	public void assertLoginIncorrecto() {
		Assert.assertTrue(
				driver.findElement(validadorLoginInvalido).getText().equalsIgnoreCase("Usuario o clave incorrecta."));
	}

	// Validacion ingreso nuevo destinatario
	public void assertNewDestinatario() {
		Assert.assertTrue(
				driver.findElement(validateNuevoIng).getText().equalsIgnoreCase("Ingresa los datos del destinatario"));
	}

}
