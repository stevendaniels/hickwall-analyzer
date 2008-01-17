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
