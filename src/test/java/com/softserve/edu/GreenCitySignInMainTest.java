package com.softserve.edu;
/*
Create a maven project and add the appropriate dependencies.
Create a test class and add the code from the example.
Refactor the code, fix bugs in the existing ones and add new test methods to test
"Signin":
1. check the locators, make changes to optimize the locators. If necessary, add new
locators
2. add test methods for testing positive and negative scenarios to the test class
3. refactor existing parameterized test methods depending on the logic of test
construction and perform additional parameterized tests

 */
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreenCitySignInMainTest {
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    @FindBy(css = "app-header:nth-child(1) .ubs-header-sing-in-img")
    private WebElement signInButton;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@type=\'submit\']")
    private WebElement signInSubmitButton;
    @FindBy(xpath = "//img[@alt=\'close button\']")
    private WebElement closeButton;
    @FindBy(xpath = "//img[@alt=\'ubs-armored-track\']")
    private WebElement ubsPickUpPicture;
    @FindBy(css = "app-header:nth-child(1) .body-2")
    private WebElement userNameSpan;
    @FindBy(xpath = "(//a[contains(text(),\'Sign out\')])[4]")
    private WebElement userSignOutLink;
    private static WebDriver driver;
    @BeforeAll
    public static void setUp() {
        //System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.greencity.social/");
        driver.manage().window().setSize(new Dimension(1264, 798));
    }

    @BeforeEach
    public void initPageElements() {
        PageFactory.initElements(driver, this);
    }
    @Test
    public void verifyTitle() {
        assertThat(driver.getTitle(), is("GreenCity"));
    }
    @Test
    public void verifyCloseButton() throws InterruptedException{
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assert (closeButton.isDisplayed());
        closeButton.click();
        Thread.sleep(2000); // For Presentation
        assert(ubsPickUpPicture.isDisplayed());
    }
    @ParameterizedTest
    @CsvSource({
            "TestUser1, TestUser_Password1, sim58801@fosiq.com",
            "TestUser2, TestUser_Password2, mld10941@vogco.com",
    })
    public void verifySignInWithCorrectData(String userName, String password, String email) {
        signInButton.click();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        assertTrue(signInSubmitButton.isEnabled());
        signInSubmitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assert (userNameSpan.isDisplayed());
        assertThat(userNameSpan.getText(), is(userName));
        userNameSpan.click();
        assert (userSignOutLink.isDisplayed());
        userSignOutLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assert(ubsPickUpPicture.isDisplayed());

    }
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
