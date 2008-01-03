/*
 * @����:Mac Kwan , ��������:2007-12-20
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Mac Kwan �ļ���Դ
 */
public class FileResource implements Resource
{
    /**
     * �ļ���
     */
    private File file = null;

    /**
     * ���ļ�·��Ϊ�����Ĺ��캯��
     * 
     * @param path
     *            �ļ�·��
     */
    public FileResource(String path)
    {
        this.file = new File(path);
    }

    /**
     * ���ļ���ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param file
     *            �ļ���ʵ��
     */
    public FileResource(File file)
    {
        this.file = file;
    }

    /**
     * ����ָ���ļ���Դ��������
     */
    public InputStream getInputStream() throws Exception
    {
        return new FileInputStream(this.file);
    }

}
