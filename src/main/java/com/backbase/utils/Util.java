package com.backbase.utils;

import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    public static Properties properties;
    public static Faker faker=new Faker();

    public static Properties loadPropertiesFile(String fileName){
        String fileFullName="src/test/resources/"+fileName;
        properties=new Properties();
        try {
            FileInputStream fileInputStream=new FileInputStream(fileFullName);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static int generateRandomID(){
        return Integer.valueOf(faker.number().digits(6));
    }

    public static String generateRandomTitle(){
        return faker.name().title();
    }

}
