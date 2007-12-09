/*
 * @作者:Hades , 创建日期:2007-4-2
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.util.List;

/**
 * @author Mac Kwan 词汇抽取器接口
 */
public interface StringFetcher
{
    /**
     * 从待处理文件srcFile中抽取词汇
     * 
     * @param srcFile
     *            待处理文件
     * @return 抽取所得词汇
     */
    public List<String> fileFetch(String srcFile);

    /**
     * 从待处理字符串doc中抽取词汇
     * 
     * @param doc
     *            待处理字符串
     * @return 抽取所得词汇
     */
    public List<String> textFetch(String doc);
}
