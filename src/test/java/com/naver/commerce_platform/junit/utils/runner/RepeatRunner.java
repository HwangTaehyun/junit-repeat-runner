package com.naver.commerce_platform.junit.utils.runner;

import com.naver.commerce_platform.junit.test.CalculatorTest;
import com.naver.commerce_platform.junit.utils.annotation.Repeat;
import com.naver.commerce_platform.junit.utils.listener.RepeatRunListener;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepeatRunner extends BlockJUnit4ClassRunner {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);
    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        logger.info("=============Repetition Test Start=============");

        /* get description of method */
        Description description = describeChild(method);

        /* add listener */
        RepeatRunListener listener = new RepeatRunListener();
        notifier.addListener(listener);

        /* handle ignore Annot. */
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
            return;
        }

        /* handle no Repeat Annot. */
        if (method.getAnnotation(Repeat.class) == null) {
            /* set testName for listener */
            listener.setTestName(description.getMethodName());
            /* run once */
            runLeaf(methodBlock(method), description, notifier);
            /* remove listener */
            notifier.removeListener(listener);
            return;
        }

        /* get repetition count */
        int count = method.getAnnotation(Repeat.class).count();

        /* if count < 1, throw Exception */
        try {
            this.checkRepetitionCount(count);
        } catch (Exception e) {
            logger.error("Error:" +e.getMessage());
            logger.info("=============Repetition Test Finished=============");
            notifier.removeListener(listener);
            return;
        }

        /* set testName */
        String testName = "";
        if (method.getAnnotation(Repeat.class).testName().isEmpty()) {
            testName = method.getName();
        } else {
            testName = method.getAnnotation(Repeat.class).testName();
        }
        listener.setTestName(testName);

        /* define retryMethodBlock inner func */
        String finalTestName = testName;
        Statement retryMethodBlock = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;
                int retryCount = method.getAnnotation(Repeat.class).retryCount();
                /* if retryCount == 0, no retry */
                if (retryCount == 0) {
                    methodBlock(method).evaluate();
                    return;
                }
                /* retry loop */
                for (int i = 0; i <= retryCount; i++) {
                    try {
                        methodBlock(method).evaluate();
                        return;
                    } catch (Throwable t) {
                        caughtThrowable = t;
                        if (i != retryCount) {
                            logger.error(finalTestName + " unit test failed with "+t.getMessage());
                            logger.error(finalTestName + " unit test rerun " + (i+1));
                        }
                    }
                }
                logger.error(finalTestName+ " unit test giving up after " + retryCount + " retry");
                throw caughtThrowable;
            }
        };

        /* repeat unit test */
        for (int i =0; i<count; ++i) {
            runLeaf(retryMethodBlock, description, notifier);
        }

        /* log repetition test summary with RepeatTestInfo(totalCount, passCount, failCount) */
        this.logRepetitionUnitTestResult(listener, testName);

        /* remove listener */
        notifier.removeListener(listener);

        logger.info("=============Repetition Test Finished=============");
    }

    private void logRepetitionUnitTestResult(RepeatRunListener listener, String testName) {
        StringBuffer logMsg = new StringBuffer("");

        logMsg.append("RepetitionTest: ")
                .append(testName)
                .append(", total_count: ")
                .append(listener.getTotalCount(testName))
                .append(", pass_count: ")
                .append(listener.getPassCount(testName))
                .append(", fail_count: ")
                .append(listener.getFailCount(testName));
        logger.info(String.valueOf(logMsg));
    }

    private void checkRepetitionCount(int count) throws Exception {
        if (count < 1) {
            throw new Exception("count값은 1 이상이어야 합니다.");
        }
    }
}
