/*
 * @作者:Mac Kwan , 创建日期:2007-12-22
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.net.URL;

import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;

/**
 * @author Mac Kwan 文件资源类单元测试
 */
public class FileResourceTest extends ResourceTest
{
    @Override
    protected Resource getResource()
    {
        URL url = this.getClass().getResource("/file/ResourceTest.txt");
        return new FileResource(url.getFile());
    }

}
