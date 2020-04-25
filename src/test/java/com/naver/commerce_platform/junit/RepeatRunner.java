package com.naver.commerce_platform.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
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

        RunListener listener = new RepeatRunListener();
        notifier.addListener(listener);

        Description description = describeChild(method);
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
            return;
        }
        // Repeat Annotation이 없을 경우 1번만 실행
        if (method.getAnnotation(Repeat.class) == null) {
            runLeaf(methodBlock(method), description, notifier);
            return;
        } else {
            int count = method.getAnnotation(Repeat.class).count();
            if (count < 1) {
                return; // throw is needed
            }
            for (int i =0; i<count; ++i) {
                runLeaf(methodBlock(method), description, notifier);
            }
        }

        // RepeatTestInfo(totalCount, passCount, failCount)를 가져와 결과 요약 로그 작성
        Class<?> cls = null;
        try {
            cls = Class.forName("com.naver.commerce_platform.junit."+"RepeatRunListener");
            Object obj = cls.getDeclaredConstructor().newInstance();
            Method getTotalCount = cls.getMethod("getTotalCount", String.class);
            Method getPassCount = cls.getMethod("getPassCount", String.class);
            Method getFailCount = cls.getMethod("getFailCount", String.class);
            String testName = "";
            StringBuffer logMsg = new StringBuffer("");

            if (method.getAnnotation(Repeat.class).testName().isEmpty()) {
                testName = method.getName();
            } else {
                testName = method.getAnnotation(Repeat.class).testName();
            }

            logMsg.append("RepetitionTest: ")
                    .append(testName)
                    .append(", total_count: ")
                    .append(getTotalCount.invoke(obj,method.getName()))
                    .append(", pass_count: ")
                    .append(getPassCount.invoke(obj,method.getName()))
                    .append(", fail_count: ")
                    .append(getFailCount.invoke(obj,method.getName()));
            logger.info(String.valueOf(logMsg));

            //remove Listener
            notifier.removeListener(listener);

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
