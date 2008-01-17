/*
 * @����:Mac Kwan , ��������:2007-12-22
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.io;

import java.net.URL;

/**
 * @author Mac Kwan �ļ���Դ�൥Ԫ����
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