package com.naver.commerce_platform.junit.test;

import com.naver.commerce_platform.junit.Calculator;
import com.naver.commerce_platform.junit.utils.runner.RepeatRunner;
import com.naver.commerce_platform.junit.utils.annotation.Repeat;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RepeatRunner.class)
public class CalculatorTest {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);

    @Test
    @Repeat(count = -1, testName = "invalid-repeat-count")
    public void testInvalidCountRepeat() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = (int)(2*Math.random());
        int y = (int)(2*Math.random());

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(1);
    }

    @Test
    @Repeat(count = 5, testName = "random-assert")
    public void testRandomAssert() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = (int)(2*Math.random());
        int y = (int)(2*Math.random());

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(1);
    }

    @Test
    @Repeat(count = 5, testName = "rerun-failed", retryCount = 3)
    public void testReRunFailedTest() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = (int)(2*Math.random());
        int y = (int)(2*Math.random());

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(1);
    }

    @Test
    @Repeat(count = 2, testName = "repeat-2")
    public void testMyCode2TimesWithName() {
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
    @Repeat(count = 2)
    public void testMyCode2Times() {
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
    @Repeat(count = 4)
    public void testMyCode4Times() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = 5;
        int y = 5;

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void testNoRepeatAnnotation() {
        //Arrange
        Calculator calculator = new Calculator();
        int x = (int)(2*Math.random());
        int y = (int)(2*Math.random());

        //Act
        int result = calculator.add(x,y);

        //Assert
        assertThat(result).isEqualTo(2);
    }
}
