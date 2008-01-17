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
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan ˫���ϣ�ʵ������
 */
public class DoubleHashDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -6085097706592874294L;

    /**
     * �ʵ�������
     */
    private Hashtable<String, ArrayList<String>>[] indexTable = null;

    /**
     * ���ʳ�
     */
    private int maxWordLen = 0;

    /**
     * �ʵ䳤��
     */
    private int wordCount = 0;

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    public void deleteWord(String word)
    {
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

        // ��ȡ�ʳ�
        int len = word.length();

        // �ж��Ƿ�������ʳ�
        if (len > this.maxWordLen)
            return;

        // �жϴʳ�Ϊlen�Ķ�������������hash���Ƿ�Ϊ��
        if (this.indexTable[len - 1] == null)
            return;
        // ��ȡ�ʵ�����
        String fch = word.substring(0, 1);
        // ���ֶ�Ӧ�Ĵʻ��б�
        ArrayList<String> wal = null;
        if (this.indexTable[len - 1].containsKey(fch))
            wal = this.indexTable[len - 1].get(fch);
        else
            return;

        // �ж��Ƿ�����ôʻ�
        String str = word.substring(1, len);
        if (Collections.binarySearch(wal, str) >= 0)
        {
            wal.remove(str);
            this.indexTable[len - 1].put(fch, wal);
        }
        else
            return;
    }

    /**
     * @return ���� maxWordLen��
     */
    public int getMaxWordLen()
    {
        return maxWordLen;
    }

    /**
     * @return ���� wordCount��
     */
    public int getWordCount()
    {
        return wordCount;
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
        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ȥ�����ִ�
        if (word.length() == 1)
            return;

        // ��ȡ�ʳ�
        int len = word.length();

        // �����ʵ�
        if (this.indexTable == null || this.maxWordLen < len)
        {
            // ����ԭ�дʵ�
            Hashtable<String, ArrayList<String>>[] oldIndexTable = this.indexTable;

            // ���³�ʼ��
            this.indexTable = new Hashtable[len];

            // �ж��Ƿ��ѳ�ʼ��
            if (oldIndexTable != null)
            {
                // ����
                System.arraycopy(oldIndexTable, 0, indexTable, 0,
                        oldIndexTable.length);

                // �ÿ�
                oldIndexTable = null;
            }

            // �������ʳ�
            this.maxWordLen = len;
        }

        // ��ʼ����������������hash��
        if (this.indexTable[len - 1] == null)
            this.indexTable[len - 1] = new Hashtable<String, ArrayList<String>>();
        // ��ȡ�ʵ�����
        String fch = word.substring(0, 1);
        // ���ֶ�Ӧ�Ĵʻ��б�
        ArrayList<String> wal = null;
        if (this.indexTable[len - 1].containsKey(fch))
            wal = this.indexTable[len - 1].get(fch);
        else
            wal = new ArrayList<String>();

        // ��ȡʣ�ಿ��
        String str = word.substring(1, len);
        // ���ʻ���в����ڵ�ǰ�ʻ�ʱ�����´ʻ�
        if (Collections.binarySearch(wal, str) < 0)
            wal.add(str);

        Collections.sort(wal);
        this.indexTable[len - 1].put(fch, wal);
    }

    /**
     * �ʵ��Ƿ�Ϊ��
     * 
     * @return �ʵ��Ƿ�Ϊ��
     */
    @Override
    public boolean isEmpty()
    {
        return indexTable == null || indexTable.length == 0;
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
        // ȥ�����ִ�
        if (word.length() == 1)
            return false;

        // ��ȡ�ʳ�
        int len = word.length();

        // ���ʳ����ڵ�ǰ�ʿ������ʳ��򷵻�false
        if (len > this.maxWordLen)
            return false;

        // ���ʳ�Ϊlen��hash������δ����ʼ��ʱ����false
        if (this.indexTable[len - 1] == null)
            return false;

        // ��ȡ����
        String fch = word.substring(0, 1);
        if (this.indexTable[len - 1].containsKey(fch))
        {
            if (len == 1)
                return true;
            else
            {
                // ��ȡ��fch��ͷ�Ĵʻ��
                ArrayList<String> wal = this.indexTable[len - 1].get(fch);
                // �۰����
                if (Collections.binarySearch(wal, word.substring(1, len)) < 0)
                    return false;
                else
                    return true;
            }
        }
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
        if (this.indexTable == null)
            return;

        for (int i = 0; i < this.indexTable.length; i++)
        {
            out.println("�ʳ���" + (i + 1));
            // �жϴʵ��Ƿ��ѳ�ʼ��
            if (this.indexTable[i] != null)
            {
                for (String fch : this.indexTable[i].keySet())
                {
                    out.println("���֣�" + fch);
                    for (String w : this.indexTable[i].get(fch))
                        out.println("\t" + w);
                }
            }
        }
        out.flush();
    }
}
