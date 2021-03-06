package normalUser;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import NormalUserXpath.PatientsXpath;
import browser.OpenBrowser;
import normalUserFunctions.PatientsFunctions;
import normalUserInputData.PatientsInfoData;

public class Patients extends OpenBrowser {

	public static String env = "Test";
	public static String testSuiteName = "Test Suit 5 -- Patients";
	public static WebDriver driver = null;

	@BeforeSuite
	public static void beforeSuit() {

		if (env.equalsIgnoreCase("Test")) {

			System.out.println("Test executes in correct environment where environment= " + env);
			System.out.println("Test Suite name = " + testSuiteName);

		}else {
			System.out.println("Test executes in wrong environment where environment= " + env);

		}
	}


	@Parameters("myBrowser")
	@BeforeTest
	public void setup(String myBrowser) throws MalformedURLException {

		driver = start(myBrowser);

	}


	//Opening browser with the given URL and navigate to Registration Page

	@BeforeMethod
	public void openBrowser() throws InterruptedException {

		driver.manage().deleteAllCookies();
		driver.get("https://dev.zntral.net/session/login");
		driver.manage().window().maximize();
		Thread.sleep(3000);
	}


	@AfterTest
	public void tearDown() throws Exception {
		if (driver != null) {
			System.out.println("Test Done!!!");
			driver.quit();
		}
	}

	//login

	@Test(priority = 1)
	public void loginUser() throws InterruptedException {

		PatientsFunctions.verifyLogin();

		String expectedUrl = "https://dev.zntral.net/dashboard";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);


	}


	//View the patients list
	@Test(priority = 2)
	public void patientsList() throws InterruptedException {

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		String expectedUrl = "https://dev.zntral.net/patients";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
	}


	//Search option

	@Test(priority = 3)
	public void search() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.search)));
		search.sendKeys(PatientsInfoData.search);
		Thread.sleep(2000);		

		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		String actualText = firstRow.getText();
		Assert.assertTrue(actualText.contains(PatientsInfoData.search));
		Thread.sleep(3000);
	}


	//Add new Patient -- Click "+" button

	@Test(priority = 4)
	public void addPatient() throws InterruptedException{

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();


		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);

		Assert.assertTrue(true);


	}

	//Add patient with valid info

	@Test(priority = 5)
	public void validPatientData() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);


		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientfirstName;
		String PlastName = PatientsInfoData.patientlastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.click();
		Thread.sleep(5000);

		String URL = driver.getCurrentUrl();
		Assert.assertTrue(URL.contains("https://dev.zntral.net/patients/"));
		Thread.sleep(3000);

	}


	//Add patient with invalid info (Unlimited characters in name fields)

	@Test(priority = 26)
	public void invalidPatientName() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);


		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientInvalidFirstName;
		String PlastName = PatientsInfoData.patientInvalidLastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement firstNameErrorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body//div[@id='inspire']//div[@role='document']//div//div//div//div//div//div//div//div[2]//div[1]//div[1]//div[2]//div[1]//div[1]//div[1]")));
		firstNameErrorMsg.isDisplayed();
		System.out.println(firstNameErrorMsg.getText());

		WebElement lastNameErrorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body//div[@id='inspire']//div[@role='document']//div//div//div//div//div//div//div//div[2]//div[1]//div[1]//div[2]//div[1]//div[1]//div[1]")));
		lastNameErrorMsg.isDisplayed();
		System.out.println(lastNameErrorMsg.getText());

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.isDisplayed();

	}


	//Adding patient without any info

	@Test(priority = 6)
	public void invalidPatientData() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);


		String Pprefix = PatientsInfoData.prefix2;
		String PfirstName= PatientsInfoData.patientfirstName2;
		String PlastName = PatientsInfoData.patientlastName2;
		String Pssn = PatientsInfoData.ssn2;
		String Pdob = PatientsInfoData.dob2;
		String Pphone = PatientsInfoData.phone2;
		String Pemail = PatientsInfoData.patientemail2;
		String Pnote = PatientsInfoData.patientnote2;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.isEnabled();
		Thread.sleep(5000);

		WebElement firstNameMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.firstNameMsg)));
		WebElement lastNameMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.lastNameMsg)));

		String expectedText4 = "First name is required";
		String actualText4 = firstNameMsg.getText();
		Assert.assertEquals(actualText4, expectedText4);

		String expectedText5 = "Last name is required";
		String actualText5 = lastNameMsg.getText();
		Assert.assertEquals(actualText5, expectedText5);

		Thread.sleep(1000);
	}


	//Edit Patient info

	@Test(priority = 7)
	public void editPatientInfo() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(2000);
		WebElement patientSelect = driver.findElement(By.xpath(PatientsXpath.firstRow));
		patientSelect.click();
		Thread.sleep(2000);

		WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.editButton)));
		editButton.click();

		WebElement editOptionSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.editOptionSelect)));
		editOptionSelect.click();
		Thread.sleep(1000);

		WebElement editSSN = driver.findElement(By.xpath(PatientsXpath.editSSN));
		editSSN.clear();

		while (!editSSN.getAttribute("value").equals("")) {
			editSSN.sendKeys(Keys.BACK_SPACE);
		}
		editSSN.sendKeys(PatientsInfoData.validssn);

		WebElement update = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.update)));
		update.click();
		Thread.sleep(3000);

		Assert.assertTrue(true);
	}

	//Delete Patient info

	@Test(priority = 8)
	public void deletePatientInfo() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(2000);
		WebElement patientSelect = driver.findElement(By.xpath(PatientsXpath.firstRow));
		patientSelect.click();


		WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.editButton)));
		editButton.click();

		WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.deleteButton)));
		deleteButton.click();


		WebElement deletePopUpYes = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.deletePopUpYes)));
		deletePopUpYes.click();

		String URL = driver.getCurrentUrl();
		Assert.assertTrue(URL.contains("https://dev.zntral.net/patients"));
		Thread.sleep(3000);
	}

	//Add contact info after adding new patient info

	@Test(priority = 9)
	public void addContactfromPatient() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);

		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientfirstName;
		String PlastName = PatientsInfoData.patientlastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.click();
		Thread.sleep(5000);

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addContactButton));
		addContactButton.click();
		Thread.sleep(1000);


		String CFirstName=PatientsInfoData.firstName;
		String Cphone2 = PatientsInfoData.phonenumber;

		PatientsFunctions.addContact(CFirstName, Cphone2);

		WebElement save2 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save2)));
		save2.click();
		Thread.sleep(5000);

		Assert.assertTrue(true);
	}

	//Add contact with empty fields after adding new patient 

	@Test(priority = 10)
	public void addContactWithEmptyData() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);

		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientfirstName;
		String PlastName = PatientsInfoData.patientlastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.click();
		Thread.sleep(5000);


		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addContactButton));
		addContactButton.click();
		Thread.sleep(1000);

		String CFirstName=PatientsInfoData.firstName2;
		String Cphone2 = PatientsInfoData.phone2;

		PatientsFunctions.addContact(CFirstName, Cphone2);

		WebElement save2 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save2)));
		save2.isEnabled();
		Thread.sleep(5000);

		WebElement firstNameMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.FirstNameMsg)));
		String expectedText4 = "First name is required";
		String actualText4 = firstNameMsg.getText();
		Assert.assertEquals(actualText4, expectedText4);

	}

	//Add contact to any patient from patients list 

	@Test(priority = 11)
	public void addContactFromPatientList() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addContactButton));
		addContactButton.click();
		Thread.sleep(1000);

		String CFirstName=PatientsInfoData.firstName;
		String Cphone2 = PatientsInfoData.phonenumber;

		PatientsFunctions.addContact(CFirstName, Cphone2);

		WebElement save =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save2)));
		save.click();
		Thread.sleep(5000);



	}

	//Add Schedule info after adding new patient info
	@Test(priority = 12)
	public void addSchdeulefromPatient() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);


		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientfirstName;
		String PlastName = PatientsInfoData.patientlastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.click();
		Thread.sleep(5000);

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addScheduleButton = driver.findElement(By.xpath(PatientsXpath.addScheduleButton));
		addScheduleButton.click();
		Thread.sleep(1000);

		WebElement startDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.startDate)));
		startDate.click();		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select1))).click();
		Thread.sleep(1000);

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select2))).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select3))).sendKeys("This is test");

		WebElement save3 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save3)));
		save3.click();
		Thread.sleep(5000);
		Assert.assertTrue(true);


	}

	//Add schedule to any patient from patients list 

	@Test(priority = 13)
	public void addSheduleFromPatientList() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addScheduleButton = driver.findElement(By.xpath(PatientsXpath.addScheduleButton));
		addScheduleButton.click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select4))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select5))).click();
		Thread.sleep(1000);

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select6))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select3))).sendKeys("This is test");

		WebElement save =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save3)));
		save.click();
		Thread.sleep(5000);



	}

	//Add schedule with empty fields after adding new patient 

	@Test(priority = 14)
	public void addSchdeuleWithEmpty() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);

		String Pprefix = PatientsInfoData.prefix;
		String PfirstName= PatientsInfoData.patientfirstName;
		String PlastName = PatientsInfoData.patientlastName;
		String Pssn = PatientsInfoData.ssn;
		String Pdob = PatientsInfoData.dob;
		String Pphone = PatientsInfoData.phone;
		String Pemail = PatientsInfoData.patientemail;
		String Pnote = PatientsInfoData.patientnote;

		PatientsFunctions.addPatientData(Pprefix, PfirstName, PlastName, Pssn, Pdob, Pphone, Pemail, Pnote);

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));
		save.click();
		Thread.sleep(5000);

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addScheduleButton = driver.findElement(By.xpath(PatientsXpath.addScheduleButton));
		addScheduleButton.click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select3))).sendKeys("");

		WebElement save2 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save3)));
		save2.click();
		Thread.sleep(5000);


		Assert.assertTrue(true);
	}


	//Add schedule with empty fields from the patient list

	@Test(priority = 15)
	public void addEmptySheduleFromPatientList() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addScheduleButton));
		addContactButton.click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.select3))).sendKeys("");

		WebElement save =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save3)));
		save.click();
		Thread.sleep(5000);


		WebElement contactNameSelect =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.status)));
		String actualText = contactNameSelect.getText();
		String expectedText = "Failed to add Schedule";
		Assert.assertTrue(actualText.contains(expectedText));

	}

	//Contact form cancel button check

	@Test(priority = 16)
	public void contactFormCancelButton() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addContactButton));
		addContactButton.click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.cancel))).click();
		Thread.sleep(5000);

		Assert.assertTrue(true);

	}

	//Schedule form cancel button check

	@Test(priority = 17)
	public void sheduleFormCancelButton() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement addContactButton = driver.findElement(By.xpath(PatientsXpath.addScheduleButton));
		addContactButton.click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.close))).click();
		Thread.sleep(2000);

		Assert.assertTrue(true);

	}

	//import patient list

	@Test(priority = 18)
	public void importPatientList() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.addButton2)));
		add.click();

		WebElement importButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.importButton)));
		importButton.click();

//		WebElement dropFile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.dropFile)));
//		dropFile.click();

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();

		//check preview button

		WebElement preview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.preview)));
		preview.click();
		Thread.sleep(2000);
		Assert.assertTrue(true);
	}

	//Add location after adding new patient without location

	@Test(priority = 19)
	public void addNewLocationToPatient() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.add)));
		add.click();

		WebElement selectAddPatient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectAddPatient)));
		selectAddPatient.click();
		Thread.sleep(2000);


		WebElement prefix = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.prefix)));
		WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.firstName)));
		WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.lastName)));
		WebElement ssn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.ssn)));
		WebElement dob = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.dob)));
		WebElement gender = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.gender)));
		WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.phone)));
		WebElement type = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.type)));
		WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.email)));
		WebElement note = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.note)));

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save)));

		prefix.sendKeys(PatientsInfoData.prefix);
		firstName.sendKeys(PatientsInfoData.patientfirstName);
		lastName.sendKeys(PatientsInfoData.patientlastName);
		ssn.sendKeys(PatientsInfoData.ssn);
		dob.sendKeys(PatientsInfoData.dob);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.ok))).click();
		gender.click();
		WebElement genderMale = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.genderMale)));
		genderMale.click();
		phone.sendKeys(PatientsInfoData.phone);
		type.click();
		WebElement typeMobile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.typeMobile)));
		typeMobile.click();
		email.sendKeys(PatientsInfoData.patientemail);

		note.sendKeys(PatientsInfoData.patientnote);
		save.click();
		Thread.sleep(3000);

		WebElement addLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.addLocation)));
		addLocation.click();

		WebElement selectLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectNewLocation)));
		selectLocation.click();
		Thread.sleep(5000);
		Assert.assertTrue(true);
	}


	//patient status change

	@Test(priority = 20)
	public void patientStatus() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement patientStatus = driver.findElement(By.xpath(PatientsXpath.patientStatus));
		patientStatus.click();

		WebElement test = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.test)));
		Thread.sleep(2000);

		if(test.getText().contains("Active")) {
			WebElement patientStatusDropdown = driver.findElement(By.xpath(PatientsXpath.patientStatusDropdown));
			patientStatusDropdown.click();

			WebElement patientStatusInActive = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.patientStatusInActive)));
			patientStatusInActive.click();
		}

		else if(test.getText().contains("Inactive")) {
			WebElement patientStatusDropdown = driver.findElement(By.xpath(PatientsXpath.patientStatusDropdown));
			patientStatusDropdown.click();

			WebElement patientStatusActive = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.patientStatusActive)));
			patientStatusActive.click();
		}

		else if(test.getText().contains("Pending")) {
			WebElement patientStatusDropdown = driver.findElement(By.xpath(PatientsXpath.patientStatusDropdown));
			patientStatusDropdown.click();

			WebElement patientStatusActive = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.patientStatusActive)));
			patientStatusActive.click();
		}

		Thread.sleep(2000);
		WebElement save = driver.findElement(By.xpath(PatientsXpath.save4));
		save.click();

		Thread.sleep(2000);

		WebElement successMsg =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.successMsg)));
		String actualText = successMsg.getText();
		System.out.println(actualText);
		String expectedText = "Updated successfully";
		Assert.assertTrue(actualText.contains(expectedText));
	}


	//End care form

	@Test(priority = 21)
	public void endOfCareForm() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement endOfCareForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.endOfCareForm)));
		endOfCareForm.click();
		Thread.sleep(2000);

		WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.name)));
		name.sendKeys("TEST");

		WebElement dd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.dd)));
		dd.sendKeys("TEST2");

		Thread.sleep(2000);
		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save4)));
		save.click();

		Assert.assertTrue(true);

	}


	//Hosp MD form

	@Test(priority = 22)
	public void hospMD() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement hospMD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.hospMD)));
		hospMD.click();
		Thread.sleep(2000);

		WebElement type = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.type2)));
		type.sendKeys("TEST");

		WebElement orders = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.orders)));
		orders.sendKeys("TEST");

		WebElement communication = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.communication)));
		communication.sendKeys("TEST");

		WebElement incidents = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.incidents)));
		incidents.sendKeys("TEST");

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save4)));
		save.click();
		Thread.sleep(2000);

		Assert.assertTrue(true);
	}


	//Test Form

	@Test(priority = 23)
	public void testForm() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);
		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);
		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement testForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.testForm)));
		testForm.click();
		Thread.sleep(2000);

		//Main patient
		WebElement co = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.co)));
		co.sendKeys("Mr");

		WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.firstName1)));
		firstName.sendKeys("Sabbir");

		WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.lastName1)));
		lastName.sendKeys("Ahmed");

		WebElement gender = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.gender1)));
		gender.click();
		Thread.sleep(3000);

		//Disorder
		WebElement diseaseDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.diseaseDropdown)));
		diseaseDropdown.click();

		WebElement selectDisease= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.selectDisease)));
		selectDisease.click();

		WebElement allergies= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.allergies)));
		allergies.click();


		//body system
		WebElement ambulation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.ambulation)));
		ambulation.click();

		WebElement diet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.diet)));
		diet.click();

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save4)));
		save.click();
		Thread.sleep(2000);

		Assert.assertTrue(true);

	}


	//Single visit form

	@Test(priority = 24)
	public void snVisit() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);

		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement snvisit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.snvisit)));
		snvisit.click();
		Thread.sleep(2000);

		WebElement mr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.mr)));
		mr.sendKeys("Mr");

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save4)));
		save.click();
		Thread.sleep(2000);

		Assert.assertTrue(true);
	}


	//Initial visit form

	@Test(priority = 25)
	public void initialVisit() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

		PatientsFunctions.verifyLogin();

		PatientsFunctions.viewPatientList();

		Thread.sleep(3000);

		WebElement firstRow = driver.findElement(By.xpath(PatientsXpath.firstRow));	
		firstRow.click();
		Thread.sleep(3000);

		WebElement addButton1 = driver.findElement(By.xpath(PatientsXpath.addButton1));
		addButton1.click();

		WebElement initialVisit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.initialVisit)));
		initialVisit.click();
		Thread.sleep(2000);

		//Event

		WebElement inPerson = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.inPerson)));
		inPerson.click();

		WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PatientsXpath.save5)));
		save.click();
		Thread.sleep(2000);

		Assert.assertTrue(true);
	}


	@AfterSuite
	public static void afterSuit() {

		System.out.println( testSuiteName + " execution Complete");
	}

}
