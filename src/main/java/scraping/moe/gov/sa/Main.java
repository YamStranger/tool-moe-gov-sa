package scraping.moe.gov.sa;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.elements.Condition;
import selenium.elements.Search;

/**
 * User: YamStranger
 * Date: 4/29/15
 * Time: 1:09 PM
 */
public class Main extends Thread {
    @Override
    public void run() {
        System.setProperty("webdriver.chrome.driver", "chrome-web-driver\\chromedriver.exe");
        final ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.moe.gov.sa/English/EServices/Public/Pages/SchoolByEducationalOffice.aspx");
        final Search district = new Search("Please select District",
                new Condition(By.xpath("//*[@id=\"ctl00_ctl15_g_ad55844d_fe4d_4f19_8dc9_99de149ea796_ctl00_ddlDistrictID\"]")));
        final Search office = new Search("Please Select Educational Office",
                new Condition(By.xpath("//*[@id=\"ctl00_ctl15_g_ad55844d_fe4d_4f19_8dc9_99de149ea796_ctl00_ddlEducationalOffice\"]")));

        final DependableSelectors selectors = new DependableSelectors(driver, district, office);
        //Close the browser
        driver.quit();
    }

    public static void main(String... args) {
        for (int i = 0; i < 10; ++i) {
            new Main().start();
        }
    }
}
