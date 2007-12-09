/*
 * @作者:Hades , 创建日期:2007-4-2
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.basic;

import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan 基于统计的高频词汇抽取器的后期处理器
 */
public class BasicStatisticPostProcessor
{
    /**
     * 前缀后缀处理
     * 
     * @param wordList
     *            待处理的高频字串集合
     * @return 处理后的高频字串集合
     * @throws Exception
     */
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("待处理高频字串集合wordList为null");
        return wordList;
    }
}
