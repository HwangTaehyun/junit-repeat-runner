package com.naver.commerce_platform.junit;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepeatTestWatcher extends TestWatcher {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    protected void succeeded(Description description) {
        logger.info("{} unit test succeeded.", description.getMethodName());
    }

    @Override
    protected void failed(Throwable e, Description description) {
        logger.error("{} unit test failed with {}.", description.getMethodName(), e);
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
    }
}
