/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
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
 * @author Mac Kwan 正向最大匹配分词法操作类
 */
public class MaxMatchSegmentProcessor extends AbstractSegmentProcessor
{
    /**
     * 字符串分隔器
     */
    private StringTokenizer tokenizer = null;

    /**
     * 默认构造函数
     */
    public MaxMatchSegmentProcessor()
    {
        this.initSeperator();
    }

    /**
     * 以一个词典操作类实例为参数的构造函数
     * 
     * @param dic
     *            词典操作类实例
     */
    public MaxMatchSegmentProcessor(Dictionary dic)
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
    public MaxMatchSegmentProcessor(Dictionary dic, StringFetcher fetcher)
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

        // 初始化结果链表
        ArrayList<String> result = new ArrayList<String>();
        // 临时词典
        List<String> tempWordList = null;
        Dictionary tempDic = null;
        if (this.fetcher != null)
        {
            tempWordList = this.fetcher.textFetch(text);
            try
            {
                tempDic = dic.getClass().newInstance();
                tempDic.insertWord(tempWordList);
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        // 对待分词文本进行分隔
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
            String token = tokenizer.nextToken();
            // 修改记录位置
            index += token.length();

            // 判断分隔文本是否为null
            if (token == null)
                continue;

            // 初始化位置标签
            int pos = 0;
            // 当前分隔文本长度
            int len = token.length();
            // 结尾位置
            int end = len;
            // 循环匹配
            while (pos < len)
            {
                while (end > pos)
                {
                    // 判断end处字符是否为数字或英文字母
                    if (end > 0
                            && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                    .charAt(end - 1)) >= 0)
                    {
                        // 记录英语字母开始位置、英语字母结束位置
                        int englishEnd = end, englishStart = end;
                        while (englishStart > 0
                                && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                        .charAt(englishStart - 1)) >= 0)
                            englishStart--;
                        // 判断当位置标签指向当前英文串首地址时将结果插入分词结果集
                        if (pos == englishStart)
                        {
                            result.add(this.toDBC(token.substring(englishStart,
                                    englishEnd)));
                            pos = end;
                            end = len;
                        }
                    }
                    // end of 判断end处字符是否为数字或英文字母

                    // 判断分段是否已分析完毕
                    if (end > pos)
                    {
                        // 汉字处理
                        String word = token.substring(pos, end);

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
                            // 去除末端以“的”“了”结尾的词
                            if (word.endsWith("的") || word.endsWith("了"))
                            {
                                result
                                        .add(word.substring(0,
                                                word.length() - 1));
                                result.add(word.substring(word.length() - 1,
                                        word.length()));
                            }
                            else
                                result.add(word);
                            pos = end;
                            end = len;
                        }
                        else
                        {
                            // 当判断到剩余单字时，将词加入到词库中
                            if (word.length() == 1)
                            {
                                result.add(word);
                                pos = end;
                                end = len;
                            }
                            else
                                end--;
                        }
                        // end of match
                    }
                    // end of if(end>pos)
                }
                // end of while (end > pos)
            }
            // end of while (pos < len)

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
