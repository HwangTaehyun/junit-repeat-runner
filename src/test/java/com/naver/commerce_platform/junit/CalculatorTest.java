package com.naver.commerce_platform.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RepeatRunner.class)
public class CalculatorTest {

    static final Logger logger =
            LoggerFactory.getLogger(RepeatRunner.class);

    @Test
    @Repeat(count = 5)
    public void testMyCode5Times() {
        //Log
        logger.info("Run testMyCode5Times...");

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
        //Log
        logger.info("Run testMyCode5Times...");

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
