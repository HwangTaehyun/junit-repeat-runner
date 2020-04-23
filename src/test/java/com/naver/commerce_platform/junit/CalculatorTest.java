package com.naver.commerce_platform.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RepeatRunner.class)
public class CalculatorTest {

    static final Logger logger =
            LoggerFactory.getLogger(RepeatRunner.class);

    @Rule
    public final TestRule watchman = new TestWatcher() {
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
            logger.info("Run {}...", description);
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
            logger.info("End {}...", description);
        }
    };


    @Test
    @Repeat(count = 5)
    public void testMyCode5Times() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = 3;
        int y = 5;

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(8);
    }

    @Test
    @Repeat(count = 10)
    public void testMyCode10Times() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = 5;
        int y = 5;

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(10);
    }
}
