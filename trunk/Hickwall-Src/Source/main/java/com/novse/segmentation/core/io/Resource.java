/*
 * @����:Mac Kwan , ��������:2007-12-20
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.io;

import java.io.InputStream;

/**
 * @author Mac Kwan ��Դ�ӿ�
 */
public interface Resource
{
    /**
     * ��ȡָ����Դ��������
     * 
     * @return ָ����Դ��������
     * @throws Exception
     */
    public InputStream getInputStream() throws Exception;
}
