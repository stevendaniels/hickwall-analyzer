/*
 * @作者:Hades , 创建日期:Apr 23, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan 词典抽象操作类
 */
public abstract class AbstractDictionary implements Dictionary
{

    /**
     * 批量删除词典中的词汇
     * 
     * @param wordList
     *            待删除的词汇列表
     */
    public void deleteWord(List<String> wordList)
    {
        // 判断词典是否为空
        if (isEmpty())
            return;

        // 判断待删除词汇列表是否为空
        if (wordList == null || wordList.size() == 0)
            return;

        for (String word : wordList)
            this.deleteWord(word);
    }

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    abstract public void deleteWord(String word);

    /**
     * 剔除列表中空或单字或词典中已有的词汇
     * 
     * @param wordList
     *            词汇列表
     * @return 剔除后的词汇列表
     */
    protected List<String> eliminate(List<String> wordList)
    {
        if (!isEmpty())
        {
            ArrayList<String> removeList = new ArrayList<String>();
            for (int i = 0; i < wordList.size(); i++)
            {
                String s = wordList.get(i);
                // 去除多余空格
                s = s.trim();
                wordList.set(i, s);

                if (StringUtils.isBlank(s) || s.length() == 1 || this.match(s))
                    removeList.add(s);
            }
            wordList.removeAll(removeList);
        }
        return wordList;
    }

    /**
     * 将词汇批量插入到词典文件中
     * 
     * @param wordList
     *            待插入词汇列表
     */
    public void insertWord(List<String> wordList)
    {
        // 判断待插入词汇列表是否为空
        if (wordList == null || wordList.size() == 0)
            return;

        // 剔除空或重复的词汇
        wordList = this.eliminate(wordList);

        for (String word : wordList)
            this.insertWord(word);
    }

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    abstract public void insertWord(String word);

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    abstract public boolean isEmpty();

    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    abstract public void loadDictionary(String fileName);

    /**
     * 判断输入的字符串是否在词典中
     * 
     * @param word
     *            待判断字符串
     * @return 判断结果
     */
    abstract public boolean match(String word);

    /**
     * 输出已载入内存中所有词汇
     * 
     * @param out
     *            输出流
     */
    abstract public void print(PrintStream out);
}
