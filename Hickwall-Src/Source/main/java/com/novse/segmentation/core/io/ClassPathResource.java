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
 * @author Mac Kwan ��·���ļ���Դ
 */
public class ClassPathResource implements Resource
{
    /**
     * ��ӳ��
     */
    private Class clazz = null;

    /**
     * ��ӳ��������
     */
    private ClassLoader clazzLoader = null;

    /**
     * ��·��
     */
    private String classPath;

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param classPath
     *            ��·��
     * @param clazz
     *            ��ӳ��
     */
    public ClassPathResource(String classPath, Class clazz)
    {
        this.classPath = classPath;
        this.clazz = clazz;
    }

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param classPath
     *            ��·��
     * @param clazz
     *            ��ӳ��
     * @param clazzLoader
     *            ��ӳ��������
     */
    public ClassPathResource(String classPath, Class clazz,
            ClassLoader clazzLoader)
    {
        this.classPath = classPath;
        this.clazz = clazz;
        this.clazzLoader = clazzLoader;
    }

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param classPath
     *            ��·��
     * @param clazzLoader
     *            ��ӳ��������
     */
    public ClassPathResource(String classPath, ClassLoader clazzLoader)
    {
        this.classPath = classPath;
        this.clazzLoader = clazzLoader;
    }

    /**
     * ����ָ����·����Դ��������
     */
    public InputStream getInputStream() throws Exception
    {
        // �ж���·���Ƿ�Ϊ��
        if (this.classPath == null)
            return null;
        // �ж���ӳ���Ƿ�Ϊ��
        if (this.clazz != null)
            return this.clazz.getResourceAsStream(this.classPath);
        // �ж���ӳ���������Ƿ�Ϊ��
        else if (this.clazzLoader != null)
            return this.clazzLoader.getResourceAsStream(this.classPath);
        else
            return null;
    }

}
