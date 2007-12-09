/*
 * @作者:Hades , 创建日期:2006-12-28
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.novse.segmentation.core.statistic.basic.BasicStatisticPostProcessor;

/**
 * @author Mac Kwan 基于统计的高频词汇抽取器
 */
public class BasicStatisticStringFetcher extends AbstractStringFetcher
{
    /**
     * @author Mac Kwan 内部类，用于记录每个单字在待提取文本中出现的次数、位置等信息
     */
    static public class CharInfo
    {
        /**
         * 记录单字出现的次数
         */
        public int frequence = 0;

        /**
         * 当前单字是否已处理
         */
        public boolean hasProcessed = false;

        /**
         * 单字在文本中出现的位置链表
         */
        public LinkedList<Integer> posArray = new LinkedList<Integer>();
    }

    /**
     * 
     * @author Mac Kwan 内部类，用于保存高频字串
     */
    static public class WordInfo
    {
        /**
         * 出现频次
         */
        public int frequence = 0;

        /**
         * 价值
         */
        public double value = 0.0;

        /**
         * 高频字串
         */
        public String word = null;
    }

    /**
     * 存放文本中出现的汉字信息
     */
    private HashMap<Character, CharInfo> indexMap = null;

    /**
     * 前缀后缀处理器
     */
    private BasicStatisticPostProcessor postProcessor = null;

    /**
     * 默认构造函数
     * 
     * @param postProcessor
     *            前缀后缀处理器
     */
    public BasicStatisticStringFetcher(BasicStatisticPostProcessor postProcessor)
    {
        this.postProcessor = postProcessor;
        this.initSeperator();
    }

    /**
     * 自定义分隔符的构造函数
     * 
     * @param postProcessor
     *            前缀后缀处理器
     * @param seperator
     *            自定义分隔符
     */
    public BasicStatisticStringFetcher(String seperator,
            BasicStatisticPostProcessor postProcessor)
    {
        this.postProcessor = postProcessor;
        this.seperator = seperator;
    }

    /**
     * 从待处理文件srcFile中抽取词汇
     * 
     * @param srcFile
     *            待处理文件
     * @return 抽取所得词汇
     */
    public List<String> fileFetch(String srcFile)
    {
        List<String> result = null;
        try
        {
            // 初始化输入
            BufferedReader in = new BufferedReader(new FileReader(srcFile));

            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null)
            {
                if (line.length() > 1)
                    buffer.append(line.trim());
            }
            in.close();

            result = this.textFetch(buffer.toString());
            // 返回结果
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 返回结果
            return result;
        }
    }

    /**
     * 根据待处理文本的长度获取词频的下限
     * 
     * @param text
     *            待处理文本
     * @return 词频下限
     */
    protected int getFreLevel(String text)
    {
        // 初始化结果
        int result = 0;
        // 获取文本长度
        int l = text.length();

        if (l <= 500)
            result = 2;
        else if (l <= 2000)
            result = 3;
        else if (l <= 10000)
            result = 4;
        else
            result = 5;

        return result;
    }

    /**
     * 获取高频字串
     * 
     * @param text
     *            待处理文本
     * @throws Exception
     */
    protected List<WordInfo> getHighFrequenceString(String text)
            throws Exception
    {
        if (text == null)
            throw new Exception("待处理文本为null");
        if (this.indexMap == null)
            throw new Exception("尚未进行预处理，索引表indexMap为null");

        // 初始化结果链表
        ArrayList<WordInfo> result = new ArrayList<WordInfo>();

        // 初始化缓冲字符串数组
        ArrayList<String> strArray = new ArrayList<String>();

        // 遍历文本中所有字符
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);

            // 跳过分隔符
            if (!this.indexMap.containsKey(ch))
                continue;

            CharInfo info = this.indexMap.get(ch);

            // 判断当前字符是否已处理
            if (info.hasProcessed)
                continue;
            // 判断当前字符频次是否大于1
            if (info.frequence == 1)
            {
                info.hasProcessed = true;
                this.indexMap.put(ch, info);
                continue;
            }

            // 遍历当前字符在文本中出现的位置
            for (int p : info.posArray)
            {
                // 下一个位置
                int nextPos = p + 1;

                // 判断是否到达文本结尾
                if (nextPos >= text.length())
                    continue;

                // 获取后续汉字
                char nextCh = text.charAt(nextPos);

                // 初始化高频字串缓冲
                StringBuffer buffer = new StringBuffer("" + ch);
                while (this.indexMap.containsKey(nextCh))
                {
                    CharInfo nextInfo = this.indexMap.get(nextCh);
                    // 若后续汉字频次大于1并且未处理
                    if (nextInfo.frequence > 1 && !nextInfo.hasProcessed)
                    {
                        buffer.append(nextCh);
                        // 判断是否到达文本结尾
                        if (++nextPos < text.length())
                            nextCh = text.charAt(nextPos);
                        else
                            break;
                    }
                    else
                        break;
                }

                // 加入缓冲字符串数组
                String word = buffer.toString().trim();
                if (word.length() > 1)
                    strArray.add(word);
            }
            // end of foreach

            // 对缓冲字符串数组排序
            Collections.sort(strArray);

            // 设置为已处理
            info.hasProcessed = true;
            this.indexMap.put(ch, info);

            // 遍历缓冲字符串数组
            for (int i = 0; i < strArray.size(); i++)
            {
                int j = i;
                // 统计高频字串的词频
                while (j < strArray.size()
                        && strArray.get(j).indexOf(strArray.get(i)) >= 0)
                    j++;
                if (j - i >= this.getFreLevel(text))
                {
                    WordInfo wordInfo = new WordInfo();
                    wordInfo.frequence = j - i;
                    wordInfo.word = strArray.get(i);
                    wordInfo.value = this.getWordValue(wordInfo.frequence,
                            wordInfo.word.length());
                    result.add(wordInfo);
                }
                i = j;
            }
            // end of for strArray

            // 清空缓冲数组
            strArray.clear();
        }
        // end of for
        return result;
    }

    /**
     * 根据词频与词长计算权重
     * 
     * @param frequence
     *            词频
     * @param strLen
     *            词长
     * @return 权重
     */
    protected double getWordValue(int frequence, int strLen)
    {
        double result = 0;
        result = frequence + 3.0 / (double) strLen;
        return result;
    }

    /**
     * 前缀后缀处理
     * 
     * @param wordList
     *            待处理的高频字串集合
     * @return 处理后的高频字串
     * @throws Exception
     */
    protected List<String> postProcess(List<WordInfo> wordList)
            throws Exception
    {
        if (wordList == null)
            throw new Exception("待处理高频字串集合wordList为null");

        // 前缀后缀处理
        wordList = this.postProcessor.postProcess(wordList);

        // 输出结果
        LinkedList<String> result = new LinkedList<String>();
        for (WordInfo w : wordList)
            result.add(w.word);
        return result;
    }

    /**
     * 预处理，用于统计单字在text文本中出现的频次
     * 
     * @param text
     *            待处理文本
     * @throws Exception
     */
    protected void preProcess(String text) throws Exception
    {
        if (text == null)
            throw new Exception("待处理文本为null");

        // 初始化索引表
        this.indexMap = new HashMap<Character, CharInfo>();

        // 第一次遍历文本中所有字符
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);
            // 判断当前字符是否是分隔符
            if (this.seperator.indexOf(ch) < 0)
            {
                // 判断是否已存在该字符的索引
                if (this.indexMap.containsKey(ch))
                {
                    CharInfo info = this.indexMap.get(ch);
                    info.frequence++;
                    this.indexMap.put(ch, info);
                }
                else
                {
                    CharInfo info = new CharInfo();
                    info.frequence++;
                    this.indexMap.put(ch, info);
                }
                // end of containsKey
            }
            // end of indexOf
        }
        // end of for

        // 第二次遍历文本中所有字符
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);
            // 跳过分隔符
            if (!this.indexMap.containsKey(ch))
                continue;
            CharInfo info = this.indexMap.get(ch);
            // 当前字符出现次数大于1
            if (info.frequence > 1)
            {
                info.posArray.add(pos);
                this.indexMap.put(ch, info);
            }
            // end of >1
        }
        // end of for
    }

    /**
     * 从待处理字符串doc中抽取词汇
     * 
     * @param doc
     *            待处理字符串
     * @return 抽取所得词汇
     */
    public List<String> textFetch(String text)
    {
        List<String> result = null;
        try
        {
            // 清空索引
            this.indexMap = null;
            // 预处理

            this.preProcess(text.trim());

            // 获取高频词汇
            result = this.postProcess(this.getHighFrequenceString(text));
            // 结果排序
            Collections.sort(result);
            // 返回处理结果
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 返回处理结果
            return result;
        }
    }
}
