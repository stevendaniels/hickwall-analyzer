/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * 基于词典匹配的中文分词抽象类
 * 
 * @author Mac Kwan 
 */
public abstract class AbstractSegmentProcessor implements SegmentProcessor
{
    /**
     * 词典操作类
     *
     */
    protected Dictionary dic = null;

    /**
     * 词汇抽取器
     */
    protected StringFetcher fetcher = null;

    /**
     * 分隔符字符串
     */
    protected String seperator = null;

    /**
     * 对srcFile文件进行分词，把结果保存为到tagFile文件中
     * 
     * @param srcFile
     *            待分词的文本文件
     * @param tagFile
     *            分词结果保存目的文件
     */
    public void fileProcessor(String srcFile, String tagFile)
    {
        try
        {
            // 初始化输入输出
            BufferedReader in = new BufferedReader(new FileReader(srcFile));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(tagFile)));

            // 读入文件
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null)
            {
                buffer.append(line);
                buffer.append(System.getProperties().getProperty(
                        "line.separator"));
            }
            // 关闭输入
            in.close();

            // 分词处理
            List<String> result = this.textProcess(buffer.toString()
                    .toLowerCase());

            // 将结果写入文件
            for (String w : result)
                out.print(w + " ");
            // 关闭输出
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }

    /**
     * @return 返回 dic。
     */
    public Dictionary getDic()
    {
        return dic;
    }

    /**
     * @return 返回 fetcher。
     */
    public StringFetcher getFetcher()
    {
        return fetcher;
    }

    /**
     * 初始化分隔符的方法
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveString();
    }

    /**
     * @param dic
     *            要设置的 dic。
     */
    public void setDic(Dictionary dic)
    {
        this.dic = dic;
    }

    /**
     * @param fetcher
     *            要设置的 fetcher。
     */
    public void setFetcher(StringFetcher fetcher)
    {
        this.fetcher = fetcher;
    }

    /**
     * 对text文本进行分词，把结果保存为字符串链表
     * 
     * @param text
     *            待分词的文本
     * @return 分词结果
     */
    abstract public List<String> textProcess(String text);

    /**
     * 转半角的函数(DBC case)
     * 
     * @param str
     *            待转换的字符串
     * @return 转换为半角后的字符串
     */
    protected String toDBC(String str)
    {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 12288)
            {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        str = null;
        return new String(c);
    }
}
