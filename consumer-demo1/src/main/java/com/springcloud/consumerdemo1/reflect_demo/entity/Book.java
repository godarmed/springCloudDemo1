package com.springcloud.consumerdemo1.reflect_demo.entity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;

@Slf4j
public class Book {
    String name;    //图书名称
    int id,price;    //图书编号和价格
    //空的构造方法
    private Book(){}
    //带两个参数的构造方法
    protected Book(String _name,int _id)
    {
        this.name=_name;
        this.id=_id;
    }
    //带可变参数的构造方法
    public Book(String...strings)throws NumberFormatException
    {
        if(0<strings.length)
            id=Integer.valueOf(strings[0]);
        if(1<strings.length)
            price=Integer.valueOf(strings[1]);
    }
    //输出图书信息
    public void print()
    {
        System.out.println("name="+name);
        System.out.println("id="+id);
        System.out.println("price="+price);
    }


    public static void main(String[] args) {
        //获取动态类
        Class book = Book.class;
        //获取Book类的所有构造方法
        Constructor[] declaredConstructors = book.getDeclaredConstructors();
        //遍历构造方法
        int i = 0;
        for (Constructor declaredConstructor : declaredConstructors) {
            Constructor con = declaredConstructor;
            //判断构造方法的参数是否可变
            log.info("构造方法[{}]是否允许带可变数量的参数：[{}]",con.getName(),con.isVarArgs());
            //参看构造方法类型
            Class[] parameterTypes = con.getParameterTypes();
            log.info("构造方法[{}]的入口参数类型依次为[{}]",con.getName(), JSON.toJSONString(parameterTypes));
            //获取所有可能拋出的异常类型
            Class[] exceptionTypes=con.getExceptionTypes();
            log.info("构造方法[{}]可能拋出的异常类型类型依次为[{}]",con.getName(), JSON.toJSONString(exceptionTypes));

            //创建一个未实例化的Book类实例
            Book book1=null;
            while(book1==null)
            {
                try
                {    //如果该成员变量的访问权限为private，则拋出异常
                    if(i==1)
                    {
                        //通过执行带两个参数的构造方法实例化book1
                        book1=(Book)con.newInstance("Java 教程",10);
                    }
                    else if(i==2)
                    {
                        //通过执行默认构造方法实例化book1
                        book1=(Book)con.newInstance();
                    }
                    else
                    {
                        //通过执行可变数量参数的构造方法实例化book1
                        Object[] parameters=new Object[]{new String[]{"100","200"}};
                        book1=(Book)con.newInstance(parameters);
                    }
                }
                catch(Exception e)
                {
                    log.info("在创建对象时拋出异常，下面执行 setAccessible() 方法");
                    con.setAccessible(true);    //设置允许访问 private 成员
                }
                book1.print();
                System.out.println("=============================\n");
                i++;
            }
        }
    }
}