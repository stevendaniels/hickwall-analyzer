/*
 * @作者:Hades , 创建日期:Apr 23, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.util.List;

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
     * 将词汇批量插入到词典文件中
     * 
     * @param wordList
     *            待插入词汇列表
     */
    public void insertWord(List<String> wordList)
    {
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

    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    abstract public void loadDictionary(String fileName);

}
