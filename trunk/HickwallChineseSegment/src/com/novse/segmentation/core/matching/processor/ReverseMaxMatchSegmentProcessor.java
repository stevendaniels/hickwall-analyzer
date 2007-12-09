/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.novse.segmentation.core.AbstractSegmentProcessor;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan 反向最大匹配分词法操作类
 */
public class ReverseMaxMatchSegmentProcessor extends AbstractSegmentProcessor
{
    /**
     * 字符串分隔器
     */
    private StringTokenizer tokenizer = null;

    /**
     * 默认构造函数
     */
    public ReverseMaxMatchSegmentProcessor()
    {
        this.initSeperator();
    }

    /**
     * 以一个词典操作类实例为参数的构造函数
     * 
     * @param dic
     *            词典操作类实例
     */
    public ReverseMaxMatchSegmentProcessor(Dictionary dic)
    {
        this.dic = dic;
        this.initSeperator();
    }

    /**
     * 以一个词典操作类实例，与一个词汇抽取器实例为参数的构造函数
     * 
     * @param dic
     *            词典操作类实例
     * @param fetcher
     *            词汇抽取器实例
     */
    public ReverseMaxMatchSegmentProcessor(Dictionary dic, StringFetcher fetcher)
    {
        this.dic = dic;
        this.fetcher = fetcher;
        this.initSeperator();
    }

    /**
     * 对text文本进行分词，把结果保存为字符串链表
     * 
     * @param text
     *            待分词的文本
     * @return 分词结果
     */
    public List<String> textProcess(String text)
    {
        if (text == null)
            return null;

        // 初始化分词结果集
        ArrayList<String> result = new ArrayList<String>();
        // 临时词典
        List<String> tempWordList = null;
        Dictionary tempDic = null;
        if (this.fetcher != null)
        {
            tempWordList = this.fetcher.textFetch(text);
            try
            {
                tempDic = this.dic.getClass().newInstance();
                tempDic.insertWord(tempWordList);
            }
            catch (InstantiationException e)
            {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }

        }

        // 初始化分隔器
        this.tokenizer = new StringTokenizer(text.toLowerCase(), this.seperator);
        // 记录将处理的字符的在字符串中的位置
        int index = 0;
        // 待分词文本长度
        int textLen = text.length();

        // 将分隔符加入到结果集中
        if (index < textLen)
        {
            String seperate = text.substring(index, index + 1);
            // 判断是否为分隔符
            while (this.seperator.indexOf(seperate) >= 0)
            {
                result.add(seperate);
                if (++index < textLen)
                    seperate = text.substring(index, index + 1);
                else
                    break;
            }
            // end of while (this.seperator.indexOf(seperate) >= 0)
        }

        // 分隔文本
        while (tokenizer.hasMoreTokens())
        {
            // 获取分隔文本
            String token = tokenizer.nextToken();
            // 修改记录位置
            index += token.length();

            // 如果分隔文本为空则开始下一个循环
            if (token == null)
                continue;

            // 初始化变量
            int len = token.length(), start = 0, pos = len;
            int wordCount = 0;
            // 循环匹配
            while (pos > 0)
            {
                while (start < pos)
                {
                    // 判断start处字符是否为数字或英文字母
                    if (start < len
                            && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                    .charAt(start)) >= 0)
                    {
                        // 记录英语字母开始位置、英语字母结束位置
                        int englishEnd = start, englishStart = start;
                        while (englishEnd < len
                                && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                        .charAt(englishEnd)) >= 0)
                            englishEnd++;
                        // 判断当位置标签指向当前英文串首地址时将结果插入分词结果集
                        if (englishEnd == pos)
                        {
                            result.add(result.size() - wordCount, this
                                    .toDBC(token.substring(englishStart,
                                            englishEnd)));
                            wordCount++;
                            pos = start;
                            start = 0;
                        }
                    }
                    // end of if(CHAR_AND_NUM.indexOf(token.charAt(start))>=0)

                    if (pos >= 1)
                    {
                        // 判断pos位置上的汉字是否为“的”“了”
                        String s = token.substring(pos - 1, pos);
                        if (s.equals("的") || s.equals("了"))
                        {
                            result.add(result.size() - wordCount, s);
                            wordCount++;
                            pos--;
                            start = 0;
                        }
                    }

                    // 判断分段是否已分析完毕
                    if (start < pos)
                    {
                        // 汉字处理
                        String word = token.substring(start, pos);

                        // 临时词典匹配结果
                        boolean tempDicMatchResult = false;
                        // 查找临时词典
                        if (tempDic != null && tempDic.match(word))
                            tempDicMatchResult = true;
                        // 词典匹配结果
                        boolean dicMatchResult = tempDicMatchResult ? true
                                : dic.match(word);

                        if (dicMatchResult || tempDicMatchResult)
                        {
                            result.add(result.size() - wordCount, word);
                            wordCount++;
                            pos = start;
                            start = 0;
                        }
                        else
                        {
                            // 当判断到剩余单字时，将词加入到词库中
                            if (word.length() == 1)
                            {
                                result.add(result.size() - wordCount, word);
                                wordCount++;
                                pos = start;
                                start = 0;
                            }
                            else
                                start++;
                        }
                        // end of match
                    }
                    // end of if(start<pos)
                }
                // end of while (start < pos)
            }
            // end of while (pos > 0)

            // 将分隔符加入到结果集中
            if (index < textLen)
            {
                String seperate = text.substring(index, index + 1);
                // 判断是否为分隔符
                while (this.seperator.indexOf(seperate) >= 0)
                {
                    result.add(seperate);
                    if (++index < textLen)
                        seperate = text.substring(index, index + 1);
                    else
                        break;
                }
                // end of while (this.seperator.indexOf(seperate) >= 0)
            }
            // end of if (index < textLen)
        }
        // end of while (tokenizer.hasMoreTokens())
        return result;
    }

}
