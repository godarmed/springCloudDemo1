package com.springcloud.global.utils;

import java.lang.reflect.*;
import java.util.ArrayList;

class People<U, V, T> {
}

public class TypeTest<T> {
    private ArrayList<String> people;

    public static void main(String[] args) {
        Type t = null;
        try {
            t = TypeTest.class.getDeclaredField("people").getGenericType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ParameterizedType pt = (ParameterizedType) t;
        Type ft = pt.getRawType();
        Type[] types = pt.getActualTypeArguments();
        //外层
        printType(t.toString(), t);
        printType(ft.toString(), ft);
        //内层
        for (Type type : types) {
            printType(type.toString(), type);
        }
    }

    public static void printType(String name, Type type) {
        if (type instanceof Class) {
            System.out.println("the type of " + name + " is : Class");
        } else if (type instanceof ParameterizedType) {
            System.out.println("the type of " + name + " is : ParameterizedType");
        } else if (type instanceof GenericArrayType) {
            System.out.println("the type of " + name + " is : GenericArrayType");
        } else if (type instanceof TypeVariable) {
            System.out.println("the type of " + name + " is : TypeVariable");
        }
    }
}


