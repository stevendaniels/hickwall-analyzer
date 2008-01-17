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
