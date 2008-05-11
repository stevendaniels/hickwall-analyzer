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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

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
     * ���ʻ��������뵽�ʵ��ļ���
     * 
     * @param wordList
     *            ������ʻ��б�
     */
    @Override
    public void insertWord(List<String> wordList)
    {
        // ��ʼ���ʵ�
        if (this.dic == null)
            dic = new Hashtable<String, ArrayList<String>>();

        // �жϴ�����ʻ��б��Ƿ�Ϊ��
        if (wordList == null || wordList.size() == 0)
            return;

        // �޳��б��пջ��ֻ�ʵ������еĴʻ�
        wordList = this.eliminate(wordList);

        // ��¼������ʻ�����
        Set<String> fchSet = new HashSet<String>();

        // ����������ʻ�
        for (String word : wordList)
        {
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

            // ���뵽�������ּ�¼����
            fchSet.add(fch);

            // ����ʻ�
            wal.add(leftWord);
            dic.put(fch, wal);
        }

        // �������ּ�¼������
        for (String fch : fchSet)
        {
            ArrayList<String> wal = dic.get(fch);
            Collections.sort(wal);
            dic.put(fch, wal);
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

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ȥ�����ִ�
        if (word.length() == 1)
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
