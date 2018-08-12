package com.jinlong.aspectjdemo;

/**
 * 文 件 名: Person
 * 创 建 人: 贺金龙
 * 创建日期: 2018/8/11 21:47
 * 邮   箱: 753355530@qq.com
 * 修改时间：
 * 修改备注：
 */
public class Person {
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
