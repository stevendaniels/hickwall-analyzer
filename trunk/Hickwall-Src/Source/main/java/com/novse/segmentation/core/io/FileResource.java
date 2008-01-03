/*
 * @作者:Mac Kwan , 创建日期:2007-12-20
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Mac Kwan 文件资源
 */
public class FileResource implements Resource
{
    /**
     * 文件类
     */
    private File file = null;

    /**
     * 以文件路径为参数的构造函数
     * 
     * @param path
     *            文件路径
     */
    public FileResource(String path)
    {
        this.file = new File(path);
    }

    /**
     * 以文件类实例为参数的构造函数
     * 
     * @param file
     *            文件类实例
     */
    public FileResource(File file)
    {
        this.file = file;
    }

    /**
     * 返回指定文件资源的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return new FileInputStream(this.file);
    }

}
