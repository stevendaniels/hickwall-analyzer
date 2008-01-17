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
package com.novse.segmentation.core.unlistedword.dictionary;

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
        if (isEmpty())
            return;
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
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

        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        if (word.length() > 2)
            return;
        // ��������
        this.dic.add(word);
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
        if (isEmpty())
            return false;

        if (StringUtils.isBlank(word))
            return false;
        // ȥ������ո�
        word = word.trim();
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
