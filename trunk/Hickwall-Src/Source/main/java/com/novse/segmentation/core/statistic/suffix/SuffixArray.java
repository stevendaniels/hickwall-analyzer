/*
 * @����:Hades , ��������:2006-11-15
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.suffix;

/**
 * @author Mac Kwan �����ú�׺��������Ľӿ�
 */
public interface SuffixArray
{
    /**
     * ���ַ���str��׺����
     * 
     * @param str
     *            ��Ҫ������׺������ַ���
     * @return str��Ӧ�ĺ�׺����
     */
    public int[] getSuffixArray(String str);

    /**
     * ����str��Ӧ��׺������������׺���ǰ׺���ȵ�����
     * 
     * @param str
     *            ��Ҫ������׺������ַ���
     * @return ���str��Ӧ��׺������������׺���ǰ׺���ȵ�����
     */
    public int[] getLCP(String str);
}
