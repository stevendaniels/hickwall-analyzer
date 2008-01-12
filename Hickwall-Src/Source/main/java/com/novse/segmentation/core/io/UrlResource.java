/*
 * @作者:Mac Kwan , 创建日期:2007-12-20
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Mac Kwan 统一资源路径下的资源
 */
public class UrlResource implements Resource
{
    /**
     * 统一资源路径
     */
    private URL url = null;

    /**
     * 以字符串形式的统一资源路径为参数的构造函数
     * 
     * @param path
     *            字符串形式的统一资源路径
     */
    public UrlResource(String path)
    {
        try
        {
            this.url = new URL(path);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 以统一资源路径实例为参数的构造函数
     * 
     * @param url
     *            统一资源路径实例
     */
    public UrlResource(URL url)
    {
        this.url = url;
    }

    /**
     * 返回指定统一资源路径下资源的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return this.url.openStream();
    }

}
