/*
 * @作者:Hades , 创建日期:May 15, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.unlistedword.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.matching.dictionary.AbstractDictionary;

/**
 * @author Mac Kwan 中文姓氏词典操作类
 */
public class ChineseFirstNameDictionary extends AbstractDictionary implements
        Serializable
{

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 860295715037663706L;

    /**
     * 词典实例
     */
    private HashSet<String> dic = null;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 初始化词典
        if (isEmpty())
            return;
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 2)
            return;
        // 删除姓氏
        this.dic.remove(word);
    }

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    public void insertWord(String word)
    {
        if (this.dic == null)
            this.dic = new HashSet<String>();

        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 2)
            return;
        // 插入姓氏
        this.dic.add(word);
    }

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    @Override
    public boolean isEmpty()
    {
        return dic == null || dic.isEmpty();
    }

    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    public void loadDictionary(String fileName)
    {
        // 初始化词典实例
        this.dic = new HashSet<String>();
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
                    this.insertWord(word.trim());
            }
            // 关闭输入流
            in.close();
        }
        catch (IOException e)
        {
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
        if (isEmpty())
            return false;

        if (StringUtils.isBlank(word))
            return false;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 2)
            return false;

        // 返回结果
        return this.dic.contains(word);
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
        for (String key : this.dic)
            out.println(key);
        out.flush();
    }

}
