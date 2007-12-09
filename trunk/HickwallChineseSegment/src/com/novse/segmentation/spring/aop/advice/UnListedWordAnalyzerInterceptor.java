/*
 * @作者:Hades , 创建日期:May 20, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring.aop.advice;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.novse.segmentation.core.unlistedword.UnListedWordAnalyzer;

/**
 * @author Mac Kwan 未登录词识别Aspect实现类
 */
public class UnListedWordAnalyzerInterceptor implements MethodInterceptor
{
    /**
     * 未登录词识别器
     */
    private UnListedWordAnalyzer analyzer = null;

    /**
     * @param analyzer
     *            未登录词识别器
     */
    public UnListedWordAnalyzerInterceptor(UnListedWordAnalyzer analyzer)
    {
        this.analyzer = analyzer;
    }

    /**
     * 初步分词后切入未登录词识别
     */
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {
        // 记录执行结果
        Object result = null;
        // 执行方法
        result = methodInvocation.proceed();
        // 未登录词识别
        if (analyzer != null)
            result = analyzer.identify((List<String>) result);
        return result;
    }

}
