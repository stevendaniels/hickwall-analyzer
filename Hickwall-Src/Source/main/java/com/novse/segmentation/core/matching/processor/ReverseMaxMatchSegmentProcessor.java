/*
 * @����:Hades , ��������:2006-11-17
 *
 * ��ͷ��ѧ03���������
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
public class ReverseMaxMatchSegmentProcessor extends AbstractSegmentProcessor
{
    /**
     * �ַ����ָ���
     */
    private StringTokenizer tokenizer = null;

    /**
     * Ĭ�Ϲ��캯��
     */
    public ReverseMaxMatchSegmentProcessor()
    {
        this.initSeperator();
    }

    /**
     * ��һ���ʵ������ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param dic
     *            �ʵ������ʵ��
     */
    public ReverseMaxMatchSegmentProcessor(Dictionary dic)
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
    public ReverseMaxMatchSegmentProcessor(Dictionary dic, StringFetcher fetcher)
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

        // ��ʼ���ִʽ����
        ArrayList<String> result = new ArrayList<String>();
        // ��ʱ�ʵ�
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
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }

        }

        // ��ʼ���ָ���
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
            // ��ȡ�ָ��ı�
            String token = tokenizer.nextToken();
            // �޸ļ�¼λ��
            index += token.length();

            // ����ָ��ı�Ϊ����ʼ��һ��ѭ��
            if (token == null)
                continue;

            // ��ʼ������
            int len = token.length(), start = 0, pos = len;
            int wordCount = 0;
            // ѭ��ƥ��
            while (pos > 0)
            {
                while (start < pos)
                {
                    // �ж�start���ַ��Ƿ�Ϊ���ֻ�Ӣ����ĸ
                    if (start < len
                            && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                    .charAt(start)) >= 0)
                    {
                        // ��¼Ӣ����ĸ��ʼλ�á�Ӣ����ĸ����λ��
                        int englishEnd = start, englishStart = start;
                        while (englishEnd < len
                                && StopWordMaker.CHAR_AND_NUM.indexOf(token
                                        .charAt(englishEnd)) >= 0)
                            englishEnd++;
                        // �жϵ�λ�ñ�ǩָ��ǰӢ�Ĵ��׵�ַʱ���������ִʽ����
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
                        // �ж�posλ���ϵĺ����Ƿ�Ϊ���ġ����ˡ�
                        String s = token.substring(pos - 1, pos);
                        if (s.equals("��") || s.equals("��"))
                        {
                            result.add(result.size() - wordCount, s);
                            wordCount++;
                            pos--;
                            start = 0;
                        }
                    }

                    // �жϷֶ��Ƿ��ѷ������
                    if (start < pos)
                    {
                        // ���ִ���
                        String word = token.substring(start, pos);

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
                            result.add(result.size() - wordCount, word);
                            wordCount++;
                            pos = start;
                            start = 0;
                        }
                        else
                        {
                            // ���жϵ�ʣ�൥��ʱ�����ʼ��뵽�ʿ���
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
