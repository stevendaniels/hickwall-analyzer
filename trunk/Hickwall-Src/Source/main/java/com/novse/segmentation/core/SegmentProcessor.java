/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core;

import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;

/**
 * 
 * @author Mac Kwan 基于词典匹配的中文分词接口
 */
public interface SegmentProcessor
{

    /**
     * 对srcFile文件进行分词，把结果保存为到tagFile文件中
     * 
     * @param srcFile
     *            待分词的文本文件
     * @param tagFile
     *            分词结果保存目的文件
     */
    public void fileProcessor(String srcFile, String tagFile);

    /**
     * @return 返回 dic。
     */
    public Dictionary getDic();

    /**
     * @return 返回 fetcher。
     */
    public StringFetcher getFetcher();

    /**
     * @param dic
     *            要设置的 dic。
     */
    public void setDic(Dictionary dic);

    /**
     * @param fetcher
     *            要设置的 fetcher。
     */
    public void setFetcher(StringFetcher fetcher);

    /**
     * 对text文本进行分词，把结果保存为字符串链表
     * 
     * @param text
     *            待分词的文本
     * @return 分词结果
     */
    public List<String> textProcess(String text);

}
