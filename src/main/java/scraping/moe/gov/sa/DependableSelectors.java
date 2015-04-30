package scraping.moe.gov.sa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import selenium.elements.ElementNotFound;
import selenium.elements.Search;

import java.util.*;

/**
 * User: YamStranger
 * Date: 4/29/15
 * Time: 3:14 PM
 */

/**
 * DependentSelectors created for loading options of selectors, witch depend on
 * each other.
 * Priority of dependencies described by input array selectors.
 * example of behaving such selectors on page :
 * selectors[3]={xpath1, xpath2, xpath3};
 * for every unique option of selector by xpath2 next selector(xpath3) will contains unique values.
 * for every unique option of selector by xpath1 next selector(xpath2) will contains unique values.
 * and so on.
 */
public class DependableSelectors {
    private final List<Search> searches = new LinkedList<>();
    private final WebDriver driver;
    private final Set<String> skip = new HashSet<>();

    public DependableSelectors(final WebDriver webDriver, final Search... searches) {
        this.driver = webDriver;
        this.searches.addAll(Arrays.asList(searches));
        this.skip.add("-1");
    }

    /**
     * load all options for selectors according selectors xpath.
     * Selectors must depend on previous selector.
     *
     * @return
     */
    public List<Option> load() throws ElementNotFound {
        final List<Option> options = new LinkedList<>();
        load(options, this.searches);
        return options;
    }

    int l = 0;

    private void load(final List<Option> values, final List<Search> queue) throws ElementNotFound {
        if (!queue.isEmpty()) {
            l += 1;
            final List<Search> level = new ArrayList<>(queue.size());
            level.addAll(queue);
            final Search search = level.remove(0);
            final Select select = new Select(search.one(driver));
            final Map<String, String> options = this.options(select);
/*            Selector

            final WebElement element = driver.findElement(By.xpath("/*//*[@id=\"ctl00_ctl15_g_ad55844d_fe4d_4f19_8dc9_99de149ea796_ctl00_updProgressSchool\"]"));
            return !element.isDisplayed();*/

            for (final Map.Entry<String, String> option : options.entrySet()) {

                final String value = option.getKey();
                final String text = option.getValue();
                //try {
                if (!skip.contains(value)) {
                    System.out.println(l + " value=" + value + ", text=" + text.hashCode() + ",xpath" + search);
                    final Option current = new Option(search, value, text);
                    values.add(current);
                    DropDownList list = new DropDownList(this.driver, search);
                    list.select(value);
                    //this.select(xpath, value, new SelectedCondition(xpath, value));
                    this.load(current.dependable, level);
                }
                /*} catch (StaleElementReferenceException staleElementReferenceException) {
                    String id = String.valueOf(new Date().getTime());

                    System.out.println("exception during loading," + id + ".png" + "    " + staleElementReferenceException.getMessage());
                    File scrsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(scrsht, new File(id + ".png"));
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }*/
            }
            l -= 1;
        }
    }
/*

    private static class SelectedCondition implements ExpectedCondition<Boolean> {
        private final String xpath;
        private final String value;

        private SelectedCondition(String xpath, String value) {
            this.xpath = xpath;
            this.value = value;
        }

        public Boolean apply(final WebDriver driver) {
            final WebElement element = driver.findElement(By.xpath("/*/
/*[@id=\"ctl00_ctl15_g_ad55844d_fe4d_4f19_8dc9_99de149ea796_ctl00_updProgressSchool\"]"));
            return !element.isDisplayed();
        }
    }


    private void select(final String xpath, final String value,
                        final ExpectedCondition<Boolean> expectation) {
        final WebElement webElement = this.driver.findElement(By.xpath(xpath));
        final Select select = new Select(webElement);
//        select.deselectAll();
        select.selectByValue(value);
        this.driver.findElement(By.xpath(xpath)).submit();
//        webElement.submit();
        try {
            Thread.sleep(50);
            (new WebDriverWait(driver, 120)).until(expectation);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
*/


    private Map<String, String> options(final Select select) {
        final Map<String, String> options = new TreeMap<>();
        final List<WebElement> elements = select.getOptions();
        for (final WebElement element : elements) {
            options.put(element.getAttribute("value"), element.getText());
        }
        return options;
    }

    /**
     * Class for
     */
    public static class Option {
        final Search search;
        final String value;
        final String text;
        final List<Option> dependable = new LinkedList<>();

        public Option(final Search search, final String value, final String text) {
            this.search = search;
            this.value = value;
            this.text = text;
        }

        public void add(final Option child) {
            this.dependable.add(child);
        }

    }
}
