/*
 * @作者:Hades , 创建日期:2006-11-17
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
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan 双层哈希词典操作类
 */
public class DoubleHashDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -6085097706592874294L;

    /**
     * 词典索引表
     */
    private Hashtable<String, ArrayList<String>>[] indexTable = null;

    /**
     * 最大词长
     */
    private int maxWordLen = 0;

    /**
     * 词典长度
     */
    private int wordCount = 0;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 判断词典是否已初始化
        if (isEmpty())
            return;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        // 获取词长
        int len = word.length();

        // 判断是否大于最大词长
        if (len > this.maxWordLen)
            return;

        // 判断词长为len的二级索引表（首字hash表）是否为空
        if (this.indexTable[len - 1] == null)
            return;
        // 获取词的首字
        String fch = word.substring(0, 1);
        // 首字对应的词汇列表
        ArrayList<String> wal = null;
        if (this.indexTable[len - 1].containsKey(fch))
            wal = this.indexTable[len - 1].get(fch);
        else
            return;

        // 判断是否包含该词汇
        String str = word.substring(1, len);
        if (Collections.binarySearch(wal, str) >= 0)
        {
            wal.remove(str);
            this.indexTable[len - 1].put(fch, wal);
        }
        else
            return;
    }

    /**
     * @return 返回 maxWordLen。
     */
    public int getMaxWordLen()
    {
        return maxWordLen;
    }

    /**
     * @return 返回 wordCount。
     */
    public int getWordCount()
    {
        return wordCount;
    }

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    @SuppressWarnings("unchecked")
    public void insertWord(String word)
    {
        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return;

        // 获取词长
        int len = word.length();

        // 调整词典
        if (this.indexTable == null || this.maxWordLen < len)
        {
            // 保留原有词典
            Hashtable<String, ArrayList<String>>[] oldIndexTable = this.indexTable;

            // 重新初始化
            this.indexTable = new Hashtable[len];

            // 判断是否已初始化
            if (oldIndexTable != null)
            {
                // 搬移
                System.arraycopy(oldIndexTable, 0, indexTable, 0,
                        oldIndexTable.length);

                // 置空
                oldIndexTable = null;
            }

            // 调整最大词长
            this.maxWordLen = len;
        }

        // 初始化二级索引表（首字hash表）
        if (this.indexTable[len - 1] == null)
            this.indexTable[len - 1] = new Hashtable<String, ArrayList<String>>();
        // 获取词的首字
        String fch = word.substring(0, 1);
        // 首字对应的词汇列表
        ArrayList<String> wal = null;
        if (this.indexTable[len - 1].containsKey(fch))
            wal = this.indexTable[len - 1].get(fch);
        else
            wal = new ArrayList<String>();

        // 截取剩余部分
        String str = word.substring(1, len);
        // 当词汇表中不存在当前词汇时插入新词汇
        if (Collections.binarySearch(wal, str) < 0)
            wal.add(str);

        Collections.sort(wal);
        this.indexTable[len - 1].put(fch, wal);
    }

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    @Override
    public boolean isEmpty()
    {
        return indexTable == null || indexTable.length == 0;
    }

    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    @SuppressWarnings("unchecked")
    public void loadDictionary(String fileName)
    {
        try
        {
            // 初始化输入流
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String word = null;
            // 初始化记录链表
            LinkedList<String> wordLink = new LinkedList<String>();
            // 最大词长
            this.maxWordLen = 0;

            // 读取词典
            while ((word = in.readLine()) != null)
            {
                // 插入词汇
                if (!StringUtils.isBlank(word))
                {
                    word = word.trim();
                    if (word.length() > this.maxWordLen)
                        this.maxWordLen = word.length();
                    wordLink.add(word);
                    this.wordCount++;
                }
            }
            // 关闭输入流
            in.close();

            // 初始化一级索引表（词长索引表）
            this.indexTable = new Hashtable[this.maxWordLen];
            // 重新遍历词典链表
            for (String w : wordLink)
            {
                // 插入词汇
                this.insertWord(w);
            }
            // 回收资源
            wordLink.clear();
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
        // 判断词典是否已初始化
        if (isEmpty())
            return false;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return false;
        // 去除多余空格
        word = word.trim();
        // 去除单字词
        if (word.length() == 1)
            return false;

        // 获取词长
        int len = word.length();

        // 当词长大于当前词库中最大词长则返回false
        if (len > this.maxWordLen)
            return false;

        // 当词长为len的hash索引表未被初始化时返回false
        if (this.indexTable[len - 1] == null)
            return false;

        // 获取首字
        String fch = word.substring(0, 1);
        if (this.indexTable[len - 1].containsKey(fch))
        {
            if (len == 1)
                return true;
            else
            {
                // 获取以fch开头的词汇表
                ArrayList<String> wal = this.indexTable[len - 1].get(fch);
                // 折半查找
                if (Collections.binarySearch(wal, word.substring(1, len)) < 0)
                    return false;
                else
                    return true;
            }
        }
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
        if (this.indexTable == null)
            return;

        for (int i = 0; i < this.indexTable.length; i++)
        {
            out.println("词长：" + (i + 1));
            // 判断词典是否已初始化
            if (this.indexTable[i] != null)
            {
                for (String fch : this.indexTable[i].keySet())
                {
                    out.println("首字：" + fch);
                    for (String w : this.indexTable[i].get(fch))
                        out.println("\t" + w);
                }
            }
        }
        out.flush();
    }
}
