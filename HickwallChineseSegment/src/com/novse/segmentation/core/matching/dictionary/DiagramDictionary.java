/*
 * @作者:Hades , 创建日期:2007-4-17
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
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan 基于二维图表的词典操作类
 */
public class DiagramDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * 二维图表词典的大小
     */
    private static final int ARRAY_SIZE = 1692;

    /**
     * GB2312集合中汉字的个数
     */
    private static final int CHAR_SIZE = 6768;

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 8916348479425642741L;

    /**
     * 词长为2的词典实例
     */
    private short[][] diagramDic = null;

    /**
     * 词长大于2的词典实例
     */
    private HashDictionary otherDic = null;

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        if (diagramDic == null && otherDic == null)
            return;

        if (word == null || StringUtils.isBlank(word))
            return;
        // 去除多于空格
        word = word.trim();
        // 过滤单字词操作
        if (word.length() < 2)
            return;

        if (word.length() == 2)
        {
            // 计算位移
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // 判断字符1、2是否为GB2312编码中的汉字
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return;
            this.setDiagram(firstIndex, secondIndex, false);
        }
        else
            otherDic.deleteWord(word);
    }

    /**
     * 根据所输入的对应位移返回gb2312编码的汉字
     * 
     * @param index
     *            对应位移
     * @return gb2312编码的汉字
     */
    protected String getChar(int index)
    {
        // 判断位移是否小于0
        if (index < 0)
            return null;

        int div = index / 94;
        int mod = index % 94;
        byte[] strBytes =
        { (byte) (div - 80), (byte) (mod - 95) };
        try
        {
            return new String(strBytes, "gb2312");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断位移为firstIndex，secondIndex的词组是否存在
     * 
     * @param firstIndex
     *            二字词首字的位移
     * @param secondIndex
     *            二字词末字的位移
     * @return true存在，false不存在
     */
    protected boolean getDiagram(int firstIndex, int secondIndex)
    {
        // 基数
        short base = 2;

        // 计算首字在二维数组中的位置
        int firstArray = firstIndex / 4;
        int firstOffset = firstIndex % 4;
        // 计算末字在二维数组中的位置
        int secondArray = secondIndex / 4;
        int secondOffset = secondIndex % 4;

        // 获取二维数组中的数值
        short num = this.diagramDic[firstArray][secondArray];
        // 计算offset位于short类型数值第几位（bit）
        int pos = firstOffset + 4 * secondOffset;
        short compare = (short) Math.pow(base, pos);
        // 如果数值与2^pos相与后结果仍然等于2^pos则pos位为1
        if ((num & compare) == compare)
            return true;
        else
            return false;
    }

    /**
     * 根据所输入的gb2312编码的汉字返回对应的位移
     * 
     * @param ch
     *            汉字
     * @return 汉字对应的编码，若异常则返回-1
     */
    protected int getIndex(String ch)
    {
        // 若输入的字符串长度大于1
        if (ch.length() > 1)
            return -1;
        // 若输入'\0'则返回位移0
        if (ch.equals("\0"))
            return -1;
        try
        {
            // 返回汉字ch对应的byte数组
            byte[] bArray = ch.getBytes("gb2312");
            // byte数组大小大于2时
            if (bArray.length > 2)
                return -1;
            // 确保 -80<=bArray[0]<=-9 -95<=bArray[1]<=-2
            if (bArray[0] > -9 || bArray[0] < -80 || bArray[1] > -2
                    || bArray[1] < -95)
                return -1;
            // 返回对应汉字的位移
            return 94 * (bArray[0] + 80) + (bArray[1] + 95);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return -1;
        }
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
        // 初始化词典
        if (diagramDic == null)
            diagramDic = new short[ARRAY_SIZE][ARRAY_SIZE];
        if (otherDic == null)
            otherDic = new HashDictionary();

        if (word == null || StringUtils.isBlank(word))
            return;
        // 去除多于空格
        word = word.trim();
        // 判断是否单字词
        if (word.length() < 2)
            return;

        // 词长为2
        if (word.length() == 2)
        {
            // 计算位移
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // 判断字符1、2是否为GB2312编码中的汉字
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return;
            this.setDiagram(firstIndex, secondIndex, true);
        }
        else
            otherDic.insertWord(word);
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
        // 初始化词典
        this.diagramDic = new short[ARRAY_SIZE][ARRAY_SIZE];
        this.otherDic = new HashDictionary();
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
                    this.insertWord(word);
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
        if (diagramDic == null)
            return false;

        if (word == null || StringUtils.isBlank(word))
            return false;

        // 去除多于空格
        word = word.trim();
        // 过滤单字词操作
        if (word.length() < 2)
            return false;

        if (word.length() == 2)
        {
            // 计算位移
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // 判断字符1、2是否为GB2312编码中的汉字
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return false;
            return this.getDiagram(firstIndex, secondIndex);
        }
        else
            return otherDic.match(word);
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
        if (diagramDic == null)
            return;
        for (int i = 0; i < CHAR_SIZE; i++)
            for (int j = 0; j < CHAR_SIZE; j++)
            {
                if (this.getDiagram(i, j))
                {
                    out.print(this.getChar(i));
                    out.println(this.getChar(j));
                }
            }
        otherDic.print(out);
        out.flush();
    }

    /**
     * 添加或删除位移为firstIndex，secondIndex的词组
     * 
     * @param firstIndex
     *            二字词首字的位移
     * @param secondIndex
     *            二字词末字的位移
     * @param value
     *            true添加词组，false删除词组
     */
    protected void setDiagram(int firstIndex, int secondIndex, boolean value)
    {
        // 基数
        short base = 2;

        // 计算首字在二维数组中的位置
        int firstArray = firstIndex / 4;
        int firstOffset = firstIndex % 4;
        // 计算末字在二维数组中的位置
        int secondArray = secondIndex / 4;
        int secondOffset = secondIndex % 4;

        // 获取二维数组中的数值
        short num = this.diagramDic[firstArray][secondArray];
        // 计算offset位于short类型数值第几位（bit）
        int pos = firstOffset + 4 * secondOffset;
        // 根据value值设置num数值
        if (value)
            num |= (short) Math.pow(base, pos);
        else
            num &= (~(short) Math.pow(base, pos));
        // 重新设置数值
        this.diagramDic[firstArray][secondArray] = num;
    }

}
