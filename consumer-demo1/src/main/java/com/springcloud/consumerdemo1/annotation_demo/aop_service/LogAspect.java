package com.springcloud.consumerdemo1.annotation_demo.aop_service;

import com.alibaba.fastjson.JSON;
import com.springcloud.consumerdemo1.annotation_demo.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component("LogAspect")
public class LogAspect {
    private final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Long> beginTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal beginTime");

    private static final ThreadLocal<Long> endTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal endTime");

    private static final ThreadLocal<String> className = new NamedThreadLocal<String>("class name");

    private static final ThreadLocal<String> methodName = new NamedThreadLocal<String>("method name");

    //织入点
    @Pointcut("@within(com.springcloud.consumerdemo1.annotation_demo.annotation.Log)" +
            "||@annotation(com.springcloud.consumerdemo1.annotation_demo.annotation.Log)")
    public void logPointCut(){}

    /**
     * 前置通知 用于拦截操作,在方法返回后执行
     */
    @Before(value = "logPointCut()")
    public void doBefore(JoinPoint joinPoint){
        doBeforeMethod(joinPoint);
        //获取启动时间,当前类和当前方法
        log.info("[前置通知][{}]类中的[{}]方法启动,开始时间：[{}]",className.get(),methodName.get(),new Date(beginTimeThreadLocal.get()));
    }

    /**
     * 后置通知 用于拦截操作,在方法返回后执行（先于返回通知或者返回异常通知）
     */
    @After(value = "logPointCut()")
    public void doAfter(JoinPoint joinPoint){
        endTimeThreadLocal.set(System.currentTimeMillis());
        log.info("[后置通知]:[{}]类中的[{}]方法结束,方法结束时间[{}],耗时[{}]毫秒",className.get(),methodName.get(),new Date(endTimeThreadLocal.get()),endTimeThreadLocal.get()-beginTimeThreadLocal.get());
    }

    /**
     * 返回通知 用于拦截操作,在方法返回后执行
     */
    @SuppressWarnings("unchecked")
    @AfterReturning(value = "logPointCut()",argNames = "joinPoint,result",returning = "result")
    public void doAfterReturn(JoinPoint joinPoint,Object result){
        Object[] object = joinPoint.getArgs();
        log.info("[返回通知]:[{}]类中的[{}]方法的返回为[{}]",className.get(),methodName.get(), JSON.toJSONString(result));
        doAfterMethod();
    }

    /**
     * 后置异常通知 用于拦截操作,在方法出现异常后执行
     */
    @AfterThrowing(value = "logPointCut()",throwing ="e")
    public void doAfterThrowing(JoinPoint joinPoint,Exception e){
        log.info("[后置异常通知]:[{}]类中的[{}]方法产生异常[{}]",className.get(),methodName.get(),e.getCause());
        doAfterMethod();
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private static Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 前置初始化方法
     */
    private void doBeforeMethod(JoinPoint joinPoint) {
        try {
            // 获得类名称
            String className = joinPoint.getTarget().getClass().getName();
            // 获得方法名称
            String methodName = joinPoint.getSignature().getName();;
            //打印日志，如有需要还可以存入数据库
            this.className.set(className);
            this.methodName.set(methodName);
            this.beginTimeThreadLocal.set(System.currentTimeMillis());
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 后置重置方法（防止线程复用造成错误）
     */
    private void doAfterMethod() {
        try {
            //清空所有ThreadLocal
            this.className.remove();
            this.methodName.remove();
            this.beginTimeThreadLocal.remove();
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==最终通知异常==异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

}
