/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        // �жϴʵ��Ƿ�Ϊ��
        if (isEmpty())
            return;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ȥ�����ִ�
        if (word.length() == 1)
            return;

        int pos;

        // �ж�ԭ�ʵ����Ƿ����иôʻ�
        if ((pos = Collections.binarySearch(dic, word)) < 0)
            return;
        else
            dic.remove(pos);
    }

    /**
     * ���ʻ��������뵽�ʵ��ļ���
     * 
     * @param wordList
     *            ������ʻ��б�
     */
    @Override
    public void insertWord(List<String> wordList)
    {
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (dic == null)
            dic = new ArrayList<String>();

        // �жϴ�����ʻ��б��Ƿ�Ϊ��
        if (wordList == null || wordList.size() == 0)
            return;

        // �޳��б��пջ��ֻ�ʵ������еĴʻ�
        wordList = this.eliminate(wordList);

        // ����ʻ�
        dic.addAll(wordList);

        // ����
        Collections.sort(dic);
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

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ȥ�����ִ�
        if (word.length() == 1)
            return;

        // �ж�ԭ�ʵ����Ƿ����иôʻ�
        if (Collections.binarySearch(dic, word) < 0)
            dic.add(word);
        // �������������
        Collections.sort(dic);
    }

    /**
     * �ʵ��Ƿ�Ϊ��
     * 
     * @return �ʵ��Ƿ�Ϊ��
     */
    @Override
    public boolean isEmpty()
    {
        return dic == null || dic.isEmpty();
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
        if (isEmpty())
            return false;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return false;
        // ȥ������ո�
        word = word.trim();
        // ���ֳɴ�
        if (word.length() == 1)
            return true;

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
