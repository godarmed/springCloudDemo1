package com.springcloud.providerdemo1.stream_demo.service;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {
    public static void main(String[] args) {
        //switchStream();
        streamMapTest();
    }

    /*
    * switch Stream
    * */
    public static void switchStream(){
        /*
        * other to Stream
        * */
        //1.Individual values
        Stream<String> stream = Stream.of("a","b","c");
        stream.forEach(System.out::print);
        //2.Arrays
        String[] strArray = new String[]{"a","b","c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        //3.Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        /*
        * Stream to other
        * */
        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();
    }

    //map/flatMap使用
    private static List<String> wordList = new ArrayList<>(Arrays.asList(new String[]{"Leo","zzy","start","中文测试"}));
    private static List<Integer> numList = new ArrayList<>(Arrays.asList(new Integer[]{3,2,7,5,8}));
    public static void streamMapTest(){
        //字符串测试
        List<String> output = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());
        log.info("字符串map测试[{}]",output);

        //数字测试
        List<Integer> squareNums = numList.stream().
                filter(n->n>1). //过滤
                sorted(Comparator.comparing(Integer::intValue).reversed()).       //正序排序
                map(n->n*n).    //重新赋值
                collect(Collectors.toList());
        log.info("数字map测试[{}]",squareNums);
    }
}
