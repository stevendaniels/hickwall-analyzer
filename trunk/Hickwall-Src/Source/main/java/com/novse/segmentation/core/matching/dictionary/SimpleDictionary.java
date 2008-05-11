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
 * @author Mac Kwan 简单顺序词典操作类
 */
public class SimpleDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -6631832710612755332L;

    /**
     * 词典容器
     */
    private ArrayList<String> dic = null;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 判断词典是否为空
        if (isEmpty())
            return;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        int pos;

        // 判断原词典中是否已有该词汇
        if ((pos = Collections.binarySearch(dic, word)) < 0)
            return;
        else
            dic.remove(pos);
    }

    /**
     * 将词汇批量插入到词典文件中
     * 
     * @param wordList
     *            待插入词汇列表
     */
    @Override
    public void insertWord(List<String> wordList)
    {
        // 判断词典是否已初始化
        if (dic == null)
            dic = new ArrayList<String>();

        // 判断待插入词汇列表是否为空
        if (wordList == null || wordList.size() == 0)
            return;

        // 剔除列表中空或单字或词典中已有的词汇
        wordList = this.eliminate(wordList);

        // 加入词汇
        dic.addAll(wordList);

        // 排序
        Collections.sort(dic);
    }

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    public void insertWord(String word)
    {
        // 初始化词典
        if (this.dic == null)
            dic = new ArrayList<String>();

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        // 判断原词典中是否已有该词汇
        if (Collections.binarySearch(dic, word) < 0)
            dic.add(word);
        // 插入后重新排序
        Collections.sort(dic);
    }

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    @Override
    public boolean isEmpty()
    {
        return dic == null || dic.isEmpty();
    }

    /**
     * 判断输入的字符串是否在词典中
     * 
     * @param word
     *            待判断字符串
     * @return 判断结果
     */
    public boolean match(String word)
    {
        // 判断词典是否已初始化
        if (isEmpty())
            return false;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return false;
        // 去除多余空格
        word = word.trim();
        // 单字成词
        if (word.length() == 1)
            return true;

        int pos = Collections.binarySearch(dic, word);
        if (pos >= 0)
            return true;
        else
            return false;
    }

    /**
     * 输出已载入内存中所有词汇
     * 
     * @param out
     *            输出流
     */
    public void print(PrintStream out)
    {
        // 判断词典是否已初始化
        if (dic == null)
            return;

        for (int i = 0; i < this.dic.size(); i++)
        {
            out.println(dic.get(i));
        }
        out.flush();
    }

}
