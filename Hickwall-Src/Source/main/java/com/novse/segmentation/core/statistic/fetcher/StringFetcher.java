/*
 * @����:Hades , ��������:2007-4-2
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.util.List;

/**
 * @author Mac Kwan �ʻ��ȡ���ӿ�
 */
public interface StringFetcher
{
    /**
     * �Ӵ������ļ�srcFile�г�ȡ�ʻ�
     * 
     * @param srcFile
     *            �������ļ�
     * @return ��ȡ���ôʻ�
     */
    public List<String> fileFetch(String srcFile);

    /**
     * �Ӵ������ַ���doc�г�ȡ�ʻ�
     * 
     * @param doc
     *            �������ַ���
     * @return ��ȡ���ôʻ�
     */
    public List<String> textFetch(String doc);
}
