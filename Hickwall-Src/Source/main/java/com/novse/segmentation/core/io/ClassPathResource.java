/*
 * @作者:Mac Kwan , 创建日期:2007-12-20
 *
 * 汕头大学03计算机本科
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
