/*
 * @����:Hades , ��������:May 22, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * @author Mac Kwan �쳣�׳�ʱ������־��¼��Aspect������
 */
public class LogThrowAdvice implements ThrowsAdvice
{
    /**
     * ��־������
     */
    private Logger logger = Logger.getLogger(LogThrowAdvice.class);

    /**
     * �쳣����ʱ������־��¼
     * 
     * @param method
     *            ����ʵ��
     * @param args
     *            ��������
     * @param target
     *            ����ʵ��
     * @param ex
     *            �쳣��Ϣ
     */
    public void afterThrowing(Method method, Object args[], Object target,
            Throwable ex)
    {
        // ��¼��־
        logger.log(Level.ERROR, method + "�������׳�" + ex + "�쳣");
    }
}
