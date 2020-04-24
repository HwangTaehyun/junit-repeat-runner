package com.naver.commerce_platform.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RepeatTestWatcher extends TestWatcher {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);
    static Map<String, RepeatTestInfo> testResult = new HashMap<>();

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
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    protected void succeeded(Description description) {
        logger.info("{} unit test succeeded.", description.getMethodName());
        RepeatTestInfo testInfo = testResult.get(description.getMethodName());
        if (testInfo == null) {
            testResult.put(description.getMethodName(),new RepeatTestInfo());
        }
        testResult.get(description.getMethodName()).passCount++;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        logger.error("{} unit test failed with {}.", description.getMethodName(), e);
        RepeatTestInfo testInfo = testResult.get(description.getMethodName());
        if (testInfo == null) {
            testResult.put(description.getMethodName(),new RepeatTestInfo());
        }
        testResult.get(description.getMethodName()).failCount++;
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
        logger.info("{} unit test starting...", description);
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        logger.info("{} unit test finished...", description);
        RepeatTestInfo testInfo = testResult.get(description.getMethodName());
        if (testInfo == null) {
            testResult.put(description.getMethodName(),new RepeatTestInfo());
        }
        testResult.get(description.getMethodName()).totalCount++;
    }
}
