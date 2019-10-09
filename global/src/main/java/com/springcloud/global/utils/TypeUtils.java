package com.springcloud.global.utils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeUtils<T> {

    private List<T> list;

    /**
     * 获取特定类型的List的参数化类型
     */
    public Type getListType(){
        Type listType = null;
        try {
            listType = TypeUtils.class.getDeclaredField("list").getGenericType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return listType;
    }

    public static void main(String[] args) {
        String str = "aaa,bbb,aa,cc,aaa,aaa,aaa";
        List<String> sourceList = Arrays.asList(str.split(","));
        List<String> targetList = sourceList.stream().distinct().collect(Collectors.toList());
        System.out.println(sourceList.size() > targetList.size());
    }

}
