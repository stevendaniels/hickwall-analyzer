/*
 * @作者:Hades , 创建日期:May 22, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * @author Mac Kwan 异常抛出时进行日志记录的Aspect操作类
 */
public class LogThrowAdvice implements ThrowsAdvice
{
    /**
     * 日志操作类
     */
    private Logger logger = Logger.getLogger(LogThrowAdvice.class);

    /**
     * 异常发生时进行日志记录
     * 
     * @param method
     *            方法实例
     * @param args
     *            方法参数
     * @param target
     *            对象实例
     * @param ex
     *            异常信息
     */
    public void afterThrowing(Method method, Object args[], Object target,
            Throwable ex)
    {
        // 记录日志
        logger.log(Level.ERROR, method + "方法内抛出" + ex + "异常");
    }
}
