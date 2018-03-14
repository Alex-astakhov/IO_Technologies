package core;

import com.google.gson.Gson;
import dataModels.ArticleModel;
import dataModels.PerformanceLogEntryModel;
import enums.ArticleMetrics;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

public class Assertions extends MethodsFactory {

    @Step("Assert performance log contains: {1} {2}")
    public static void AssertPerformanceLogEntry(List<String> logs, String targetMethod, String targetUrl){
        boolean success = false;
        System.out.println("Searching for target request: " + targetMethod + " " + targetUrl);
        Gson gson = new Gson();
        for (String log: logs) {
            PerformanceLogEntryModel entry = gson.fromJson(log, PerformanceLogEntryModel.class);
            System.out.println("Check entry: " + log);
            try {
                String method = entry.message.params.request.method;
                String requestUrl = entry.message.params.request.url;
                if (method.equals(targetMethod) && requestUrl.contains(targetUrl)){
                    success = true;
                    break;
                }
            } catch (NullPointerException e){
                continue;
            }
        }
        Assert.assertTrue(success, "No target request found!");
    }

    @Step("Assert that URL contains: {0}")
    public static void urlContains(String text, int timeout, String message){ // проверка на содержание в URL определенного строкового выражения с таймаутом
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        try{
            wait.until(ExpectedConditions.urlContains(text));
        } catch (TimeoutException e){
            System.out.println("URL doesn't contain " + text + " during " + timeout + " sec.");
            System.out.println("Actual URL: " + driver().getCurrentUrl());
            throw new AssertionError(message + "ErrorData: URL doesn't contain " + text + " during " + timeout + " sec.\n" + "Actual URL: " + driver().getCurrentUrl());
        }
    }

    @Step("Assert order of articles by metric {1}, from best: {2}")
    public static void assertArticlesOrder(List<ArticleModel> articlesMetricsList, ArticleMetrics metric, boolean best){
        for (int i = 0; i < articlesMetricsList.size() - 1; i++) {
            ArticleModel article1 = articlesMetricsList.get(i);
            ArticleModel article2 = articlesMetricsList.get(i + 1);
            int compare = article1.compareByMetric(article2, metric);
            if (best){
                Assert.assertTrue(compare >= 0, "Wrong order of articles by metric (DESC)" + metric.toString() +
                        "\nIn row " + i + ": " + article1.toString() + "\nIn row " + ++i + ": " + article2.toString());
            }
            else {
                Assert.assertTrue(compare <= 0, "Wrong order of articles by metric (ASC)" + metric.toString() +
                        "\nIn row " + i + ": " + article1.toString() + "\nIn row " + ++i + ": " + article2.toString());
            }
        }
    }
}
