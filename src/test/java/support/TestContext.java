package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    private static Map<String, Object> testData = new HashMap();

    public static void saveData(String key, Object data) {
        testData.put(key, data);
    }

    public static Integer getTestDataInteger(String key) {
        return (Integer) testData.get(key);
    }

    public static String getTestDataString(String key) {
        return (String) testData.get(key);
    }


    public static Map<String, Object> getTestDataMap(String key) {
        return (Map<String, Object>) testData.get(key);
    }

    public static String timestamp = new SimpleDateFormat("+yyyy-MM-dd-hh-mm-sss").format(new Date());

    public static void initialize() {
        initialize(getConfig().getBrowser(), getConfig().getTestEnv(), getConfig().isHeadless(), getConfig().isBetaVersion());
    }

    public static void teardown() {
        driver.quit();
    }

    public static void initialize(String browser, String testEnv, boolean isHeadless, boolean isBeta) {
        Dimension size = new Dimension(1920, 1080);
        Point position = new Point(0, 0);
        if (testEnv.equals("local")) {
            switch (browser) {
                case "chrome":
                    if (isBeta) {
                        String browserDetectionCommand = "/Applications/Google Chrome Beta.app/Contents/MacOS/Google Chrome Beta --version";
                        if (SystemUtils.IS_OS_WINDOWS) {
                            browserDetectionCommand = "REG QUERY \"HKCU\\Software\\Google\\Chrome Beta\\BLBeacon\" /v version";
                        }
                        WebDriverManager.chromedriver().clearResolutionCache();
                        WebDriverManager.chromedriver()
                                .browserVersionDetectionCommand(browserDetectionCommand)
                                .setup();
                    } else {
                        WebDriverManager.chromedriver().setup();
                    }
                    Map<String, Object> chromePreferences = new HashMap<>();
                    chromePreferences.put("profile.default_content_settings.geolocation", 2);
                    chromePreferences.put("profile.default_content_settings.popups", 0);
                    chromePreferences.put("download.prompt_for_download", false);
                    chromePreferences.put("download.directory_upgrade", true);
                    chromePreferences.put("download.default_directory", System.getProperty("user.dir") + "/src/test/resources/downloads");
                    chromePreferences.put("safebrowsing.enabled", false);
                    chromePreferences.put("plugins.always_open_pdf_externally", true);
                    chromePreferences.put("plugins.plugins_disabled", new ArrayList<String>(){{ add("Chrome PDF Viewer"); }});
                    chromePreferences.put("credentials_enable_service", false);
                    chromePreferences.put("password_manager_enabled", false);
                    // for EMEA only - disable cookies
//                    chromePreferences.put("profile.default_content_setting_values.cookies", 2);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isBeta) {
                        if (SystemUtils.IS_OS_MAC) {
                            chromeOptions.setBinary("/Applications/Google Chrome Beta.app/Contents/MacOS/Google Chrome Beta");
                        } else if (SystemUtils.IS_OS_WINDOWS) {
                            chromeOptions.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
                        }
                    }
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.setExperimentalOption("prefs", chromePreferences);
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                    if (isHeadless) {
                        chromeOptions.setHeadless(true);
                        chromeOptions.addArguments("--window-size=" + size.getWidth() + "," + size.getWidth());
                        chromeOptions.addArguments("--disable-gpu");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        FirefoxBinary firefoxBinary = new FirefoxBinary();
                        firefoxBinary.addCommandLineOptions("--headless");
                        firefoxOptions.setBinary(firefoxBinary);
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "safari":
                    driver = new SafariDriver();
                    driver.manage().window().setPosition(position);
                    driver.manage().window().setSize(size);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    throw new RuntimeException("Driver is not implemented for: " + browser);
            }
        } else if (testEnv.equals("grid")){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.ANY);
            try {
                URL hubUrl = new URL("http://localhost:4444/wd/hub");
                driver = new RemoteWebDriver(hubUrl, capabilities);
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Unsupported test environment: " + testEnv);
        }
    }

    public static Map<String, String> getDataByFileName(String fileName) {
        return new Yaml().load(getStream(fileName));
    }

    public static Config getConfig() {
        return new Yaml().loadAs(getStream("config"), Config.class);
    }


    private static InputStream getStream(String fileName) {
        String path = System.getProperty("user.dir") + "/src/test/resources/data/" + fileName + ".yml";
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new Error(e);
        }
    }
}