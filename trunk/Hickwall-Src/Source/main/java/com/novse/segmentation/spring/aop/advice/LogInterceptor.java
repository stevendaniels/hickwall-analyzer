/*
 * @����:Hades , ��������:May 22, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Mac Kwan ����ִ��ʱ������־��¼��Aspectʵ����
 */
public class LogInterceptor implements MethodInterceptor
{
    /**
     * ��־��¼������
     */
    private Logger logger = Logger.getLogger(LogInterceptor.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        logger.log(Level.INFO, methodInvocation.getMethod() + "����ִ��֮ǰ");

        // ִ�з���
        Object result = methodInvocation.proceed();

        logger.log(Level.INFO, methodInvocation.getMethod() + "����ִ��֮��");
        return result;
    }

}
