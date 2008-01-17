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
