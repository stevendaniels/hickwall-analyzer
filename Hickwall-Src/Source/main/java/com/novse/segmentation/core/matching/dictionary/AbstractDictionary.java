/*
 * @����:Hades , ��������:Apr 23, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.util.List;

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
     * ���ʻ��������뵽�ʵ��ļ���
     * 
     * @param wordList
     *            ������ʻ��б�
     */
    public void insertWord(List<String> wordList)
    {
        for (String word : wordList)
            this.insertWord(word);
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    abstract public void insertWord(String word);

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

    /**
     * �������ı���ʽ�洢�Ĵʵ�
     * 
     * @param fileName
     *            �ʵ���ļ���
     */
    abstract public void loadDictionary(String fileName);

}
