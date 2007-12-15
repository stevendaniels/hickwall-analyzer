/*
 * @作者:Hades , 创建日期:2007-1-25
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.lucene.analysis;

import java.util.Set;

import org.apache.lucene.analysis.StopFilter;

/**
 * @author Mac Kwan 分隔符制造器
 */
public class StopWordMaker
{
    /**
     * 附加分隔符
     */
    private static final String APPEND_STRING = " \r\n《》？，。、：“；‘’”『』【】－―—─＝÷＋§·～！◎＃￥％…※×（）　";

    /**
     * 英文数字字符集
     */
    public static final String CHAR_AND_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz０１２３４５６７８９ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

    /**
     * “不含有数字及英文字母”的分隔符字符串
     */
    private static String seperator = null;

    /**
     * “含有数字及英文字母”的分隔符字符串
     */
    private static String seperatorWithCharAndNum = null;

    /**
     * 分隔符结合
     */
    private static Set stopWords = null;

    /**
     * 返回“不含有数字及英文字母”分隔符集合
     * 
     * @return 分隔符集合
     */
    public static Set retreiveSet()
    {
        if (stopWords == null)
        {
            // 初始化分隔符
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                // 不过滤英文、数字字符
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
                buffer.append(c);
            buffer.append(APPEND_STRING);
            stopWords = StopFilter.makeStopSet(buffer.toString().trim().split(
                    ""));
        }
        return stopWords;
    }

    /**
     * 返回“不含有数字及英文字母”的分隔符字符串
     * 
     * @return “不含有数字及英文字母”的分隔符字符串
     */
    public static String retreiveString()
    {
        if (seperator == null)
        {
            // 初始化分隔符
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                // 不过滤英文、数字字符
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            {
                // 不过滤英文、数字字符
                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            buffer.append(APPEND_STRING);
            seperator = buffer.toString();
        }
        return seperator;
    }

    /**
     * 返回“含有数字及英文字母”的分隔符字符串
     * 
     * @return “含有数字及英文字母”的分隔符字符串
     */
    public static String retreiveStringWithNumberAndChar()
    {
        if (seperatorWithCharAndNum == null)
        {
            // 初始化分隔符
            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {
                buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
                buffer.append(c);
            buffer.append(APPEND_STRING);
            seperatorWithCharAndNum = buffer.toString();
        }
        return seperatorWithCharAndNum;
    }
}
