package com.jinlong.aspect.aspectmodule;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author : 贺金龙
 * email : 753355530@qq.com
 * create at 2018/8/11 21:04
 * description : 解释一些相应通配符的内容
 */
@Aspect
public class TestAspect {

    private static final String TAG = "TestAspect";

//    @Pointcut("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
//    public void callMethod() {
//        //为了演示call方法的使用
//    }
//
//    @Before("callMethod()")
//    public void beforeCallMethod(JoinPoint joinPoint) {
//        Log.e(TAG, "call方法的演示");
//    }

    //上面代码可以简化为：
    @Before("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
    public void beforeCallMethod(JoinPoint joinPoint) {

    }

    @Around("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
    public void aroundCallMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        Log.e(TAG, "方法执行的时间为" + (endTime - startTime));
    }

    @Before("execution(com.jinlong.aspectjdemo.Person.new(..))")
    public void beforeConstructorExecution(JoinPoint joinPoint) {
        //这个是显示Constructor的
        Log.e(TAG, "before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }

//    @After("execution(* com.jinlong.aspectjdemo.Person(..))")
//    public void beforeConstructorExecution(JoinPoint joinPoint) {
//        //todo 这里还有点问题没有研究明白
//        Log.e(TAG, "before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }


    @Around("get(String com.jinlong.aspectjdemo.Person.age)")
    public String aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原代码
        Object obj = joinPoint.proceed();
        String age = obj.toString();
        Log.e(TAG, "age: " + age);
        return "100";
    }

    @Around("set(String com.jinlong.aspectjdemo.Person.age)&&!withincode(com.jinlong.aspectjdemo.Person.new(..))")
    public void aroundFieleSet(ProceedingJoinPoint joinPoint) throws Throwable {
        //设置相应的成员变量
        joinPoint.proceed();
    }

    @Before("handler(java.lang.Exception)")
    public void handlerMethod() {
        Log.e(TAG, "handlerMethod: 异常产生了");
    }
}
