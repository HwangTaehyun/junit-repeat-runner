package com.naver.commerce_platform.junit;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RepeatRunListener extends RunListener {

    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);
    static Map<String, RepeatTestInfo> testResult = new HashMap<>();
    private static final Description FAILED = Description.createTestDescription("failed", "failed");

    public int getTotalCount(String methodName){
        return this.testResult.get(methodName).totalCount;
    }
    public int getPassCount(String methodName){
        return this.testResult.get(methodName).passCount;
    }
    public int getFailCount(String methodName){
        return this.testResult.get(methodName).failCount;
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        System.out.println("Number of tests to execute: " + description.testCount());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        System.out.println("Number of tests executed: " + result.getRunCount());
    }

    @Override
    public void testStarted(Description description) throws Exception {
        logger.info("{} unit test starting...", description);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        if (!description.getChildren().contains(FAILED)) {
            // Process passed tests here...
            logger.info("{} unit test succeeded.", description.getMethodName());
            RepeatTestInfo testInfo = testResult.get(description.getMethodName());
            if (testInfo == null) {
                testResult.put(description.getMethodName(),new RepeatTestInfo());
            }
            testResult.get(description.getMethodName()).passCount++;
        }
        logger.info("{} unit test finished...", description);
        testResult.get(description.getMethodName()).totalCount++;
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        logger.error("{} unit test failed with {}.", failure.getDescription().getMethodName(), failure.getMessage());
        RepeatTestInfo testInfo = testResult.get(failure.getDescription().getMethodName());
        if (testInfo == null) {
            testResult.put(failure.getDescription().getMethodName(),new RepeatTestInfo());
        }
        testResult.get(failure.getDescription().getMethodName()).failCount++;
        failure.getDescription().addChild(FAILED);
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        logger.info("Ignored: " + description.getMethodName());
    }
}
