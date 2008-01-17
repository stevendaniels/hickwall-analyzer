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
package com.novse.segmentation.util;

import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.DiagramDictionary;
import com.novse.segmentation.core.matching.dictionary.DoubleHashDictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.core.matching.dictionary.TrieTreeDictionary;
import com.novse.segmentation.core.matching.dictionary.loader.TextDictionaryLoader;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;

/**
 * @author Mac Kwan �ʵ乤����
 */
public class DictionaryUtils
{
    /**
     * TXT��ʽ�ʵ������
     */
    private static final TextDictionaryLoader DIC_LOADER = new TextDictionaryLoader();

    /**
     * �����ʼ�����������ʵ���
     * 
     * @param firstNameDicResource
     *            �������ϴʵ���Դ
     * @param chineseNameDicResource
     *            ���������ʵ���Դ
     * @return ���������ʵ���
     */
    public static ChineseNameDictionary createChineseNameDictionary(
            Resource firstNameDicResource, Resource chineseNameDicResource)
    {
        // �������ϴʵ���
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        // ͨ������������ʵ�
        DIC_LOADER.load(firstNameDic, firstNameDicResource);

        // ���������ʵ���
        ChineseNameDictionary chineseNameDic = new ChineseNameDictionary(
                firstNameDic);
        // ͨ������������ʵ�
        DIC_LOADER.load(chineseNameDic, chineseNameDicResource);

        return chineseNameDic;
    }

    /**
     * �����ʼ�����ڶ�άͼ��Ĵʵ���
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return ���ڶ�άͼ��Ĵʵ���
     */
    public static DiagramDictionary createDiagramDictionary(Resource dicResource)
    {

        // ������ڶ�άͼ��Ĵʵ���
        DiagramDictionary dic = new DiagramDictionary();

        // ͨ������������ʵ�
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * �����ʼ��˫�ع�ϣ�ʵ���
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return ˫�ع�ϣ�ʵ���
     */
    public static DoubleHashDictionary createDoubleHashDictionary(
            Resource dicResource)
    {

        // �������˫�ع�ϣ�ʵ���
        DoubleHashDictionary dic = new DoubleHashDictionary();

        // ͨ������������ʵ�
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * �����ʼ�����ֹ�ϣ�ʵ���
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return ���ֹ�ϣ�ʵ���
     */
    public static HashDictionary createHashDictionary(Resource dicResource)
    {
        // �������ֹ�ϣ�ʵ���
        HashDictionary dic = new HashDictionary();

        // ͨ������������ʵ�
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * �����ʼ��α���������ʵ���
     * 
     * @param firstNameDicResource
     *            �������ϴʵ���Դ
     * @param notChineseNameDicResource
     *            α���������ʵ���Դ
     * @return α���������ʵ���
     */
    public static NotChineseNameDictionary createNotChineseNameDictionary(
            Resource firstNameDicResource, Resource notChineseNameDicResource)
    {
        // �������ϴʵ���
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        // ͨ������������ʵ�
        DIC_LOADER.load(firstNameDic, firstNameDicResource);

        // α���������ʵ���
        NotChineseNameDictionary chineseNameDic = new NotChineseNameDictionary(
                firstNameDic);
        // ͨ������������ʵ�
        DIC_LOADER.load(chineseNameDic, notChineseNameDicResource);

        return chineseNameDic;
    }

    /**
     * �����ʼ���򵥴ʵ���
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return �򵥴ʵ���
     */
    public static SimpleDictionary createSimpleDictionary(Resource dicResource)
    {

        // ����򵥴ʵ���
        SimpleDictionary dic = new SimpleDictionary();

        // ͨ������������ʵ�
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * �����ʼ��TRIE���ʵ���
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return TRIE���ʵ���
     */
    public static TrieTreeDictionary createTrieTreeDictionary(
            Resource dicResource)
    {

        // ����TRIE���ʵ���
        TrieTreeDictionary dic = new TrieTreeDictionary();

        // ͨ������������ʵ�
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }
}
