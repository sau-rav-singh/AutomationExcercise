package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import listeners.ExtentReportConfigListener;
import org.apache.http.Header;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExtentReportManager {

    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            String reportPath = getReportPath();
            extentReports = createInstance(reportPath, "Test API Automation Report", "Test Execution Report");
        }
        return extentReports;
    }

    public static ExtentReports createInstance(String fileName, String reportName, String documentTitle) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
        sparkReporter.config().setReportName(reportName);
        sparkReporter.config().setDocumentTitle(documentTitle);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        return reports;
    }

    public static String getReportPath() {
        String directory = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "TestRun_" + System.currentTimeMillis();
        File reportDir = new File(directory);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        return Paths.get(directory, "TestReport.html").toString();
    }


    public static void logPassDetails(String log) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.pass(MarkupHelper.createLabel(log, ExtentColor.GREEN)));
    }

    public static void logFailureDetails(String log) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.fail(MarkupHelper.createLabel(log, ExtentColor.RED)));
    }

    public static void logExceptionDetails(String log) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.fail(log));
    }

    public static void logInfoDetails(String log) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.info(MarkupHelper.createLabel(log, ExtentColor.GREY)));
    }

    public static void logWarningDetails(String log) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW)));
    }

    public static void logJson(String json) {
        ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON)));
    }

    public static void logHeaders(List<Header> headersList) {
        if (headersList != null && !headersList.isEmpty()) {
            String[][] arrayHeaders = headersList.stream().map(header -> new String[]{header.getName(), header.getValue()}).toArray(String[][]::new);
            ExtentReportConfigListener.getExtentTest().ifPresent(test -> test.info(MarkupHelper.createTable(arrayHeaders)));
        }
    }
}
