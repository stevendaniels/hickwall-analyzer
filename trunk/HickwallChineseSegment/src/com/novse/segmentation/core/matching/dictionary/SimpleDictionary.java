/*
 * @作者:Hades , 创建日期:2006-11-18
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan 简单顺序词典操作类
 */
public class SimpleDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -6631832710612755332L;

    /**
     * 词典容器
     */
    private ArrayList<String> dic = null;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 初始化词典
        if (this.dic == null)
            return;

        if (word == null || StringUtils.isBlank(word))
            return;
        int pos;
        // 判断原词典中是否已有该词汇
        if ((pos = Collections.binarySearch(dic, word)) < 0)
            return;
        else
            dic.remove(pos);
    }

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    public void insertWord(String word)
    {
        // 初始化词典
        if (this.dic == null)
            dic = new ArrayList<String>();

        if (word == null || StringUtils.isBlank(word))
            return;
        // 判断原词典中是否已有该词汇
        if (Collections.binarySearch(dic, word) < 0)
            dic.add(word);
        // 插入后重新排序
        Collections.sort(dic);
    }

    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    public void loadDictionary(String fileName)
    {
        // 初始化词典容器
        if (this.dic != null)
            this.dic.clear();
        this.dic = new ArrayList<String>();

        try
        {
            // 初始化输入流
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String word = null;

            // 读取词典
            while ((word = in.readLine()) != null)
            {
                // 插入词汇
                if (!StringUtils.isBlank(word))
                    dic.add(word.trim());
            }
            // 词典排序
            Collections.sort(dic);
            // 关闭输入
            in.close();
        }
        catch (IOException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 判断输入的字符串是否在词典中
     * 
     * @param word
     *            待判断字符串
     * @return 判断结果
     */
    public boolean match(String word)
    {
        // 判断词典是否已初始化
        if (dic == null)
            return false;

        if (word == null || StringUtils.isBlank(word))
            return false;

        int pos = Collections.binarySearch(dic, word);
        if (pos >= 0)
            return true;
        else
            return false;
    }

    /**
     * 输出已载入内存中所有词汇
     * 
     * @param out
     *            输出流
     */
    public void print(PrintStream out)
    {
        // 判断词典是否已初始化
        if (dic == null)
            return;

        for (int i = 0; i < this.dic.size(); i++)
        {
            out.println(dic.get(i));
        }
        out.flush();
    }

}
