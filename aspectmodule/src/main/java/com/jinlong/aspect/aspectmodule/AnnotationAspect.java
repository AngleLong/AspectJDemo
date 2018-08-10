package com.jinlong.aspect.aspectmodule;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author : 贺金龙
 * email : 753355530@qq.com
 * create at 2018/8/10  16:30
 * description : 注解的切面类
 */
@Aspect
public class AnnotationAspect {

    public static final String TAG = "hjl";

    @Pointcut("execution(@com.jinlong.aspectjdemo.DebugTrace * *(..))")
    public void DebugTraceMethod() {
    }

    @Before("DebugTraceMethod()")
    public void beforeDebugTraceMethod(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解这个方法执行了: ");
    }
}
