package listeners;

import core.BrowserFactory;
import core.MethodsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static core.Constants.BROWSER_LOG;

public class ListenerWithBrowserShot implements ITestListener {

    private Logger logger = LoggerFactory.getLogger("WebDriver");

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println();
        logger.info(iTestResult.getMethod().getMethodName() + " is started...");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        MethodsFactory.allureSetStory(BrowserFactory.BROWSER);
        logger.info(iTestResult.getMethod().getMethodName() + " is finished successfully!\n");
        if(BROWSER_LOG)
            MethodsFactory.printBrowserLog(false);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        MethodsFactory.allureSetStory(BrowserFactory.BROWSER);
        MethodsFactory.pngAttachment(iTestResult.getMethod().getMethodName());
        logger.info(iTestResult.getMethod().getMethodName() + " is failed!\n");
        if(BROWSER_LOG) {
            MethodsFactory.printBrowserLog(true);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        MethodsFactory.allureSetStory(BrowserFactory.BROWSER);
        logger.info(iTestResult.getMethod().getMethodName() + " is skipped!\n\n");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        MethodsFactory.allureSetStory(BrowserFactory.BROWSER);
        MethodsFactory.pngAttachment(iTestResult.getMethod().getMethodName());
        logger.info(iTestResult.getMethod().getMethodName() + " is partly failed!\n\n");
        if(BROWSER_LOG)
            MethodsFactory.printBrowserLog(false);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info("--------------------   Started suite <<< " + iTestContext.getSuite().getName() + " >>>   --------------------\n");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("--------------------   Finished suite <<< " + iTestContext.getSuite().getName() + " >>>   -------------------\n");
    }
}
