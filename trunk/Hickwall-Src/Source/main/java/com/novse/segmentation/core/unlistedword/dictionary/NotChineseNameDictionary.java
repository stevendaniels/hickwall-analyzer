/*
 * @作者:Hades , 创建日期:2007-5-21
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

import com.novse.segmentation.core.matching.dictionary.AbstractDictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;

/**
 * @author Mac Kwan 用于记录以中文姓氏为首但并非中文姓名的词典操作类
 */
public class NotChineseNameDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * 
     * @author Mac Kwan 中文姓氏开头但并非中文姓名的汉字统计信息
     */
    public static class NotChineseNameCharInfo implements Serializable
    {

        /**
         * <code>serialVersionUID</code> 的注释
         */
        private static final long serialVersionUID = 354751051371918550L;

        /**
         * 总共出现的频次
         */
        public int charFrequence = 0;

        /**
         * 作为词组中其他位置出现的频次
         */
        public int otherFrequence = 0;

        /**
         * 作为词组首字（伪姓氏）出现的频次
         */
        public int firstFrequence = 0;

        /**
         * 判断该汉字的频次信息是否均为非正数
         * 
         * @return
         */
        public boolean isBlank()
        {
            return charFrequence <= 0 && otherFrequence <= 0
                    && firstFrequence <= 0;
        }

    }

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 6644742138143868633L;

    /**
     * 所有伪姓氏出现的次数
     */
    private int firstCount = 0;

    /**
     * 所有其他用字出现的次数
     */
    private int otherCount = 0;

    /**
     * 中文姓氏词典
     */
    private ChineseFirstNameDictionary firstNameDic = null;

    /**
     * 中文姓名用字统计信息载体
     */
    private HashMap<String, NotChineseNameCharInfo> map = null;

    /**
     * 记录已载入的非名字库
     */
    private HashDictionary notNameDic = null;

    /**
     * @param firstNameDic
     *            中文姓氏词典
     */
    public NotChineseNameDictionary(ChineseFirstNameDictionary firstNameDic)
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
        if (isEmpty())
            return;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return;

        // 判断是否有此名字
        if (!this.notNameDic.match(word))
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
            NotChineseNameCharInfo firstNameInfo = this.map.get(firstName);
            if (firstNameInfo != null)
            {
                // 出现总频次自减
                if (firstNameInfo.charFrequence > 0)
                    firstNameInfo.charFrequence--;
                // 姓氏出现频次自减
                if (firstNameInfo.firstFrequence > 0)
                    firstNameInfo.firstFrequence--;
                // 重新放入中文姓名用字统计信息载体
                if (!firstNameInfo.isBlank())
                    this.map.put(firstName, firstNameInfo);
                else
                    this.map.remove(firstName);
            }
            else
                agree = false;
            // 所有姓氏出现频次自减
            this.firstCount--;

            if (agree)
            {
                // 名字处理
                for (int i = index; i < word.length(); i++)
                {
                    // 获取名字
                    String givenName = word.substring(i, i + 1);
                    NotChineseNameCharInfo givenNameInfo = this.map
                            .get(givenName);
                    if (givenNameInfo != null)
                    {
                        // 总出现频次自增
                        if (givenNameInfo.charFrequence > 0)
                            givenNameInfo.charFrequence--;
                        // 名字首字或末字频次自减
                        givenNameInfo.otherFrequence--;
                        // 重新放入中文姓名用字统计信息载体
                        if (!givenNameInfo.isBlank())
                            this.map.put(givenName, givenNameInfo);
                        else
                            this.map.remove(givenName);
                    }
                    else
                        agree = false;
                    // 所有名字用字出现次数自减
                    this.otherCount--;
                }
            }
            if (agree)
            {
                // 加入到名字库
                this.notNameDic.deleteWord(word);
            }
        }
    }

    /**
     * @return 返回 firstNameCount。
     */
    public int getFirstCount()
    {
        return firstCount;
    }

    /**
     * @return 返回 firstNameDic。
     */
    public ChineseFirstNameDictionary getFirstNameDic()
    {
        return firstNameDic;
    }

    /**
     * 获取汉字ch的伪姓名统计信息
     * 
     * @param ch
     *            汉字
     * @return 汉字的姓名统计信息
     */
    public NotChineseNameCharInfo getNotChineseNameCharInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return 返回 givenNameCount。
     */
    public int getOtherCount()
    {
        return otherCount;
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
            this.map = new HashMap<String, NotChineseNameCharInfo>();
        if (this.notNameDic == null)
            this.notNameDic = new HashDictionary();

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return;

        // 判断是否已有此中文姓名
        if (this.notNameDic.match(word))
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
            NotChineseNameCharInfo firstNameInfo;
            if (this.map.containsKey(firstName))
                firstNameInfo = this.map.get(firstName);
            else
                firstNameInfo = new NotChineseNameCharInfo();
            // 总出现频次自增
            firstNameInfo.charFrequence++;
            // 姓氏出现频次自增
            firstNameInfo.firstFrequence++;
            this.map.put(firstName, firstNameInfo);
            // 所有姓氏出现次数自增
            this.firstCount++;
            // 名字处理
            for (int i = index; i < word.length(); i++)
            {
                // 获取名字
                String givenName = word.substring(i, i + 1);
                NotChineseNameCharInfo givenNameInfo;
                if (this.map.containsKey(givenName))
                    givenNameInfo = this.map.get(givenName);
                else
                    givenNameInfo = new NotChineseNameCharInfo();
                // 总出现频次自增
                givenNameInfo.charFrequence++;
                // 名字首字或末字频次自增
                givenNameInfo.otherFrequence++;
                this.map.put(givenName, givenNameInfo);
                // 所有姓名用字出现次数自增
                this.otherCount++;
            }
            // 加入到名字库
            this.notNameDic.insertWord(word);
        }
    }

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    @Override
    public boolean isEmpty()
    {
        return firstNameDic == null || firstNameDic.isEmpty() || map == null
                || map.isEmpty() || notNameDic == null || notNameDic.isEmpty();
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
            this.map = new HashMap<String, NotChineseNameCharInfo>();
        if (this.notNameDic == null)
            this.notNameDic = new HashDictionary();
        this.firstCount = 0;
        this.otherCount = 0;

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
        // 判断是否已初始化名字记录
        if (isEmpty())
            return false;

        // 判断词汇是否为空字符串
        if (StringUtils.isBlank(word))
            return false;
        // 去除多余空格
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return false;

        return this.notNameDic.match(word);
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
        if (this.notNameDic == null || this.map == null)
            return;
        this.notNameDic.print(out);
    }
}
