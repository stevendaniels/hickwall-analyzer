/*
 * @作者:Hades , 创建日期:2007-4-2
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan 双阀值的前缀后缀处理器
 */
public class DoubleSetpointPostProcessor extends BasicStatisticPostProcessor
{
    /**
     * @author Mac Kwan 内部类，高频字串比较器（对高频字串出现的频次进行比较）
     */
    static protected class WordInfoFrequenceComparator implements
            Comparator<WordInfo>
    {
        public int compare(WordInfo o1, WordInfo o2)
        {
            return o2.frequence - o1.frequence;
        }
    }

    /**
     * @author Mac Kwan 内部类，高频字串比较器（对高频字串进行比较）
     */
    static protected class WordInfoStringComparator implements
            Comparator<WordInfo>
    {
        public int compare(WordInfo o1, WordInfo o2)
        {
            return o1.word.compareTo(o2.word);
        }
    }

    /**
     * 置信度上限
     */
    private double maxConfidence = 0.9;

    /**
     * 置信度下限
     */
    private double minConfidence = 0.1;

    /**
     * 前缀后缀处理
     * 
     * @param wordList
     *            待处理的高频字串集合
     * @return 处理后的高频字串集合
     * @throws Exception
     */
    @Override
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("待处理高频字串集合wordList为null");

        // 先对待处理的高频字串集合排序
        Collections.sort(wordList, new WordInfoStringComparator());

        // 创建废置数组
        ArrayList<WordInfo> remove = new ArrayList<WordInfo>();

        // 循环遍历高频字串集合
        for (int i = 0; i < wordList.size(); i++)
        {
            // 去除“的”“了”“地”“着”开头的词汇
            if (wordList.get(i).word.indexOf("的") == 0
                    || wordList.get(i).word.indexOf("了") == 0
                    || wordList.get(i).word.indexOf("地") == 0
                    || wordList.get(i).word.indexOf("着") == 0)
            {
                remove.add(wordList.get(i));
                continue;
            }
            for (int j = i + 1; j < wordList.size(); j++)
            {
                // 判断wordList[i]是否为wordList[j]的前缀
                int firstIndex = wordList.get(j).word
                        .indexOf(wordList.get(i).word);
                // 判断wordList[i]是否为wordList[j]的后缀
                int lastIndex = wordList.get(j).word.lastIndexOf(wordList
                        .get(i).word);

                // wordList[i]是wordList[j]的字串时
                if (firstIndex >= 0 || lastIndex >= 0)
                {
                    double confidence = (double) (wordList.get(i).frequence - wordList
                            .get(j).frequence)
                            / (double) wordList.get(j).frequence;
                    // 置信度过小
                    if (confidence <= this.minConfidence)
                        remove.add(wordList.get(i));
                    // 置信度过大
                    if (confidence >= this.maxConfidence)
                        remove.add(wordList.get(j));
                }
            }
        }
        // 移除不符合条件的高频字串
        wordList.removeAll(remove);
        // 清空废置数组
        remove = null;

        // 返回结果
        return wordList;
    }

    /**
     * @param maxConfidence
     *            要设置的 maxConfidence。
     */
    public void setMaxConfidence(double maxConfidence)
    {
        this.maxConfidence = maxConfidence;
    }

    /**
     * @param minConfidence
     *            要设置的 minConfidence。
     */
    public void setMinConfidence(double minConfidence)
    {
        this.minConfidence = minConfidence;
    }
}
