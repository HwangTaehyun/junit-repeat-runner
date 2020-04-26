package com.naver.commerce_platform.junit.utils.runner;

import com.naver.commerce_platform.junit.test.CalculatorTest;
import com.naver.commerce_platform.junit.utils.annotation.Repeat;
import com.naver.commerce_platform.junit.utils.listener.RepeatRunListener;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RepeatRunner extends BlockJUnit4ClassRunner {
    static final Logger logger =
            LoggerFactory.getLogger(CalculatorTest.class);
    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        logger.info("=============Repetition Test Start=============");
        /* add listener */
        RepeatRunListener listener = new RepeatRunListener();
        notifier.addListener(listener);

        /* handle ignore Annot. */
        Description description = describeChild(method);
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

        /* if count < 1, throw IllegalArgumentException */
        if (count < 1) {
            try {
                throw new IllegalArgumentException("count값은 1 이상이어야 합니다.");
            } catch (IllegalArgumentException e) {
                logger.error("Error:" +e.getMessage());
            } finally {
                /* remove listener */
                notifier.removeListener(listener);
                logger.info("=============Repetition Test Finished=============");
                return;
            }
        }

        /* set testName */
        String testName = "";
        if (method.getAnnotation(Repeat.class).testName().isEmpty()) {
            testName = method.getName();
        } else {
            testName = method.getAnnotation(Repeat.class).testName();
        }
        listener.setTestName(testName);

        /* repeat unit test */
        for (int i =0; i<count; ++i) {
            runLeaf(methodBlock(method), description, notifier);
        }

        /* log repetition test summary with RepeatTestInfo(totalCount, passCount, failCount) */
        Class<?> cls = null;
        try {
            cls = Class.forName("com.naver.commerce_platform.junit.utils.listener."+"RepeatRunListener");
            Object obj = cls.getDeclaredConstructor().newInstance();
            Method getTotalCount = cls.getMethod("getTotalCount", String.class);
            Method getPassCount = cls.getMethod("getPassCount", String.class);
            Method getFailCount = cls.getMethod("getFailCount", String.class);
            StringBuffer logMsg = new StringBuffer("");

            logMsg.append("RepetitionTest: ")
                    .append(testName)
                    .append(", total_count: ")
                    .append(getTotalCount.invoke(obj,testName))
                    .append(", pass_count: ")
                    .append(getPassCount.invoke(obj,testName))
                    .append(", fail_count: ")
                    .append(getFailCount.invoke(obj,testName));
            logger.info(String.valueOf(logMsg));

            /* remove listener */
            notifier.removeListener(listener);

            logger.info("=============Repetition Test Finished=============");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
