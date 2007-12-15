/*
 * @����:Hades , ��������:2007-1-25
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.lucene.analysis;

import java.util.Set;

import org.apache.lucene.analysis.StopFilter;

/**
 * @author Mac Kwan �ָ���������
 */
public class StopWordMaker
{
    /**
     * ���ӷָ���
     */
    private static final String APPEND_STRING = " \r\n�����������������������������������D�������£��졤�����򣣣���������������";

    /**
     * Ӣ�������ַ���
     */
    public static final String CHAR_AND_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz�����������������������£ãģţƣǣȣɣʣˣ̣ͣΣϣУѣңӣԣգ֣ףأ٣ڣ��������������������������������";

    /**
     * �����������ּ�Ӣ����ĸ���ķָ����ַ���
     */
    private static String seperator = null;

    /**
     * ���������ּ�Ӣ����ĸ���ķָ����ַ���
     */
    private static String seperatorWithCharAndNum = null;

    /**
     * �ָ������
     */
    private static Set stopWords = null;

    /**
     * ���ء����������ּ�Ӣ����ĸ���ָ�������
     * 
     * @return �ָ�������
     */
    public static Set retreiveSet()
    {
        if (stopWords == null)
        {
            // ��ʼ���ָ���
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                // ������Ӣ�ġ������ַ�
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
                buffer.append(c);
            buffer.append(APPEND_STRING);
            stopWords = StopFilter.makeStopSet(buffer.toString().trim().split(
                    ""));
        }
        return stopWords;
    }

    /**
     * ���ء����������ּ�Ӣ����ĸ���ķָ����ַ���
     * 
     * @return �����������ּ�Ӣ����ĸ���ķָ����ַ���
     */
    public static String retreiveString()
    {
        if (seperator == null)
        {
            // ��ʼ���ָ���
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                // ������Ӣ�ġ������ַ�
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            {
                // ������Ӣ�ġ������ַ�
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            buffer.append(APPEND_STRING);
            seperator = buffer.toString();
        }
        return seperator;
    }

    /**
     * ���ء��������ּ�Ӣ����ĸ���ķָ����ַ���
     * 
     * @return ���������ּ�Ӣ����ĸ���ķָ����ַ���
     */
    public static String retreiveStringWithNumberAndChar()
    {
        if (seperatorWithCharAndNum == null)
        {
            // ��ʼ���ָ���
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
                buffer.append(c);
            buffer.append(APPEND_STRING);
            seperatorWithCharAndNum = buffer.toString();
        }
        return seperatorWithCharAndNum;
    }
}
