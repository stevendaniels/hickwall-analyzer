/*
 * @����:Hades , ��������:May 15, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.unlistedword.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.matching.dictionary.AbstractDictionary;

/**
 * @author Mac Kwan �������ϴʵ������
 */
public class ChineseFirstNameDictionary extends AbstractDictionary implements
        Serializable
{

    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 860295715037663706L;

    /**
     * �ʵ�ʵ��
     */
    private HashSet<String> dic = null;

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
        if (word.length() > 2)
            return;
        // ɾ������
        this.dic.remove(word);
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    public void insertWord(String word)
    {
        if (this.dic == null)
            this.dic = new HashSet<String>();
        if (word == null || StringUtils.isBlank(word))
            return;
        if (word.length() > 2)
            return;
        // ��������
        this.dic.add(word);
    }

    /**
     * �������ı���ʽ�洢�Ĵʵ�
     * 
     * @param fileName
     *            �ʵ���ļ���
     */
    public void loadDictionary(String fileName)
    {
        // ��ʼ���ʵ�ʵ��
        this.dic = new HashSet<String>();
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
                    this.insertWord(word.trim());
            }
            // �ر�������
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
        if (this.dic == null)
            return false;
        if (word == null || StringUtils.isBlank(word))
            return false;
        if (word.length() > 2)
            return false;

        // ���ؽ��
        return this.dic.contains(word);
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
        for (String key : this.dic)
            out.println(key);
        out.flush();
    }

}
