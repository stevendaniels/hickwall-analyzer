/*
 * @作者:Mac Kwan , 创建日期:2007-12-22
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.novse.segmentation.core.io.ByteArrayResource;
import com.novse.segmentation.core.io.Resource;

/**
 * 
 * @author Mac Kwan 字节数组资源单元测试
 */
public class ByteArrayResourceTest extends ResourceTest
{

    @Override
    protected Resource getResource()
    {
        byte[] result = null;
        try
        {
            //输入流
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(new File(this.getClass().getResource(
                            "/file/ResourceTest.txt").getFile())));
            //输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //缓冲
            byte[] buffer = new byte[1024];
            //读入多少字节
            int i;
            while((i=in.read(buffer))>0)
            {
                out.write(buffer, 0, i);
            }
            out.flush();
            
            //转换为字节数组
            result=out.toByteArray();
            
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }

        return new ByteArrayResource(result);
    }

}
