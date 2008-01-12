/*
 * @作者:Hades , 创建日期:2007-4-6
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.unlistedword.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;

/**
 * @author Mac Kwan 用于记录汉字在实际语料中出现次数的操作类
 */
public class CharFrequenceDictionary implements Loadable, Serializable
{
    /**
     * 
     * @author Mac Kwan 用于记录汉字统计信息的实体
     * 
     */
    public static class CharFrequenceInfo implements Serializable
    {
        /**
         * <code>serialVersionUID</code> 的注释
         */
        private static final long serialVersionUID = -3687085604926867396L;

        /**
         * 汉字ch出现的次数
         */
        public int charFrequence = 0;

        /**
         * 汉字ch作为词汇首字出现的次数
         */
        public int firstCharFrequence = 0;

        /**
         * 汉字ch作为单字词出现的次数
         */
        public int singleCharFrequnece = 0;
    }

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -3656409214613561989L;

    /**
     * 记录总共的字数
     */
    private int charCount = 0;

    /**
     * 记录汉字出现次数的树型表
     */
    private HashMap<String, CharFrequenceInfo> map = null;

    /**
     * 记录总共的词汇数
     */
    private int wordCount = 0;

    /**
     * @return 返回 charCount。
     */
    public int getCharCount()
    {
        return charCount;
    }

    /**
     * 返回汉字ch的统计信息
     * 
     * @param ch
     *            汉字
     * @return 相应的统计信息
     */
    public CharFrequenceInfo getInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return 总词汇数。
     */
    public int getWordCount()
    {
        return wordCount;
    }

    /**
     * 从指定的文本文件srcFile中载入词频信息
     * 
     * @param fileName
     *            装载词频信息的文本文件
     */
    public void loadDictionary(String fileName)
    {
        try
        {
            // 初始化阅读器
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            // 初始化树型表
            this.map = new HashMap<String, CharFrequenceInfo>();

            String line = null;
            // 读入文件
            while ((line = in.readLine()) != null)
            {
                // 判断是否空字符串
                if (!StringUtils.isBlank(line))
                {
                    // 分隔字符
                    String[] array = line.split("\t");
                    if (array.length == 2)
                    {
                        // 获取词汇
                        String word = array[0];
                        // 获取词汇出现的次数
                        int frequence = Integer.parseInt(array[1]);

                        // 修正词汇数
                        this.wordCount += frequence;
                        // 修正字数
                        this.charCount += (word.length() * frequence);

                        // 判断是否为单字词
                        if (word.length() == 1)
                        {
                            CharFrequenceInfo info = null;
                            // 判断是否已记录该单字
                            if (this.map.containsKey(word))
                                info = this.map.get(word);
                            else
                                info = new CharFrequenceInfo();
                            // 修正出现次数
                            info.singleCharFrequnece += frequence;
                            info.charFrequence += frequence;
                            this.map.put(word, info);
                        }
                        else if (word.length() > 1)
                        {
                            // 遍历所有汉字
                            for (int i = 0; i < word.length(); i++)
                            {
                                // 获取单字ch
                                String ch = word.substring(i, i + 1);
                                CharFrequenceInfo info = null;
                                // 判断是否已记录该单字
                                if (this.map.containsKey(ch))
                                    info = this.map.get(ch);
                                else
                                    info = new CharFrequenceInfo();
                                // 修正出现次数
                                if (i == 0)
                                    info.firstCharFrequence += frequence;
                                info.charFrequence += frequence;
                                this.map.put(ch, info);
                            }
                        }
                        // end of if (word.length() == 1)
                    }
                    // end of if (array.length == 2)
                }
            }
            // 关闭阅读器
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
