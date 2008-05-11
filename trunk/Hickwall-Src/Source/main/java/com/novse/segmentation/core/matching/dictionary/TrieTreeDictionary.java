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
 * @author Mac Kwan Trie索引树词典操作类
 */
public class TrieTreeDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 5356931651155974259L;

    /**
     * 词典
     */
    private TreeMap dicMap = null;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 判断当前词库中是否已有词汇word
        if (!this.match(word))
            return;

        // 判断词典是否已初始化
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

        // 在末端加上'\0'
        if (!word.endsWith("\0"))
            word = word + "\0";

        TreeMap[] mapSet = new TreeMap[word.length()];
        // 当前层HashMap
        TreeMap tempMap = this.dicMap;
        // 遍历字符串所有字
        for (int i = 0; i < word.length(); i++)
        {
            // 截取单字
            char ch = word.charAt(i);
            // 保存当前层HashMap，指向下层HashMap
            mapSet[i] = tempMap;
            // 非末端字
            if (i != word.length() - 1)
                tempMap = (TreeMap) tempMap.get(ch);
        }
        // 从后遍历所有层次HashMap
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
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    @SuppressWarnings("unchecked")
    public void insertWord(String word)
    {
        // 判断词典中是否已有该词汇
        if (this.match(word))
            return;

        // 判断词典是否已初始化
        if (this.dicMap == null)
            this.dicMap = new TreeMap();

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        // 在末端加上'\0'
        if (!word.endsWith("\0"))
            word = word + "\0";

        // 第一层Hash地图
        TreeMap tempMap = this.dicMap;
        // 遍历字符串中所有字符
        for (int i = 0; i < word.length(); i++)
        {
            // 截取单字
            char ch = word.charAt(i);
            // 判断当前层次的键中是否包含单字ch
            if (tempMap.containsKey(ch))
                tempMap = (TreeMap) tempMap.get(ch);
            else
            {
                // 非最后字符时，初始化下一层Hash地图
                if (i != word.length() - 1)
                {
                    TreeMap newMap = new TreeMap();
                    tempMap.put(ch, newMap);
                    tempMap = newMap;
                }
                // 否则，将最后字符放入Hash地图
                else
                    tempMap.put(ch, ch);
            }
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
        return dicMap == null || dicMap.isEmpty();
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
        // 当词典尚未初始化则返回false
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

        // 在word末端附加'\0'以避免同一前缀冲突
        if (!word.endsWith("\0"))
            word = word + "\0";

        // 结果
        boolean result = false;
        // 第一层Hash地图
        TreeMap tempMap = this.dicMap;
        // 遍历字符串各字
        for (int i = 0; i < word.length(); i++)
        {
            // 获取第i个字符
            char ch = word.charAt(i);
            // 查看当前层次HashMap是否包含当前字符ch
            if (tempMap.containsKey(ch))
            {
                if (i != word.length() - 1)
                    tempMap = (TreeMap) tempMap.get(ch);
                else
                    // 由于最后的字符为'\0'
                    result = true;
            }
            else
                break;
        }
        return result;
    }

    /**
     * 输出已载入内存中所有词汇
     * 
     * @deprecated
     * 
     * @param out
     *            输出流
     */
    public void print(PrintStream out)
    {
        out.println("当前组织形式词典尚未实现此方法");
        out.flush();
    }
}
