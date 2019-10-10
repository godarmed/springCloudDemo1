package com.springcloud.global.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class TypeUtils {

    private TypeUtils() { }

    /**
     * 获取特定类型的List的参数化类型
     */
    public static Type getListType(Class clazz,String fieldName) {
        Type listType = null;
        Field listField = null;
        try {
            listField = clazz.getDeclaredField(fieldName);
            if(listField != null){
                listType = listField.getGenericType();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return listType;
    }
}
