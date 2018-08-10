package com.jinlong.aspect.aspectmodule;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author : 贺金龙
 * email : 753355530@qq.com
 * create at 2018/8/10  15:09
 * description :
 */
@Aspect
public class TraceAspect {

    private static final String TAG = "hjl";

    @Before("execution(* android.app.Activity.on*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: 切面的点执行了！" + key);
    }

    @Around("execution(* com.jinlong.aspectjdemo.MainActivity.testApp(..))")
    public void onActivityMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String key = proceedingJoinPoint.getSignature().toString();

        //这句代码的含义就是执行了这些方法
        //@Around 会替换原先执行的代码，但如果你仍然希望执行原先的代码，可以使用joinPoint.proceed()。
        proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        Log.e(TAG, "方法用时: " + (endTime - startTime));
    }

    @After("call(* com.jinlong.aspectjdemo.MainActivity.onToast(..))")
    public void onCallBefore(JoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "方法结束时 ");
    }
}
