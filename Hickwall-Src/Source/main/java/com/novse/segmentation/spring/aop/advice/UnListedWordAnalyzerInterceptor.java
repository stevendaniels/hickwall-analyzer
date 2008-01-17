/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
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
