package com.naver.commerce_platform.junit;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RepeatRunner.class)
public class CalculatorTest {

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
