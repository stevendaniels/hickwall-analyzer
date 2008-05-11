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
 * @author Mac Kwan 单层哈希词典操作类
 */
public class HashDictionary extends AbstractDictionary implements Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -436844886894530829L;

    /**
     * 词典
     */
    private Hashtable<String, ArrayList<String>> dic = null;

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

        // 截取首字
        String fch = word.substring(0, 1);
        if (dic.containsKey(fch))
        {
            // 获取词汇表
            ArrayList<String> wal = dic.get(fch);
            // 截取词汇剩余部分
            String leftWord = word.substring(1);
            // 查找该词汇是否存在于词汇表中
            int pos = Collections.binarySearch(wal, leftWord);
            // 存在时删除
            if (pos >= 0)
            {
                wal.remove(pos);
                dic.put(fch, wal);
            }
        }
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
        // 初始化词典
        if (this.dic == null)
            dic = new Hashtable<String, ArrayList<String>>();

        // 判断待插入词汇列表是否为空
        if (wordList == null || wordList.size() == 0)
            return;

        // 剔除列表中空或单字或词典中已有的词汇
        wordList = this.eliminate(wordList);

        // 记录待插入词汇首字
        Set<String> fchSet = new HashSet<String>();

        // 遍历待插入词汇
        for (String word : wordList)
        {
            // 截取首字
            String fch = word.substring(0, 1);
            // 词汇表
            ArrayList<String> wal = null;
            if (dic.containsKey(fch))
                wal = dic.get(fch);
            else
                wal = new ArrayList<String>();
            // 截取词汇剩余部分
            String leftWord = word.substring(1);

            // 加入到根据首字记录集中
            fchSet.add(fch);

            // 插入词汇
            wal.add(leftWord);
            dic.put(fch, wal);
        }

        // 根据首字记录集排序
        for (String fch : fchSet)
        {
            ArrayList<String> wal = dic.get(fch);
            Collections.sort(wal);
            dic.put(fch, wal);
        }
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
            dic = new Hashtable<String, ArrayList<String>>();

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        // 截取首字
        String fch = word.substring(0, 1);
        // 词汇表
        ArrayList<String> wal = null;
        if (dic.containsKey(fch))
            wal = dic.get(fch);
        else
            wal = new ArrayList<String>();
        // 截取词汇剩余部分
        String leftWord = word.substring(1);
        // 判断词汇表中是否已有该词汇
        if (Collections.binarySearch(wal, leftWord) < 0)
        {
            wal.add(leftWord);
            Collections.sort(wal);
            dic.put(fch, wal);
        }
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

        // 截取首字
        String fch = word.substring(0, 1);
        // 判断词汇表是否有此首字
        if (!dic.containsKey(fch))
            return false;
        // 获取词汇表
        ArrayList<String> wal = dic.get(fch);
        // 截取词汇剩余部分
        String leftWord = word.substring(1);

        // 折半查找
        int pos = Collections.binarySearch(wal, leftWord);

        return (pos >= 0);
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

        // 获取首字集合
        for (String fch : dic.keySet())
        {
            out.println("首字:" + fch);
            for (String w : dic.get(fch))
                out.println("\t" + w);
        }
        out.flush();
    }

}
