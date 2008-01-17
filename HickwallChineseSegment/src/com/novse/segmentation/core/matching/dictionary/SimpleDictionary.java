/*
 * @����:Hades , ��������:2006-11-18
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan ��˳��ʵ������
 */
public class SimpleDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -6631832710612755332L;

    /**
     * �ʵ�����
     */
    private ArrayList<String> dic = null;

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    public void deleteWord(String word)
    {
        // ��ʼ���ʵ�
        if (this.dic == null)
            return;

        if (word == null || StringUtils.isBlank(word))
            return;
        int pos;
        // �ж�ԭ�ʵ����Ƿ����иôʻ�
        if ((pos = Collections.binarySearch(dic, word)) < 0)
            return;
        else
            dic.remove(pos);
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    public void insertWord(String word)
    {
        // ��ʼ���ʵ�
        if (this.dic == null)
            dic = new ArrayList<String>();

        if (word == null || StringUtils.isBlank(word))
            return;
        // �ж�ԭ�ʵ����Ƿ����иôʻ�
        if (Collections.binarySearch(dic, word) < 0)
            dic.add(word);
        // �������������
        Collections.sort(dic);
    }

    /**
     * �������ı���ʽ�洢�Ĵʵ�
     * 
     * @param fileName
     *            �ʵ���ļ���
     */
    public void loadDictionary(String fileName)
    {
        // ��ʼ���ʵ�����
        if (this.dic != null)
            this.dic.clear();
        this.dic = new ArrayList<String>();

        try
        {
            // ��ʼ��������
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String word = null;

            // ��ȡ�ʵ�
            while ((word = in.readLine()) != null)
            {
                // ����ʻ�
                if (!StringUtils.isBlank(word))
                    dic.add(word.trim());
            }
            // �ʵ�����
            Collections.sort(dic);
            // �ر�����
            in.close();
        }
        catch (IOException e)
        {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
    }

    /**
     * �ж�������ַ����Ƿ��ڴʵ���
     * 
     * @param word
     *            ���ж��ַ���
     * @return �жϽ��
     */
    public boolean match(String word)
    {
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (dic == null)
            return false;

        if (word == null || StringUtils.isBlank(word))
            return false;

        int pos = Collections.binarySearch(dic, word);
        if (pos >= 0)
            return true;
        else
            return false;
    }

    /**
     * ����������ڴ������дʻ�
     * 
     * @param out
     *            �����
     */
    public void print(PrintStream out)
    {
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (dic == null)
            return;

        for (int i = 0; i < this.dic.size(); i++)
        {
            out.println(dic.get(i));
        }
        out.flush();
    }

}