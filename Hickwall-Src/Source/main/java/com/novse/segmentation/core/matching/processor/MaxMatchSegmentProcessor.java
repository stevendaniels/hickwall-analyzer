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
 * @author Mac Kwan �������ƥ��ִʷ�������
 */
public class MaxMatchSegmentProcessor extends AbstractSegmentProcessor
{
    /**
     * �ַ����ָ���
     */
    private StringTokenizer tokenizer = null;

    /**
     * Ĭ�Ϲ��캯��
     */
    public MaxMatchSegmentProcessor()
    {
        this.initSeperator();
    }

    /**
     * ��һ���ʵ������ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param dic
     *            �ʵ������ʵ��
     */
    public MaxMatchSegmentProcessor(Dictionary dic)
    {
        this.dic = dic;
        this.initSeperator();
    }

    /**
     * ��һ���ʵ������ʵ������һ���ʻ��ȡ��ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param dic
     *            �ʵ������ʵ��
     * @param fetcher
     *            �ʻ��ȡ��ʵ��
     */
    public MaxMatchSegmentProcessor(Dictionary dic, StringFetcher fetcher)
    {
        this.dic = dic;
        this.fetcher = fetcher;
        this.initSeperator();
    }

    /**
     * ��text�ı����зִʣ��ѽ������Ϊ�ַ�������
     * 
     * @param text
     *            ���ִʵ��ı�
     * @return �ִʽ��
     */
    public List<String> textProcess(String text)
    {
        if (text == null)
            return null;

        // ��ʼ���������
        ArrayList<String> result = new ArrayList<String>();
        // ��ʱ�ʵ�
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

        // �Դ��ִ��ı����зָ�
        this.tokenizer = new StringTokenizer(text.toLowerCase(), this.seperator);
        // ��¼��������ַ������ַ����е�λ��
        int index = 0;
        // ���ִ��ı�����
        int textLen = text.length();

        // ���ָ������뵽�������
        if (index < textLen)
        {
            String seperate = text.substring(index, index + 1);
            // �ж��Ƿ�Ϊ�ָ���
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

        // �ָ��ı�
        while (tokenizer.hasMoreTokens())
        {
            String token = tokenizer.nextToken();
            // �޸ļ�¼λ��
            index += token.length();

            // �жϷָ��ı��Ƿ�Ϊnull
            if (token == null)
                continue;

            // ��ʼ��λ�ñ�ǩ
            int pos = 0;
            // ��ǰ�ָ��ı�����
            int len = token.length();
            // ��βλ��
            int end = len;
            // ѭ��ƥ��
            while (pos < len)
            {
                while (end > pos)
                {
                    // �ж�end���ַ��Ƿ�Ϊ���ֻ�Ӣ����ĸ
                    if (end > 0
                            && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                    .charAt(end - 1)) >= 0)
                    {
                        // ��¼Ӣ����ĸ��ʼλ�á�Ӣ����ĸ����λ��
                        int englishEnd = end, englishStart = end;
                        while (englishStart > 0
                                && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                        .charAt(englishStart - 1)) >= 0)
                            englishStart--;
                        // �жϵ�λ�ñ�ǩָ��ǰӢ�Ĵ��׵�ַʱ���������ִʽ����
                        if (pos == englishStart)
                        {
                            result.add(this.toDBC(token.substring(englishStart,
                                    englishEnd)));
                            pos = end;
                            end = len;
                        }
                    }
                    // end of �ж�end���ַ��Ƿ�Ϊ���ֻ�Ӣ����ĸ

                    // �жϷֶ��Ƿ��ѷ������
                    if (end > pos)
                    {
                        // ���ִ���
                        String word = token.substring(pos, end);

                        // ��ʱ�ʵ�ƥ����
                        boolean tempDicMatchResult = false;
                        // ������ʱ�ʵ�
                        if (tempDic != null && tempDic.match(word))
                            tempDicMatchResult = true;
                        // �ʵ�ƥ����
                        boolean dicMatchResult = tempDicMatchResult ? true
                                : dic.match(word);

                        if (dicMatchResult || tempDicMatchResult)
                        {
                            // ȥ��ĩ���ԡ��ġ����ˡ���β�Ĵ�
                            if (word.endsWith("��") || word.endsWith("��"))
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
                            // ���жϵ�ʣ�൥��ʱ�����ʼ��뵽�ʿ���
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

            // ���ָ������뵽�������
            if (index < textLen)
            {
                String seperate = text.substring(index, index + 1);
                // �ж��Ƿ�Ϊ�ָ���
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
