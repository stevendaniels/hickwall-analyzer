/*
 * @����:Mac Kwan , ��������:2007-12-22
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.io;

import java.util.Random;

import com.novse.segmentation.core.io.ClassPathResource;
import com.novse.segmentation.core.io.Resource;

/**
 * @author Mac Kwan ��·������Դ��Ԫ����
 */
public class ClassPathResourceTest extends ResourceTest
{

    @Override
    protected Resource getResource()
    {
        // �����
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
