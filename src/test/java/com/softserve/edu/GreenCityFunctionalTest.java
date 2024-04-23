package com.softserve.edu;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreenCityFunctionalTest {
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    @FindBy(css = "app-header:nth-child(1) .ubs-header-sing-in-img")
    private WebElement signInButton;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(xpath = "//*[@id=\"email-err-msg\"]/app-error/div")
    private WebElement errorEmail;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//div[@id=\'pass-err-msg\']/app-error/div")
    private WebElement errorPassword;
    @FindBy(xpath = "//button[@type=\'submit\']")
    private WebElement signInSubmitButton;
    @FindBy(css = ".mat-simple-snackbar > span")
    private WebElement result;
    @FindBy(css = ".alert-general-error")
    private WebElement errorGeneralMessage;
    @FindBy(xpath = "//img[@alt=\'close button\']")
    private WebElement closeButton;


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

    @ParameterizedTest
    @MethodSource("EmailErrorProvider")
    public void verifyEmailInvalidError(String email, String emailError) {
        signInButton.click();
        emailInput.sendKeys(email);
        emailInput.sendKeys("\t");
        assertThat(errorEmail.getText(), is(emailError));
        assertFalse(signInSubmitButton.isEnabled());
    }

    @Test
    public void verifyGeneralEmptyFieldsError() {
        signInButton.click();
        emailInput.sendKeys("\t");
        passwordInput.sendKeys("\t");
        assertThat(errorGeneralMessage.getText(), is("Please fill all required fields."));
        assertFalse(signInSubmitButton.isEnabled());
    }

    @ParameterizedTest
    @MethodSource("PasswordErrorProvider")
    public void verifyPasswordInvalidError(String password, String passwordError) {
        signInButton.click();
        passwordInput.sendKeys(password);
        passwordInput.sendKeys("\t");
        assertThat(errorPassword.getText(), is(passwordError));
        assertFalse(signInSubmitButton.isEnabled());
    }
    @ParameterizedTest
    @CsvSource({
            "a@c.v, password",
            "sim58801@fosiq.com, password",
            "a@c.v, TestUser_Password1"
    })
    public void verifyInvalidInputError(String email, String password) {
        signInButton.click();
        emailInput.sendKeys(email);
        assertThat(emailInput.getAttribute("value"), is(email));
        passwordInput.sendKeys(password);
        assertThat(passwordInput.getAttribute("value"), is(password));
        assertTrue(signInSubmitButton.isEnabled());
        signInSubmitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(errorGeneralMessage.getText(), is("Bad email or password"));
    }

    @ParameterizedTest
    @MethodSource("EmailPasswordInvalidDataProvider")
    public void verifyInvalidDataCheckingFunctionality(String email, String password) {
        signInButton.click();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        passwordInput.sendKeys("\t");
        assertFalse(signInSubmitButton.isEnabled());
    }

    @AfterEach
    public void closeSignInWindow() {
        closeButton.click();;
    }
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    private static Object[] EmailErrorProvider() {
        String emptyEmailErrorMessage = "Email is required";
        String invalidEmailErrorMessage = "Please check if the email is written correctly";
        return new Object[][]{
                {"", emptyEmailErrorMessage},
                {"a@v", invalidEmailErrorMessage},
                {"a@v.", invalidEmailErrorMessage},
                {"@v.a", invalidEmailErrorMessage},
                {"a@.v", invalidEmailErrorMessage},
                {"a.v@c", invalidEmailErrorMessage},
                {"a.v", invalidEmailErrorMessage}
        };
    }
    private static Object[] PasswordErrorProvider() {
        String invalidPasswordErrorMessage = "Password have from 8 to 20 characters long without spaces and contain at least one uppercase letter (A-Z), one lowercase letter (a-z), a digit (0-9), and a special character (~`!@#$%^&*()+=_-{}[]|:;”’?/<>,.)";
        String toLongPasswordErrorMessage = "Password must be less than 20 characters long without spaces.";
        String emptyPasswordErrorMessage = "Password is required";
        return new Object[][]{
                {"p", invalidPasswordErrorMessage},
                {"1234567", invalidPasswordErrorMessage},
                {"",emptyPasswordErrorMessage},
                {"123456789012345678901", toLongPasswordErrorMessage}

        };
    }

    private static Object[] EmailPasswordInvalidDataProvider() {

        return new Object[][]{
                {"","" },
                {"a@v.c","" },
                {"","password"},
                {"a","p"},
                {"a","password"},
                {"a@c.v","p"},
                {"","p"},
                {"a",""},
                {"a@c.v","123456789012345678901"},
                {"","123456789012345678901"},
                {"a.v", "123456789012345678901"}
        };
    }
}
