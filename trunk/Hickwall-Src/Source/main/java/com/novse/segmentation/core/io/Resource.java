/*
 * @作者:Mac Kwan , 创建日期:2007-12-20
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.InputStream;

/**
 * @author Mac Kwan 资源接口
 */
public interface Resource
{
    /**
     * 获取指定资源的输入流
     * 
     * @return 指定资源的输入流
     * @throws Exception
     */
    public InputStream getInputStream() throws Exception;
}
