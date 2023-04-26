package com.backbase.utils;

import java.util.LinkedHashMap;
import java.util.Optional;

public class CommonTestData {

    private CommonTestData(){}

    private static ThreadLocal<LinkedHashMap<String,Object>> data=
            ThreadLocal.withInitial(()->new LinkedHashMap<>());

    public static Object getValue(String key) {

        return data.get().get(key);
    }

    public static void setValue(String key, Object value) {
        data.get().put(key,value);
    }

}