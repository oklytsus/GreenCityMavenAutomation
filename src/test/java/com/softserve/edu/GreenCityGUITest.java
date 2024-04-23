package com.softserve.edu;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GreenCityGUITest {
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    @FindBy(css = "app-header:nth-child(1) .ubs-header-sing-in-img")
    private WebElement signInButton;
    @FindBy(css = ".main-picture")
    private WebElement mainPicture;
    @FindBy(css = ".container h1")
    private WebElement welcomeText;
    @FindBy(xpath = "//h2[contains(.,\'Please enter your details to sign in.\')]")
    private WebElement signInDetailsText;
    //@FindBy(css = "label:nth-child(1)")
    @FindBy(xpath = "//label[contains(.,\'Email\')]")
    private WebElement emailLabel;
    @FindBy(xpath = "//label[contains(.,\'Password\')]")
    private WebElement passwordLabel;
    @FindBy(xpath = "//img[@alt=\'show-hide-password\']")
    private WebElement showHidePasswordImage;
    @FindBy(xpath = "//img[@alt=\'hide password\']")
    private WebElement hidePasswordImage;
    @FindBy(xpath = "//img[@alt=\'show password\']")
    private WebElement showPasswordImage;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@type=\'submit\']")
    private WebElement signInSubmitButton;
    @FindBy(xpath = "//span[contains(.,\'or\')]")
    private WebElement orSpan;
    @FindBy(xpath = "//button[contains(.,\'Sign in with Google\')]")
    private WebElement signInWithGoogleButton;
    @FindBy(linkText = "Forgot password?")
    private WebElement forgotPasswordLink;
    @FindBy(css = ".missing-account > p")
    private WebElement missingAccount;
    @FindBy(linkText = "Sign up")
    private WebElement signUpLink;
    @FindBy(xpath = "//img[@alt=\'close button\']")
    private WebElement closeButton;
    @FindBy(xpath = "//img[@alt=\'ubs-armored-track\']")
    private WebElement ubsPickUpPicture;


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
    public void verifyMainPictureIsDisplayed() throws InterruptedException{
        signInButton.click();
        Thread.sleep(6000); // For Presentation
        assert(mainPicture.isDisplayed());
    }
    @Test
    public void verifyWelcomeText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        assertThat(welcomeText.getText(), is("Welcome back!"));
    }

    @Test
    public void verifySignInDetailsText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(signInDetailsText.getText(), is("Please enter your details to sign in."));
    }

    @Test
    public void verifyEmailLabelText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(emailLabel.getText(), is("Email"));
    }

    @Test
    public void verifyEmailPlaceholder() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Assertions.assertEquals("example@email.com", emailInput.getAttribute("placeholder"));
    }
    @Test
    public void verifyPasswordLabelText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(passwordLabel.getText(), is("Password"));
    }

    @Test
    public void verifyPasswordPlaceholder() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Assertions.assertEquals("Password", passwordInput.getAttribute("placeholder"));
    }
    @Test
    public void verifyShowHidePasswordFunctionality() throws InterruptedException{
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        showHidePasswordImage.click();
        Thread.sleep(6000); // For Presentation
        assert(hidePasswordImage.isDisplayed());
        hidePasswordImage.click();
        Thread.sleep(6000); // For Presentation
        assert(showPasswordImage.isDisplayed());
    }

    @Test
    public void verifySubmitButtonText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(signInSubmitButton.getText(), is("Sign in"));
    }
    @Test
    public void verifySubmitButtonIsDisabled() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertFalse(signInSubmitButton.isEnabled());
    }
    @Test
    public void verifyOrSpanText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(orSpan.getText(), is("or"));
    }
    @Test
    public void verifySignInWithGoogleButtonText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(signInWithGoogleButton.getText(), is("Sign in with Google"));
    }
    @Test
    public void verifyForgotPasswordLinkText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(forgotPasswordLink.getText(), is("Forgot password?"));
    }
    @Test
    public void verifyMissingAccountText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(missingAccount.getText(), is("Don\'t have an account yet? Sign up"));
    }
    @Test
    public void verifySignUpLinkText() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertThat(signUpLink.getText(), is("Sign up"));
    }

    @AfterEach
    public void closeSignInWindow() {
        closeButton.click();;
    }
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
