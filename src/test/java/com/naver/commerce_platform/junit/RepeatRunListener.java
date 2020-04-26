package com.naver.commerce_platform.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RepeatRunListener extends RunListener {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);
    /* 각 unit test의 repetition test result info 관리를 위한 컨테이너 */
    private static Map<String, RepeatTestInfo> testResult = new HashMap<>();
    /* fail flag */
    private boolean failed = false;
    private String testName;

    RepeatRunListener() {
        super();
    }

    public int getTotalCount(String testName){
        return this.testResult.get(testName).totalCount;
    }
    public int getPassCount(String testName){
        return this.testResult.get(testName).passCount;
    }
    public int getFailCount(String testName){
        return this.testResult.get(testName).failCount;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public void testStarted(Description description) throws Exception {
        logger.info("{} unit test starting...", testName);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        if (!this.failed) {
            /* Process passed tests here */
            logger.info("{} unit test succeeded.", testName);
            RepeatTestInfo testInfo = testResult.get(testName);
            if (testInfo == null) {
                testResult.put(testName,new RepeatTestInfo());
            }
            testResult.get(testName).passCount++;
        }
        logger.info("{} unit test finished...", testName);
        testResult.get(testName).totalCount++;
        this.failed=false;
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        /* Process failed tests here */
        logger.error("{} unit test failed with {}.", testName, failure.getMessage());
        RepeatTestInfo testInfo = testResult.get(testName);
        if (testInfo == null) {
            testResult.put(testName,new RepeatTestInfo());
        }
        testResult.get(testName).failCount++;
        this.failed = true;
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        /* Process ignored tests here */
        logger.info("Ignored: " + testName);
    }
}
