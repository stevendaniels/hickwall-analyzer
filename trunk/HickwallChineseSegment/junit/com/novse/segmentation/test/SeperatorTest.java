/*
 * @����:Hades , ��������:2006-11-19
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.test;

/**
 * @author Mac Kwan
 * 
 */
public class SeperatorTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO �Զ����ɷ������
        String charAndNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // ��ʼ���ָ���
        StringBuffer buffer = new StringBuffer();
        for (char c = '\u0000'; c <= '\u007F'; c++)
        {
            if (charAndNum.indexOf(c) < 0)
                buffer.append(c);
        }
        for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            buffer.append(c);
        // buffer.append("�����������������������������������D�������£��졤�����򣣣������������� ");
        System.out.println(buffer.toString());
    }

}
