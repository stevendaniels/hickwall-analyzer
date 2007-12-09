/*
 * @作者:Hades , 创建日期:2006-11-19
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
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan 单层哈希词典操作类
 */
public class HashDictionary extends AbstractDictionary implements Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -436844886894530829L;

    /**
     * 词典
     */
    private Hashtable<String, ArrayList<String>> dic = null;

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
        // 截取首字
        String fch = word.substring(0, 1);
        if (dic.containsKey(fch))
        {
            // 获取词汇表
            ArrayList<String> wal = dic.get(fch);
            // 截取词汇剩余部分
            String leftWord = word.substring(1);
            // 查找该词汇是否存在于词汇表中
            int pos = Collections.binarySearch(wal, leftWord);
            // 存在时删除
            if (pos >= 0)
            {
                wal.remove(pos);
                dic.put(fch, wal);
            }
        }
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
            dic = new Hashtable<String, ArrayList<String>>();

        if (word == null || StringUtils.isBlank(word))
            return;
        // 截取首字
        String fch = word.substring(0, 1);
        // 词汇表
        ArrayList<String> wal = null;
        if (dic.containsKey(fch))
            wal = dic.get(fch);
        else
            wal = new ArrayList<String>();
        // 截取词汇剩余部分
        String leftWord = word.substring(1);
        // 判断词汇表中是否已有该词汇
        if (Collections.binarySearch(wal, leftWord) < 0)
        {
            wal.add(leftWord);
            Collections.sort(wal);
            dic.put(fch, wal);
        }
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
        this.dic = new Hashtable<String, ArrayList<String>>();

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

        // 截取首字
        String fch = word.substring(0, 1);
        // 判断词汇表是否有此首字
        if (!dic.containsKey(fch))
            return false;
        // 获取词汇表
        ArrayList<String> wal = dic.get(fch);
        // 截取词汇剩余部分
        String leftWord = word.substring(1);

        // 折半查找
        int pos = Collections.binarySearch(wal, leftWord);

        return (pos >= 0);
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

        // 获取首字集合
        for (String fch : dic.keySet())
        {
            out.println("首字:" + fch);
            for (String w : dic.get(fch))
                out.println("\t" + w);
        }
        out.flush();
    }

}
