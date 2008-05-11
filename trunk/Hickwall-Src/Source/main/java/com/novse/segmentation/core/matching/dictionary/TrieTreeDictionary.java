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
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan Trie�������ʵ������
 */
public class TrieTreeDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 5356931651155974259L;

    /**
     * �ʵ�
     */
    private TreeMap dicMap = null;

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    public void deleteWord(String word)
    {
        // �жϵ�ǰ�ʿ����Ƿ����дʻ�word
        if (!this.match(word))
            return;

        // �жϴʵ��Ƿ��ѳ�ʼ��
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

        // ��ĩ�˼���'\0'
        if (!word.endsWith("\0"))
            word = word + "\0";

        TreeMap[] mapSet = new TreeMap[word.length()];
        // ��ǰ��HashMap
        TreeMap tempMap = this.dicMap;
        // �����ַ���������
        for (int i = 0; i < word.length(); i++)
        {
            // ��ȡ����
            char ch = word.charAt(i);
            // ���浱ǰ��HashMap��ָ���²�HashMap
            mapSet[i] = tempMap;
            // ��ĩ����
            if (i != word.length() - 1)
                tempMap = (TreeMap) tempMap.get(ch);
        }
        // �Ӻ�������в��HashMap
        for (int i = word.length() - 1; i >= 0; i--)
        {
            int limitSize = (i == word.length() - 1 ? 1 : 0);
            if (mapSet[i].size() == limitSize && i > 0)
                mapSet[i - 1].remove(word.charAt(i - 1));
            else
            {
                mapSet[i].remove(word.charAt(i));
                break;
            }
        }
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    @SuppressWarnings("unchecked")
    public void insertWord(String word)
    {
        // �жϴʵ����Ƿ����иôʻ�
        if (this.match(word))
            return;

        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (this.dicMap == null)
            this.dicMap = new TreeMap();

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ȥ�����ִ�
        if (word.length() == 1)
            return;

        // ��ĩ�˼���'\0'
        if (!word.endsWith("\0"))
            word = word + "\0";

        // ��һ��Hash��ͼ
        TreeMap tempMap = this.dicMap;
        // �����ַ����������ַ�
        for (int i = 0; i < word.length(); i++)
        {
            // ��ȡ����
            char ch = word.charAt(i);
            // �жϵ�ǰ��εļ����Ƿ��������ch
            if (tempMap.containsKey(ch))
                tempMap = (TreeMap) tempMap.get(ch);
            else
            {
                // ������ַ�ʱ����ʼ����һ��Hash��ͼ
                if (i != word.length() - 1)
                {
                    TreeMap newMap = new TreeMap();
                    tempMap.put(ch, newMap);
                    tempMap = newMap;
                }
                // ���򣬽�����ַ�����Hash��ͼ
                else
                    tempMap.put(ch, ch);
            }
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
        return dicMap == null || dicMap.isEmpty();
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
        // ���ʵ���δ��ʼ���򷵻�false
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

        // ��wordĩ�˸���'\0'�Ա���ͬһǰ׺��ͻ
        if (!word.endsWith("\0"))
            word = word + "\0";

        // ���
        boolean result = false;
        // ��һ��Hash��ͼ
        TreeMap tempMap = this.dicMap;
        // �����ַ�������
        for (int i = 0; i < word.length(); i++)
        {
            // ��ȡ��i���ַ�
            char ch = word.charAt(i);
            // �鿴��ǰ���HashMap�Ƿ������ǰ�ַ�ch
            if (tempMap.containsKey(ch))
            {
                if (i != word.length() - 1)
                    tempMap = (TreeMap) tempMap.get(ch);
                else
                    // ���������ַ�Ϊ'\0'
                    result = true;
            }
            else
                break;
        }
        return result;
    }

    /**
     * ����������ڴ������дʻ�
     * 
     * @deprecated
     * 
     * @param out
     *            �����
     */
    public void print(PrintStream out)
    {
        out.println("��ǰ��֯��ʽ�ʵ���δʵ�ִ˷���");
        out.flush();
    }
}
