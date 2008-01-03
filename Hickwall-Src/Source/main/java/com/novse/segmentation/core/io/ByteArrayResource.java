/*
 * @作者:Mac Kwan , 创建日期:2007-12-20
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Mac Kwan 字节数组资源
 */
public class ByteArrayResource implements Resource
{
    /**
     * 字节数组
     */
    private byte[] byteArray = null;

    /**
     * 以字节数组为参数的构造函数
     * 
     * @param byteArray
     *            字节数组
     */
    public ByteArrayResource(byte[] byteArray)
    {
        this.byteArray = byteArray;
    }

    /**
     * 返回指定字节数组的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return new ByteArrayInputStream(this.byteArray);
    }

}
