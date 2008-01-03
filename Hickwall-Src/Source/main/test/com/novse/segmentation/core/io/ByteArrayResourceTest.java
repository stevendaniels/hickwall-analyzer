/*
 * @����:Mac Kwan , ��������:2007-12-22
 *
 * ��ͷ��ѧ03���������
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
 * @author Mac Kwan �ֽ�������Դ��Ԫ����
 */
public class ByteArrayResourceTest extends ResourceTest
{

    @Override
    protected Resource getResource()
    {
        byte[] result = null;
        try
        {
            //������
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(new File(this.getClass().getResource(
                            "/file/ResourceTest.txt").getFile())));
            //�����
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //����
            byte[] buffer = new byte[1024];
            //��������ֽ�
            int i;
            while((i=in.read(buffer))>0)
            {
                out.write(buffer, 0, i);
            }
            out.flush();
            
            //ת��Ϊ�ֽ�����
            result=out.toByteArray();
            
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }

        return new ByteArrayResource(result);
    }

}
