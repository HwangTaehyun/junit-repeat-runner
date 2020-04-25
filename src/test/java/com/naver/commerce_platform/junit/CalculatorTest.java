package com.naver.commerce_platform.junit;

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
}
