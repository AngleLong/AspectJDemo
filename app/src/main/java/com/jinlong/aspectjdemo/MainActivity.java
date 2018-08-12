package com.jinlong.aspectjdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hjl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mothod1();

        //为了显示call方法的内容
        callMethod();
        //演示相应的构造方法的
        constructorExecution();
        //catch的演示
        catchMethod();
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/8/10  16:28
     * description : 方法1
     */
    @DebugTrace
    private void mothod1() {
        Log.e(TAG, "方法1执行了");
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/8/10  16:29
     * description : 点击事件
     */
    public void testApp(View view) {
        int result = 0;
        for (int i = 1; i <= 100; i++) {
            result += i;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onToast();

        Log.e(TAG, "计算结果" + result);
    }


    private void onToast() {
        Log.e(TAG, "弹出Toast");
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/8/11 21:49
     * description : 演示相应的构造方法的
     */
    private void constructorExecution() {
        Person person = new Person("张三", "20");
        Log.e(TAG, "constructorExecution: " + person.getAge());
        person.setAge("30");
        Log.e(TAG, "constructorExecution: " + person.getAge());
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/8/11 21:03
     * description : 为了显示call方法的内容
     */
    private void callMethod() {
        Log.e(TAG, "callMethod()方法执行了");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/8/12 12:00
     * description :  演示相应的catch方法
     */
    private void catchMethod() {
        try {
            int sum = 100 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
