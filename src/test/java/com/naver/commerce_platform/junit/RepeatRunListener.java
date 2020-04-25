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
    /* 각 unit test의 repetition test result info 관리를 위한 컨테이너 */
    private static Map<String, RepeatTestInfo> testResult = new HashMap<>();
    /* fail flag */
    private boolean failed = false;

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
        if (!this.failed) {
            /* Process passed tests here */
            logger.info("{} unit test succeeded.", description.getMethodName());
            RepeatTestInfo testInfo = testResult.get(description.getMethodName());
            if (testInfo == null) {
                testResult.put(description.getMethodName(),new RepeatTestInfo());
            }
            testResult.get(description.getMethodName()).passCount++;
        }
        logger.info("{} unit test finished...", description);
        testResult.get(description.getMethodName()).totalCount++;
        this.failed=false;
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        /* Process failed tests here */
        logger.error("{} unit test failed with {}.", failure.getDescription().getMethodName(), failure.getMessage());
        RepeatTestInfo testInfo = testResult.get(failure.getDescription().getMethodName());
        if (testInfo == null) {
            testResult.put(failure.getDescription().getMethodName(),new RepeatTestInfo());
        }
        testResult.get(failure.getDescription().getMethodName()).failCount++;
        this.failed = true;
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        /* Process ignored tests here */
        logger.info("Ignored: " + description.getMethodName());
    }
}
