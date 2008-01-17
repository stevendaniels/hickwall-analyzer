/*
 * @����:Hades , ��������:Apr 23, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan �ʵ���������
 */
public abstract class AbstractDictionary implements Dictionary
{

    /**
     * ����ɾ���ʵ��еĴʻ�
     * 
     * @param wordList
     *            ��ɾ���Ĵʻ��б�
     */
    public void deleteWord(List<String> wordList)
    {
        // �жϴʵ��Ƿ�Ϊ��
        if (isEmpty())
            return;

        // �жϴ�ɾ���ʻ��б��Ƿ�Ϊ��
        if (wordList == null || wordList.size() == 0)
            return;

        for (String word : wordList)
            this.deleteWord(word);
    }

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    abstract public void deleteWord(String word);

    /**
     * �޳��б��пջ��ֻ�ʵ������еĴʻ�
     * 
     * @param wordList
     *            �ʻ��б�
     * @return �޳���Ĵʻ��б�
     */
    protected List<String> eliminate(List<String> wordList)
    {
        // �жϴ������ʻ��б��Ƿ�Ϊ��
        if (wordList == null)
            return null;

        // ��ȡ�ʵ��Ƿ�Ϊ��
        boolean isEmpty = this.isEmpty();

        ArrayList<String> removeList = new ArrayList<String>();
        for (int i = 0; i < wordList.size(); i++)
        {
            String s = wordList.get(i);
            // �ж��ַ����Ƿ�Ϊ��
            if (StringUtils.isBlank(s))
            {
                removeList.add(s);
            }
            else
            {
                // ȥ������ո�
                s = s.trim();
                wordList.set(i, s);

                if (s.length() == 1 || (!isEmpty && this.match(s)))
                    removeList.add(s);
            }
        }
        wordList.removeAll(removeList);

        // ͨ��HashSet��wordList���ظ��Ĵʻ�ȥ��
        HashSet<String> wordSet = new HashSet<String>(wordList);

        // ������������䵽
        wordList.clear();
        wordList.addAll(wordSet);

        return wordList;
    }

    /**
     * ���ʻ��������뵽�ʵ��ļ���
     * 
     * @param wordList
     *            ������ʻ��б�
     */
    public void insertWord(List<String> wordList)
    {
        // �жϴ�����ʻ��б��Ƿ�Ϊ��
        if (wordList == null || wordList.size() == 0)
            return;

        // �޳��ջ��ظ��Ĵʻ�
        //wordList = this.eliminate(wordList);

        for (String word : wordList)
        {
            if(!this.match(word))
                this.insertWord(word);
        }
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    abstract public void insertWord(String word);

    /**
     * �ʵ��Ƿ�Ϊ��
     * 
     * @return �ʵ��Ƿ�Ϊ��
     */
    abstract public boolean isEmpty();

    /**
     * �������ı���ʽ�洢�Ĵʵ�
     * 
     * @param fileName
     *            �ʵ���ļ���
     */
    abstract public void loadDictionary(String fileName);

    /**
     * �ж�������ַ����Ƿ��ڴʵ���
     * 
     * @param word
     *            ���ж��ַ���
     * @return �жϽ��
     */
    abstract public boolean match(String word);

    /**
     * ����������ڴ������дʻ�
     * 
     * @param out
     *            �����
     */
    abstract public void print(PrintStream out);
}