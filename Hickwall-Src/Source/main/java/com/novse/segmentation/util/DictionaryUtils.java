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
 * @author Mac Kwan 词典工具类
 */
public class DictionaryUtils
{
    /**
     * TXT格式词典加载器
     */
    private static final TextDictionaryLoader DIC_LOADER = new TextDictionaryLoader();

    /**
     * 构造初始化中文姓名词典类
     * 
     * @param firstNameDicResource
     *            中文姓氏词典资源
     * @param chineseNameDicResource
     *            中文姓名词典资源
     * @return 中文姓名词典类
     */
    public static ChineseNameDictionary createChineseNameDictionary(
            Resource firstNameDicResource, Resource chineseNameDicResource)
    {
        // 中文姓氏词典类
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        // 通过加载器载入词典
        DIC_LOADER.load(firstNameDic, firstNameDicResource);

        // 中文姓名词典类
        ChineseNameDictionary chineseNameDic = new ChineseNameDictionary(
                firstNameDic);
        // 通过加载器载入词典
        DIC_LOADER.load(chineseNameDic, chineseNameDicResource);

        return chineseNameDic;
    }

    /**
     * 构造初始化基于二维图表的词典类
     * 
     * @param dicResource
     *            词典资源
     * @return 基于二维图表的词典类
     */
    public static DiagramDictionary createDiagramDictionary(Resource dicResource)
    {

        // 构造基于二维图表的词典类
        DiagramDictionary dic = new DiagramDictionary();

        // 通过加载器载入词典
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * 构造初始化双重哈希词典类
     * 
     * @param dicResource
     *            词典资源
     * @return 双重哈希词典类
     */
    public static DoubleHashDictionary createDoubleHashDictionary(
            Resource dicResource)
    {

        // 构造基于双重哈希词典类
        DoubleHashDictionary dic = new DoubleHashDictionary();

        // 通过加载器载入词典
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * 构造初始化首字哈希词典类
     * 
     * @param dicResource
     *            词典资源
     * @return 首字哈希词典类
     */
    public static HashDictionary createHashDictionary(Resource dicResource)
    {
        // 构造首字哈希词典类
        HashDictionary dic = new HashDictionary();

        // 通过加载器载入词典
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * 构造初始化伪中文姓名词典类
     * 
     * @param firstNameDicResource
     *            中文姓氏词典资源
     * @param notChineseNameDicResource
     *            伪中文姓名词典资源
     * @return 伪中文姓名词典类
     */
    public static NotChineseNameDictionary createNotChineseNameDictionary(
            Resource firstNameDicResource, Resource notChineseNameDicResource)
    {
        // 中文姓氏词典类
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        // 通过加载器载入词典
        DIC_LOADER.load(firstNameDic, firstNameDicResource);

        // 伪中文姓名词典类
        NotChineseNameDictionary chineseNameDic = new NotChineseNameDictionary(
                firstNameDic);
        // 通过加载器载入词典
        DIC_LOADER.load(chineseNameDic, notChineseNameDicResource);

        return chineseNameDic;
    }

    /**
     * 构造初始化简单词典类
     * 
     * @param dicResource
     *            词典资源
     * @return 简单词典类
     */
    public static SimpleDictionary createSimpleDictionary(Resource dicResource)
    {

        // 构造简单词典类
        SimpleDictionary dic = new SimpleDictionary();

        // 通过加载器载入词典
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }

    /**
     * 构造初始化TRIE树词典类
     * 
     * @param dicResource
     *            词典资源
     * @return TRIE树词典类
     */
    public static TrieTreeDictionary createTrieTreeDictionary(
            Resource dicResource)
    {

        // 构造TRIE树词典类
        TrieTreeDictionary dic = new TrieTreeDictionary();

        // 通过加载器载入词典
        DIC_LOADER.load(dic, dicResource);

        return dic;
    }
}
