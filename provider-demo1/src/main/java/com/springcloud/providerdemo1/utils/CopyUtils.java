package com.springcloud.providerdemo1.utils;

import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class CopyUtils {
    public static Object copy(Object source) throws Exception {

        Object o = null;
        Class clazz = source.getClass();
        if(!clazz.equals(ServletRequestAttributes.class)){
            o = clazz.newInstance();
        }else{
            o = new ServletRequestAttributes(((ServletRequestAttributes)source).getRequest());
        }
        while (clazz != Object.class) {

            Field fields[] = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()))
                    continue;
                field.setAccessible(true);
                Object value = field.get(source);
                // 基本类型
                if (field.getType().isPrimitive()) {
                    field.set(o, value);
                }
                // 数组类型  因为数组类型也是 Object 的实例, 所以写在前面
                else if (field.getType().isArray()) {
                    field.set(o, operateArray(value));
                }
                // 不为null的对象
                else if (value instanceof Object) {
                    field.set(o, copy(value));
                }
                field.setAccessible(false);
            }
            clazz = clazz.getSuperclass();
        }
        return o;
    }

    // 一维数组, 二维
    public static Object operateArray(Object array) throws Exception {
        // 1. 数组不为null, 2. 数组长度 >= 0
        if (array == null)
            return null;

        int length = Array.getLength(array);
        Class componentType = array.getClass().getComponentType();

        // 基本类型 + String 类型, 因为String 类型的值是不变的, 所以采用等值
        if (componentType.isPrimitive() || componentType == String.class) {
            Object xxx = returnPrimitive(array, length);
            return xxx;
        }
        // 保证长度 > 0
        if (componentType.isArray()) {
            // 固定长度的多维数组
            Object value = Array.newInstance(array.getClass().getComponentType(), Array.getLength(array));
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Array.set(value, i, operateArray(Array.get(array, i)));
            }
            return value;
        } else {
            Object o = null;
            o = Array.newInstance(componentType, length);
            for (int i = 0; i < length; i++) {
                Object value = copy(Array.get(array, i));
                Array.set(o, i, value);
            }
            return o;
        }
    }

    public static Object returnPrimitive(Object array, int length) {
        Class componentType = array.getClass().getComponentType();
        if (componentType == int.class)
            return Arrays.copyOf((int[]) array, length);
        if (componentType == double.class)
            return Arrays.copyOf((double[]) array, length);
        if (componentType == float.class)
            return Arrays.copyOf((float[]) array, length);
        if (componentType == long.class)
            return Arrays.copyOf((int[]) array, length);
        if (componentType == boolean.class)
            return Arrays.copyOf((boolean[]) array, length);
        if (componentType == byte.class)
            return Arrays.copyOf((byte[]) array, length);
        if (componentType == short.class)
            return Arrays.copyOf((short[]) array, length);
        if (componentType == char.class)
            return Arrays.copyOf((char[]) array, length);
        if (componentType == String.class)
            return Arrays.copyOf((String[]) array, length);

        return null;
    }
}
