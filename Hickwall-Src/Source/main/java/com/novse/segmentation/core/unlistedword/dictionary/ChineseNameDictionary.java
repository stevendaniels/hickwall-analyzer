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
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;
import com.novse.segmentation.core.matching.dictionary.AbstractDictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;

/**
 * @author Mac Kwan 中文姓名词典操作类
 */
public class ChineseNameDictionary extends AbstractDictionary implements
        Serializable, Loadable
{
    /**
     * 
     * @author Mac Kwan 中文姓名汉字统计信息
     */
    public static class ChineseNameCharInfo implements Serializable
    {

        /**
         * <code>serialVersionUID</code> 的注释
         */
        private static final long serialVersionUID = -2286081784979591303L;

        /**
         * 总共出现的频次
         */
        public int charFrequence = 0;

        /**
         * 作为中文姓名中名字出现的频次
         */
        public int givenNameFrequence = 0;

        /**
         * 作为中文姓名姓氏出现的频次
         */
        public int firstNameFrequence = 0;

        /**
         * 判断该汉字的频次信息是否均为非正数
         * 
         * @return
         */
        public boolean isBlank()
        {
            return charFrequence <= 0 && givenNameFrequence <= 0
                    && firstNameFrequence <= 0;
        }

    }

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 4346774580395998242L;

    /**
     * 所有姓氏出现的次数
     */
    private int firstNameCount = 0;

    /**
     * 所有名字用字出现的次数
     */
    private int givenNameCount = 0;

    /**
     * 中文姓氏词典
     */
    private ChineseFirstNameDictionary firstNameDic = null;

    /**
     * 中文姓名用字统计信息载体
     */
    private HashMap<String, ChineseNameCharInfo> map = null;

    /**
     * 记录已载入的名字库
     */
    private HashDictionary nameDic = null;

    /**
     * @param firstNameDic
     *            中文姓氏词典
     */
    public ChineseNameDictionary(ChineseFirstNameDictionary firstNameDic)
    {
        this.firstNameDic = firstNameDic;
    }

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word)
    {
        // 判断是否已初始化名字记录
        if (this.nameDic == null || this.map == null)
            return;
        if (word == null || !StringUtils.isBlank(word))
            return;
        if (word.length() > 4 || word.length() < 2)
            return;
        // 判断是否有此名字
        if (!this.nameDic.match(word))
            return;

        // 开始删除中文姓名
        // 尝试是否为双字姓氏姓名
        String firstName = word.substring(0, 2);
        int index = 0;
        if (this.firstNameDic.match(firstName))
        {
            // 双字姓氏姓名处理
            index = 2;
        }
        else
        {
            // 单字姓氏处理
            firstName = word.substring(0, 1);
            index = 1;
        }
        // 判断是否满足中文姓名命名规则
        if (word.length() - index == 1 || word.length() - index == 2)
        {
            // 下一步操作允许标志
            boolean agree = true;
            // 处理姓氏
            ChineseNameCharInfo firstNameInfo = this.map.get(firstName);
            if (firstNameInfo != null)
            {
                // 出现总频次自减
                if (firstNameInfo.charFrequence > 0)
                    firstNameInfo.charFrequence--;
                // 姓氏出现频次自减
                if (firstNameInfo.firstNameFrequence > 0)
                    firstNameInfo.firstNameFrequence--;
                // 重新放入中文姓名用字统计信息载体
                if (!firstNameInfo.isBlank())
                    this.map.put(firstName, firstNameInfo);
                else
                    this.map.remove(firstName);
            }
            else
                agree = false;
            // 所有姓氏出现频次自减
            this.firstNameCount--;

            if (agree)
            {
                // 名字处理
                for (int i = index; i < word.length(); i++)
                {
                    // 获取名字
                    String givenName = word.substring(i, i + 1);
                    ChineseNameCharInfo givenNameInfo = this.map.get(givenName);
                    if (givenNameInfo != null)
                    {
                        // 总出现频次自增
                        if (givenNameInfo.charFrequence > 0)
                            givenNameInfo.charFrequence--;
                        // 名字首字或末字频次自减
                        givenNameInfo.givenNameFrequence--;
                        // 重新放入中文姓名用字统计信息载体
                        if (!givenNameInfo.isBlank())
                            this.map.put(givenName, givenNameInfo);
                        else
                            this.map.remove(givenName);
                    }
                    else
                        agree = false;
                    // 所有名字用字出现次数自减
                    this.givenNameCount--;
                }
            }
            if (agree)
            {
                // 加入到名字库
                this.nameDic.deleteWord(word);
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
        if (this.map == null)
            this.map = new HashMap<String, ChineseNameCharInfo>();
        if (this.nameDic == null)
            this.nameDic = new HashDictionary();
        if (word == null || StringUtils.isBlank(word))
            return;
        if (word.length() > 4 || word.length() < 2)
            return;
        // 判断是否已有此中文姓名
        if (this.nameDic.match(word))
            return;

        // 开始插入中文姓名
        // 尝试是否为双字姓氏姓名
        String firstName = word.substring(0, 2);
        int index = 0;
        if (this.firstNameDic.match(firstName))
        {
            // 双字姓氏姓名处理
            index = 2;
        }
        else
        {
            // 单字姓氏处理
            firstName = word.substring(0, 1);
            index = 1;
        }
        // 判断是否满足中文姓名命名规则
        if (word.length() - index == 1 || word.length() - index == 2)
        {
            // 处理姓氏
            ChineseNameCharInfo firstNameInfo;
            if (this.map.containsKey(firstName))
                firstNameInfo = this.map.get(firstName);
            else
                firstNameInfo = new ChineseNameCharInfo();
            // 总出现频次自增
            firstNameInfo.charFrequence++;
            // 姓氏出现频次自增
            firstNameInfo.firstNameFrequence++;
            this.map.put(firstName, firstNameInfo);
            // 所有姓氏出现次数自增
            this.firstNameCount++;
            // 名字处理
            for (int i = index; i < word.length(); i++)
            {
                // 获取名字
                String givenName = word.substring(i, i + 1);
                ChineseNameCharInfo givenNameInfo;
                if (this.map.containsKey(givenName))
                    givenNameInfo = this.map.get(givenName);
                else
                    givenNameInfo = new ChineseNameCharInfo();
                // 总出现频次自增
                givenNameInfo.charFrequence++;
                // 名字首字或末字频次自增
                givenNameInfo.givenNameFrequence++;
                this.map.put(givenName, givenNameInfo);
                // 所有姓名用字出现次数自增
                this.givenNameCount++;
            }
            // 加入到名字库
            this.nameDic.insertWord(word);
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
        // 初始化词典
        if (this.map == null)
            this.map = new HashMap<String, ChineseNameCharInfo>();
        if (this.nameDic == null)
            this.nameDic = new HashDictionary();
        this.firstNameCount = 0;
        this.givenNameCount = 0;

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
        // 判断是否已初始化名字记录
        if (nameDic == null || map == null)
            return false;
        if (word == null || StringUtils.isBlank(word))
            return false;
        if (word.length() > 4 || word.length() < 2)
            return false;
        return this.nameDic.match(word);
    }

    /**
     * 输出已载入内存中所有词汇
     * 
     * @param out
     *            输出流
     */
    public void print(PrintStream out)
    {
        // 判断是否已初始化名字记录
        if (this.nameDic == null || this.map == null)
            return;
        this.nameDic.print(out);
    }

    /**
     * @return 返回 firstNameDic。
     */
    public ChineseFirstNameDictionary getFirstNameDic()
    {
        return firstNameDic;
    }

    /**
     * 获取汉字ch的姓名统计信息
     * 
     * @param ch
     *            汉字
     * @return 汉字的姓名统计信息
     */
    public ChineseNameCharInfo getChineseNameCharInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return 返回 firstNameCount。
     */
    public int getFirstNameCount()
    {
        return firstNameCount;
    }

    /**
     * @return 返回 givenNameCount。
     */
    public int getGivenNameCount()
    {
        return givenNameCount;
    }
}
