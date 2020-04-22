package com.naver.commerce_platform.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class RepeatRunner extends BlockJUnit4ClassRunner {
    public RepeatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        System.out.println("invoking: " + method.getName());
        return super.methodInvoker(method, test);
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {

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
    }



}
