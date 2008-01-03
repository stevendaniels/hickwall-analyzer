/*
 * @����:Mac Kwan , ��������:2007-12-20
 *
 * ��ͷ��ѧ03���������
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
