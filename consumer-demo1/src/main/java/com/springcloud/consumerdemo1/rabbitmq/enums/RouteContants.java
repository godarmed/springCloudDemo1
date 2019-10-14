package com.springcloud.consumerdemo1.rabbitmq.enums;

public interface RouteContants {
    //送达国家位置
    interface COUNTRY{
        Integer USA_NEWS = 2;
        Integer USA_WEATHER = 3;
        Integer EUROPE_NEWS = 4;
        Integer EUROPE_WEATHER = 5;
        Integer CN_NEWS = 6;
        Integer CN_WEATHER = 7;
    }

}
