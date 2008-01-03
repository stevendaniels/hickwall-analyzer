/*
 * @作者:Mac Kwan , 创建日期:2007-12-22
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.util.Random;

import com.novse.segmentation.core.io.ClassPathResource;
import com.novse.segmentation.core.io.Resource;

/**
 * @author Mac Kwan 类路径下资源单元测试
 */
public class ClassPathResourceTest extends ResourceTest
{

    @Override
    protected Resource getResource()
    {
        // 随机数
        Random random = new Random();
        double num = random.nextDouble();

        if (num < 0.3)
            return new ClassPathResource("/file/ResourceTest.txt", this.getClass());
        else if (num < 0.6)
            return new ClassPathResource("file/ResourceTest.txt", this.getClass()
                    .getClassLoader());
        else
            return new ClassPathResource("/file/ResourceTest.txt", this.getClass(),
                    this.getClass().getClassLoader());
    }

}
