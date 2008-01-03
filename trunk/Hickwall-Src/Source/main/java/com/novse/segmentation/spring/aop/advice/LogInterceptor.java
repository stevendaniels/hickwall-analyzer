/*
 * @作者:Hades , 创建日期:May 22, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Mac Kwan 方法执行时进行日志记录的Aspect实现类
 */
public class LogInterceptor implements MethodInterceptor
{
    /**
     * 日志记录操作类
     */
    private Logger logger = Logger.getLogger(LogInterceptor.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        logger.log(Level.INFO, methodInvocation.getMethod() + "方法执行之前");

        // 执行方法
        Object result = methodInvocation.proceed();

        logger.log(Level.INFO, methodInvocation.getMethod() + "方法执行之后");
        return result;
    }

}
