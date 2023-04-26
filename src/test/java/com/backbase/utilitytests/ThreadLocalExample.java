package com.backbase.utilitytests;

import com.backbase.utils.CommonTestData;

import java.util.HashMap;

public class ThreadLocalExample {
    public static void main(String[] args) {
        CommonTestData.setValue("name","rajendra");
        CommonTestData.setValue("age",39);
        System.out.println(CommonTestData.getValue("name"));
        System.out.println(CommonTestData.getValue("age"));
    }
}
