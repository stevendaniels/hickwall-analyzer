/*
 * @����:Hades , ��������:2006-11-19
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
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan �����ϣ�ʵ������
 */
public class HashDictionary extends AbstractDictionary implements Serializable
{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -436844886894530829L;

    /**
     * �ʵ�
     */
    private Hashtable<String, ArrayList<String>> dic = null;

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
        // ��ȡ����
        String fch = word.substring(0, 1);
        if (dic.containsKey(fch))
        {
            // ��ȡ�ʻ��
            ArrayList<String> wal = dic.get(fch);
            // ��ȡ�ʻ�ʣ�ಿ��
            String leftWord = word.substring(1);
            // ���Ҹôʻ��Ƿ�����ڴʻ����
            int pos = Collections.binarySearch(wal, leftWord);
            // ����ʱɾ��
            if (pos >= 0)
            {
                wal.remove(pos);
                dic.put(fch, wal);
            }
        }
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
            dic = new Hashtable<String, ArrayList<String>>();

        if (word == null || StringUtils.isBlank(word))
            return;
        // ��ȡ����
        String fch = word.substring(0, 1);
        // �ʻ��
        ArrayList<String> wal = null;
        if (dic.containsKey(fch))
            wal = dic.get(fch);
        else
            wal = new ArrayList<String>();
        // ��ȡ�ʻ�ʣ�ಿ��
        String leftWord = word.substring(1);
        // �жϴʻ�����Ƿ����иôʻ�
        if (Collections.binarySearch(wal, leftWord) < 0)
        {
            wal.add(leftWord);
            Collections.sort(wal);
            dic.put(fch, wal);
        }
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
        this.dic = new Hashtable<String, ArrayList<String>>();

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
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (dic == null)
            return false;

        if (word == null || StringUtils.isBlank(word))
            return false;

        // ��ȡ����
        String fch = word.substring(0, 1);
        // �жϴʻ���Ƿ��д�����
        if (!dic.containsKey(fch))
            return false;
        // ��ȡ�ʻ��
        ArrayList<String> wal = dic.get(fch);
        // ��ȡ�ʻ�ʣ�ಿ��
        String leftWord = word.substring(1);

        // �۰����
        int pos = Collections.binarySearch(wal, leftWord);

        return (pos >= 0);
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

        // ��ȡ���ּ���
        for (String fch : dic.keySet())
        {
            out.println("����:" + fch);
            for (String w : dic.get(fch))
                out.println("\t" + w);
        }
        out.flush();
    }

}
