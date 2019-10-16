package com.springcloud.global.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SetValuesUtil {

    private static final Integer arrayNum = 1;

    /**
     * 基本类型、包装类型、String类型
     */
    private static String[] basicTypes = {"java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String",
            "int","double","long","short","byte","boolean","char","float"};



    /**
     * 反射给对象的所有属性,包括继承的属性赋一个默认值
     *
     * @param model 要赋值的对象
     * @param <T>   要赋值的对象类型
     * @return 赋值后的对象
     * @throws Exception 可能的类型转换异常
     */
    public static <T> T setObjectReflect(T model) throws Exception {
        if (model instanceof String) {
            String str = "123";
            return (T) str;
        }
        //本类属性
        Field[] thisFields = model.getClass().getDeclaredFields();
        //父类属性
        Field[] superFields = model.getClass().getSuperclass().getDeclaredFields();
        //权限设置
        Field.setAccessible(thisFields, true);
        Field.setAccessible(superFields, true);
        //设置属性值
        setFieldReflect(model, thisFields, false);
        setFieldReflect(model, superFields, true);

        return model;
    }

    public static <T> T setFieldReflect(T model, Field[] fields, Boolean... isSuperFields) throws Exception {
        //属性赋值
        for (int i = 0; i < fields.length; i++) {
            String srcName = fields[i].getName();
            srcName = srcName.substring(0, 1).toUpperCase() + srcName.substring(1);
            String type = fields[i].getGenericType().toString();
            log.info("属性类型[{}]:[{}]", fields[i], type);

            //如果是一个字符串
            if (type.equals("class java.lang.String")) {
                Method m1 = getMethod(model, "set" + srcName, String.class, isSuperFields[0]);
                m1.invoke(model, "123");
            }
            //如果是一个整形
            if (type.equals("class java.lang.Integer")) {
                Method m1 = getMethod(model, "set" + srcName, Integer.class, isSuperFields[0]);
                m1.invoke(model, 1);
            }

            //如果是一个长整形
            if (type.equals("class java.lang.Long")) {
                Method m1 = getMethod(model, "set" + srcName, Long.class, isSuperFields[0]);
                m1.invoke(model, 1L);
            }

            //如果是一个List
            if (type.indexOf("java.util.List") != -1) {
                //获取泛型类型名称
                String typeName = fields[i].getGenericType().getTypeName();
                typeName = typeName.substring(typeName.indexOf("<") + 1, typeName.lastIndexOf(">"));
                //生成List
                Class clazz = Class.forName(typeName);
                List list = new ArrayList();
                list.add(setObjectReflect(clazz.newInstance()));
                Method m1 = getMethod(model, "set" + srcName, List.class, isSuperFields[0]);
                m1.invoke(model, list);
            }

            //如果是一个数组
            if (type.indexOf("class [L") != -1) {
                String arrayClassName = type.substring(type.indexOf("[L") + 2, type.lastIndexOf(";"));
                Class theClass = Class.forName(arrayClassName);
                Array array = (Array) Array.newInstance(theClass, arrayNum);
                for (int j = 0; j < arrayNum; j++) {
                    array.set(array, j, setObjectReflect(theClass.newInstance()));
                }
                Method m1 = getMethod(model, "set" + srcName, array.getClass(), isSuperFields[0]);
                m1.invoke(model, (Object) array);
            }
        }
        return model;
    }

    public static <T> Method getMethod(T model, String methodName, Class clazz, Boolean... isSuperMethod) throws NoSuchMethodException {
        isSuperMethod[0] = isSuperMethod[0] == null ? false : isSuperMethod[0];
        Method method = null;
        if (isSuperMethod[0]) {
            method = model.getClass().getSuperclass().getDeclaredMethod(methodName, clazz);
        } else {
            method = model.getClass().getDeclaredMethod(methodName, clazz);
        }
        return method;
    }

    //获取所有的String类型的属性
    public static <T> T setFieldsReflect(T model) throws Exception {
        if (model instanceof String) {
            String str = "123";
            return (T) str;
        }

        //返回结果
        List<String> fieldStrings = new ArrayList<>();
        //本类属性
        Field[] fields = model.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            String srcName = fields[i].getName();
            srcName = srcName.substring(0, 1).toUpperCase() + srcName.substring(1);
            String type = fields[i].getGenericType().toString();
            log.info("属性类型[{}]:[{}]", fields[i], type);

            //如果是一个字符串

            if (type.equals("class java.lang.String")) {
                Method m1 = getMethod(model, "get" + srcName, String.class, false);
                fieldStrings.add((String) m1.invoke(model));
            } else {

            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        //log.info(JSON.toJSONString(setObjectReflect(batchPortLogVO)));
        System.out.println(System.currentTimeMillis());
    }
}
