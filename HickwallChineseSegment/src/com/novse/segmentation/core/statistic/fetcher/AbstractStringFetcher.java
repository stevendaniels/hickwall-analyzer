/*
 * @作者:Hades , 创建日期:2007-4-2
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.util.List;

/**
 * @author Mac Kwan 词汇抽取器的抽象实现类
 */
public abstract class AbstractStringFetcher implements StringFetcher
{
    /**
     * 英文数字字符集
     */
    protected final String CHAR_AND_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 分隔符字符串
     */
    protected String seperator = null;

    /**
     * 从待处理文件srcFile中抽取词汇
     * 
     * @param srcFile
     *            待处理文件
     * @return 抽取所得词汇
     */
    abstract public List<String> fileFetch(String srcFile);

    /**
     * 初始化分隔符的方法
     */
    protected void initSeperator()
    {
        // 初始化分隔符
        StringBuffer buffer = new StringBuffer();
        for (char c = '\u0000'; c <= '\u007F'; c++)
        {
            // 不过滤英文、数字字符
            if (this.CHAR_AND_NUM.indexOf(c) < 0)
                buffer.append(c);
        }
        for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            buffer.append(c);
        buffer.append(" \r\n《》？，。、：“；‘’”『』【】－―—─＝÷＋§·～！◎＃￥％…※×（）　");
        this.seperator = buffer.toString();
    }

    /**
     * 从待处理字符串doc中抽取词汇
     * 
     * @param doc
     *            待处理字符串
     * @return 抽取所得词汇
     */
    abstract public List<String> textFetch(String doc);

}
