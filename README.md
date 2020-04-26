# junit-repeat-runner
* Unit Test의 반복 실행 기능을 지원하는 @Repeat Annot.과 RepeatRunner 엔진 제공

## Table of contents

[● Environments](#Environments)

[● Service Contents](#Service-Contents)

[● Setting](#Setting)

[● Reference](#Reference)

## Environments
* JDK 8
* Gradle 5.2.1
* JUnit 4.12

## Service Contents
* @Repeat Annot.에 로그에 적용될 unit test name (testName) 설정 기능 제공
* 반복 횟수동안, 각 Unit Test의 실패, 성공 여부 정보를 파일 로그로 제공 (-> JUnit.log 파일)
* 각 Unit Test의 총 반복 횟수중 성공 횟수와 실패 횟수를 결과 요약 로그로 제공

![image](https://user-images.githubusercontent.com/42465090/80320644-a418b580-8852-11ea-9479-5c7e3c7766cd.png)
* Unit Test 실패시, @Repeat Annot.에 설정된 retryCount 값만큼 성공할때까지 재실행하는 기능 제공
 ```
@Test
@Repeat(count = 5, retryCount = 2)
public void testReRunFailed() {
    //Arrange
    Calculator calculator = new Calculator();
    int x = (int)(2*Math.random());
    int y = (int)(2*Math.random());

    //Act
    int result = calculator.add(x,y);

    //Assert
    assertThat(result).isEqualTo(1);
}
```
 ![image](https://user-images.githubusercontent.com/42465090/80320303-9d893e80-8850-11ea-8f78-dcd3ba16c042.png)

## Setting
* "tests with error: no tests found for given includes.." 와 같은 에러 발생시 아래와 같은 세팅이 필요합니다.

![image](https://user-images.githubusercontent.com/42465090/80321374-9285dc80-8857-11ea-81c3-a4ba1f8543f8.png)

## Reference
* https://junit.org/junit4/javadoc/4.12/org/junit/runners/package-summary.html
* https://spring.io/guides/gs/gradle/
* https://linked2ev.github.io/devsub/2019/09/30/Intellij-junit4-gradle-issue/
