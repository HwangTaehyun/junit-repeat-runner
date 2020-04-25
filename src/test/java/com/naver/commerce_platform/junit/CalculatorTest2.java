package com.naver.commerce_platform.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RepeatRunner.class)
public class CalculatorTest2 {

    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest2.class);

    @Rule
    public final TestRule watchman = new RepeatTestWatcher();

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
