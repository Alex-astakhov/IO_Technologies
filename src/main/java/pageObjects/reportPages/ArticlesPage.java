package pageObjects.reportPages;

import core.MethodsFactory;
import dataModels.ArticleModel;
import enums.ArticleMetrics;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticlesPage extends MethodsFactory {
    private By sortingDirectionCheckerValue = By.cssSelector(".ui__switch input");
    private By sortingDirectionChecker = By.cssSelector(".ui__switch .slider");
    private By metricsSelect = By.cssSelector(".data_articles_sorting .filter_title_value");
    private By pageViewsMetricOption = By.cssSelector(".filters_list.on [data-name='pageviews']");
    private By averageTimeMetricOption = By.cssSelector(".filters_list.on [data-name='timeread']");
    private By finishedReadingMetricOption = By.cssSelector(".filters_list.on [data-name='readability']");
    private By recirculationMetricOption = By.cssSelector(".filters_list.on [data-name='recirculation']");

    private By pageViewsValue = By.xpath(".//*[contains(@data-tooltip,'Pageviews for')]");
    private By averageTimeValue = By.cssSelector(".time_value");
    private By finishedReadingValue =
            By.xpath(".//*[contains(@class, 'data_list')]//*[contains(@class, 'col_6')][1]//*[@class='persent_bar_value']");
    private By recirculationValue =
            By.xpath(".//*[contains(@class, 'data_list')]//*[contains(@class, 'col_6')][2]//*[@class='persent_bar_value']");

    private By nextPageButton = By.cssSelector("[data-action='next_page']");
    private By prevPageButton = By.cssSelector("[data-action='prev_page']");

    @Step("Select metric to sort: {0}")
    public void sortByMetric(ArticleMetrics articleMetrics){
        click(metricsSelect);
        switch (articleMetrics){
            case PAGEVIEWS:
                clickWithoutScroll(pageViewsMetricOption);
                break;
            case AVERAGE_TIME:
                clickWithoutScroll(averageTimeMetricOption);
                break;
            case FINISHED_READING:
                clickWithoutScroll(finishedReadingMetricOption);
                break;
            case RECIRCULATION:
                clickWithoutScroll(recirculationMetricOption);
        }
    }

    @Step("Set sorting direction from best to worst: {0}")
    public void setSortingDirection(boolean fromBest){
        if (fromBest && isChecked(sortingDirectionCheckerValue))
            click(sortingDirectionChecker);
        if (!fromBest && !isChecked(sortingDirectionCheckerValue))
            click(sortingDirectionChecker);
    }

    @Step("Get all articles metrics...")
    public List<ArticleModel> getAllArticlesFromPage(){
        int articlesCount = getAllElements(pageViewsValue).size();
        List<ArticleModel> result = new ArrayList<>();
        for (int i = 0; i < articlesCount; i++) {
            int pageViews = getPageViewsValueByIndex(i);
            Date averageTime = getAverageTimeValueByIndex(i);
            int finishedReading = getFinishedReadingValueByIndex(i);
            int recirculation = getRecirculationValueByIndex(i);
            result.add(new ArticleModel(pageViews, averageTime, finishedReading, recirculation));
        }
        return result;
    }

    @Step("Get page views metric from row {0}")
    private int getPageViewsValueByIndex(int index){
        return Integer.parseInt(getTextFromWebElement(getAllElements(pageViewsValue).get(index)));
    }

    @Step("Get average time metric from row {0}")
    private Date getAverageTimeValueByIndex(int index){
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        Date result = null;
        try {
            result = formatter.parse(getTextFromWebElement(getAllElements(averageTimeValue).get(index)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Step("Get finished reading metric from row {0}")
    private int getFinishedReadingValueByIndex(int index){
        return Integer.parseInt(getTextFromWebElement(getAllElements(finishedReadingValue).get(index)).replaceAll("[^0-9]+", ""));
    }

    @Step("Get recirculation metric from row {0}")
    private int getRecirculationValueByIndex(int index){
        return Integer.parseInt(getTextFromWebElement(getAllElements(recirculationValue).get(index)).replaceAll("[^0-9]+", ""));
    }

    @Step("Click 'Next'")
    public void clickNext(){
        if (elementIsPresent(nextPageButton))
            click(nextPageButton);
    }

    @Step("Click 'Back'")
    public void clickBack(){
        if (elementIsPresent(prevPageButton) && elementIsVisible(prevPageButton))
            click(prevPageButton);
    }
}
