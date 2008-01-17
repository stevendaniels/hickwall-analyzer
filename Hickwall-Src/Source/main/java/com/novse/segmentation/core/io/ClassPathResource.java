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
package com.novse.segmentation.core.io;

import java.io.InputStream;

/**
 * @author Mac Kwan 类路径文件资源
 */
public class ClassPathResource implements Resource
{
    /**
     * 类映射
     */
    private Class clazz = null;

    /**
     * 类映射载入器
     */
    private ClassLoader clazzLoader = null;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 默认构造函数
     * 
     * @param classPath
     *            类路径
     * @param clazz
     *            类映射
     */
    public ClassPathResource(String classPath, Class clazz)
    {
        this.classPath = classPath;
        this.clazz = clazz;
    }

    /**
     * 默认构造函数
     * 
     * @param classPath
     *            类路径
     * @param clazz
     *            类映射
     * @param clazzLoader
     *            类映射载入器
     */
    public ClassPathResource(String classPath, Class clazz,
            ClassLoader clazzLoader)
    {
        this.classPath = classPath;
        this.clazz = clazz;
        this.clazzLoader = clazzLoader;
    }

    /**
     * 默认构造函数
     * 
     * @param classPath
     *            类路径
     * @param clazzLoader
     *            类映射载入器
     */
    public ClassPathResource(String classPath, ClassLoader clazzLoader)
    {
        this.classPath = classPath;
        this.clazzLoader = clazzLoader;
    }

    /**
     * 返回指定类路径资源的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        // 判断类路径是否为空
        if (this.classPath == null)
            return null;
        // 判断类映射是否为空
        if (this.clazz != null)
            return this.clazz.getResourceAsStream(this.classPath);
        // 判断类映射载入器是否为空
        else if (this.clazzLoader != null)
            return this.clazzLoader.getResourceAsStream(this.classPath);
        else
            return null;
    }

}
