package com.softserve.edu;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class GreenCityLinksTest {
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    @FindBy(css = "app-header:nth-child(1) .ubs-header-sing-in-img")
    private WebElement signInButton;
    @FindBy(css = ".container h1")
    private WebElement welcomeText;
    @FindBy(xpath = "//button[contains(.,\'Sign in with Google\')]")
    private WebElement signInWithGoogleButton;
    @FindBy(linkText = "Forgot password?")
    private WebElement forgotPasswordLink;
    @FindBy(css = ".restore-password-container > h1")
    private WebElement restorePasswordHeader;
    @FindBy(linkText = "Back to Sign in")
    private WebElement backToSignInLink;
    @FindBy(linkText = "Sign up")
    private WebElement signUpLink;
    @FindBy(xpath = "//span[contains(.,\'Sign in with Google\')]")
    private WebElement signInWithGoogleLink;
    @FindBy(xpath = "//h2[contains(.,\'Please enter your details to sign up.\')]")
    private WebElement signUpSubHeader;
    @FindBy(linkText = "Sign in")
    private WebElement signInLink;
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

    @Test
    public void verifyForgotPasswordLink() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        forgotPasswordLink.click();
        assert(restorePasswordHeader.isDisplayed());
        backToSignInLink.click();
        assert(welcomeText.isDisplayed());
    }
    @Test
    public void verifySignUpLink() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        signUpLink.click();
        assert(signUpSubHeader.isDisplayed());
        signInLink.click();
        assert(welcomeText.isDisplayed());
    }
    @Test
    public void verifySignInWithGoogleLink() {
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assert (signInWithGoogleLink.isEnabled());
        signInWithGoogleLink.click();
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
