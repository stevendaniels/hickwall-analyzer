/*
 * @����:Hades , ��������:May 20, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.novse.segmentation.core.unlistedword.UnListedWordAnalyzer;

/**
 * @author Mac Kwan δ��¼��ʶ��Aspectʵ����
 */
public class UnListedWordAnalyzerInterceptor implements MethodInterceptor
{
    /**
     * δ��¼��ʶ����
     */
    private UnListedWordAnalyzer analyzer = null;

    /**
     * @param analyzer
     *            δ��¼��ʶ����
     */
    public UnListedWordAnalyzerInterceptor(UnListedWordAnalyzer analyzer)
    {
        this.analyzer = analyzer;
    }

    /**
     * �����ִʺ�����δ��¼��ʶ��
     */
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        // ��¼ִ�н��
        Object result = null;
        // ִ�з���
        result = methodInvocation.proceed();
        // δ��¼��ʶ��
        if (analyzer != null)
            result = analyzer.identify((List<String>) result);
        return result;
    }

}
