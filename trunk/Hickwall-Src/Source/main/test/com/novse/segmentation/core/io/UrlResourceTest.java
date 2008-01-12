/*
 * @作者:Mac Kwan , 创建日期:2007-12-22
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.net.URL;

/**
 * @author Mac Kwan 统一资源路径资源类单元测试
 */
public class UrlResourceTest extends ResourceTest
{
    @Override
    protected Resource getResource()
    {
        URL url = this.getClass().getResource("/file/ResourceTest.txt");
        return new UrlResource(url);
    }

}
