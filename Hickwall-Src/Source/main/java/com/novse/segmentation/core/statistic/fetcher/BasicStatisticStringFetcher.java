/*
 * @����:Hades , ��������:2006-12-28
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.novse.segmentation.core.statistic.basic.BasicStatisticPostProcessor;

/**
 * @author Mac Kwan ����ͳ�Ƶĸ�Ƶ�ʻ��ȡ��
 */
public class BasicStatisticStringFetcher extends AbstractStringFetcher
{
    /**
     * @author Mac Kwan �ڲ��࣬���ڼ�¼ÿ�������ڴ���ȡ�ı��г��ֵĴ�����λ�õ���Ϣ
     */
    static public class CharInfo
    {
        /**
         * ��¼���ֳ��ֵĴ���
         */
        public int frequence = 0;

        /**
         * ��ǰ�����Ƿ��Ѵ���
         */
        public boolean hasProcessed = false;

        /**
         * �������ı��г��ֵ�λ������
         */
        public LinkedList<Integer> posArray = new LinkedList<Integer>();
    }

    /**
     * 
     * @author Mac Kwan �ڲ��࣬���ڱ����Ƶ�ִ�
     */
    static public class WordInfo
    {
        /**
         * ����Ƶ��
         */
        public int frequence = 0;

        /**
         * ��ֵ
         */
        public double value = 0.0;

        /**
         * ��Ƶ�ִ�
         */
        public String word = null;
    }

    /**
     * ����ı��г��ֵĺ�����Ϣ
     */
    private HashMap<Character, CharInfo> indexMap = null;

    /**
     * ǰ׺��׺������
     */
    private BasicStatisticPostProcessor postProcessor = null;

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param postProcessor
     *            ǰ׺��׺������
     */
    public BasicStatisticStringFetcher(BasicStatisticPostProcessor postProcessor)
    {
        this.postProcessor = postProcessor;
        this.initSeperator();
    }

    /**
     * �Զ���ָ����Ĺ��캯��
     * 
     * @param postProcessor
     *            ǰ׺��׺������
     * @param seperator
     *            �Զ���ָ���
     */
    public BasicStatisticStringFetcher(String seperator,
            BasicStatisticPostProcessor postProcessor)
    {
        this.postProcessor = postProcessor;
        this.seperator = seperator;
    }

    /**
     * �Ӵ������ļ�srcFile�г�ȡ�ʻ�
     * 
     * @param srcFile
     *            �������ļ�
     * @return ��ȡ���ôʻ�
     */
    public List<String> fileFetch(String srcFile)
    {
        List<String> result = null;
        try
        {
            // ��ʼ������
            BufferedReader in = new BufferedReader(new FileReader(srcFile));

            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null)
            {
                if (line.length() > 1)
                    buffer.append(line.trim());
            }
            in.close();

            result = this.textFetch(buffer.toString());
            // ���ؽ��
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ���ؽ��
            return result;
        }
    }

    /**
     * ���ݴ������ı��ĳ��Ȼ�ȡ��Ƶ������
     * 
     * @param text
     *            �������ı�
     * @return ��Ƶ����
     */
    protected int getFreLevel(String text)
    {
        // ��ʼ�����
        int result = 0;
        // ��ȡ�ı�����
        int l = text.length();

        if (l <= 500)
            result = 2;
        else if (l <= 2000)
            result = 3;
        else if (l <= 10000)
            result = 4;
        else
            result = 5;

        return result;
    }

    /**
     * ��ȡ��Ƶ�ִ�
     * 
     * @param text
     *            �������ı�
     * @throws Exception
     */
    protected List<WordInfo> getHighFrequenceString(String text)
            throws Exception
    {
        if (text == null)
            throw new Exception("�������ı�Ϊnull");
        if (this.indexMap == null)
            throw new Exception("��δ����Ԥ����������indexMapΪnull");

        // ��ʼ���������
        ArrayList<WordInfo> result = new ArrayList<WordInfo>();

        // ��ʼ�������ַ�������
        ArrayList<String> strArray = new ArrayList<String>();

        // �����ı��������ַ�
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);

            // �����ָ���
            if (!this.indexMap.containsKey(ch))
                continue;

            CharInfo info = this.indexMap.get(ch);

            // �жϵ�ǰ�ַ��Ƿ��Ѵ���
            if (info.hasProcessed)
                continue;
            // �жϵ�ǰ�ַ�Ƶ���Ƿ����1
            if (info.frequence == 1)
            {
                info.hasProcessed = true;
                this.indexMap.put(ch, info);
                continue;
            }

            // ������ǰ�ַ����ı��г��ֵ�λ��
            for (int p : info.posArray)
            {
                // ��һ��λ��
                int nextPos = p + 1;

                // �ж��Ƿ񵽴��ı���β
                if (nextPos >= text.length())
                    continue;

                // ��ȡ��������
                char nextCh = text.charAt(nextPos);

                // ��ʼ����Ƶ�ִ�����
                StringBuffer buffer = new StringBuffer("" + ch);
                while (this.indexMap.containsKey(nextCh))
                {
                    CharInfo nextInfo = this.indexMap.get(nextCh);
                    // ����������Ƶ�δ���1����δ����
                    if (nextInfo.frequence > 1 && !nextInfo.hasProcessed)
                    {
                        buffer.append(nextCh);
                        // �ж��Ƿ񵽴��ı���β
                        if (++nextPos < text.length())
                            nextCh = text.charAt(nextPos);
                        else
                            break;
                    }
                    else
                        break;
                }

                // ���뻺���ַ�������
                String word = buffer.toString().trim();
                if (word.length() > 1)
                    strArray.add(word);
            }
            // end of foreach

            // �Ի����ַ�����������
            Collections.sort(strArray);

            // ����Ϊ�Ѵ���
            info.hasProcessed = true;
            this.indexMap.put(ch, info);

            // ���������ַ�������
            for (int i = 0; i < strArray.size(); i++)
            {
                int j = i;
                // ͳ�Ƹ�Ƶ�ִ��Ĵ�Ƶ
                while (j < strArray.size()
                        && strArray.get(j).indexOf(strArray.get(i)) >= 0)
                    j++;
                if (j - i >= this.getFreLevel(text))
                {
                    WordInfo wordInfo = new WordInfo();
                    wordInfo.frequence = j - i;
                    wordInfo.word = strArray.get(i);
                    wordInfo.value = this.getWordValue(wordInfo.frequence,
                            wordInfo.word.length());
                    result.add(wordInfo);
                }
                i = j;
            }
            // end of for strArray

            // ��ջ�������
            strArray.clear();
        }
        // end of for
        return result;
    }

    /**
     * ���ݴ�Ƶ��ʳ�����Ȩ��
     * 
     * @param frequence
     *            ��Ƶ
     * @param strLen
     *            �ʳ�
     * @return Ȩ��
     */
    protected double getWordValue(int frequence, int strLen)
    {
        double result = 0;
        result = frequence + 3.0 / (double) strLen;
        return result;
    }

    /**
     * ǰ׺��׺����
     * 
     * @param wordList
     *            ������ĸ�Ƶ�ִ�����
     * @return �����ĸ�Ƶ�ִ�
     * @throws Exception
     */
    protected List<String> postProcess(List<WordInfo> wordList)
            throws Exception
    {
        if (wordList == null)
            throw new Exception("�������Ƶ�ִ�����wordListΪnull");

        // ǰ׺��׺����
        wordList = this.postProcessor.postProcess(wordList);

        // ������
        LinkedList<String> result = new LinkedList<String>();
        for (WordInfo w : wordList)
            result.add(w.word);
        return result;
    }

    /**
     * Ԥ��������ͳ�Ƶ�����text�ı��г��ֵ�Ƶ��
     * 
     * @param text
     *            �������ı�
     * @throws Exception
     */
    protected void preProcess(String text) throws Exception
    {
        if (text == null)
            throw new Exception("�������ı�Ϊnull");

        // ��ʼ��������
        this.indexMap = new HashMap<Character, CharInfo>();

        // ��һ�α����ı��������ַ�
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);
            // �жϵ�ǰ�ַ��Ƿ��Ƿָ���
            if (this.seperator.indexOf(ch) < 0)
            {
                // �ж��Ƿ��Ѵ��ڸ��ַ�������
                if (this.indexMap.containsKey(ch))
                {
                    CharInfo info = this.indexMap.get(ch);
                    info.frequence++;
                    this.indexMap.put(ch, info);
                }
                else
                {
                    CharInfo info = new CharInfo();
                    info.frequence++;
                    this.indexMap.put(ch, info);
                }
                // end of containsKey
            }
            // end of indexOf
        }
        // end of for

        // �ڶ��α����ı��������ַ�
        for (int pos = 0; pos < text.length(); pos++)
        {
            char ch = text.charAt(pos);
            // �����ָ���
            if (!this.indexMap.containsKey(ch))
                continue;
            CharInfo info = this.indexMap.get(ch);
            // ��ǰ�ַ����ִ�������1
            if (info.frequence > 1)
            {
                info.posArray.add(pos);
                this.indexMap.put(ch, info);
            }
            // end of >1
        }
        // end of for
    }

    /**
     * �Ӵ������ַ���doc�г�ȡ�ʻ�
     * 
     * @param doc
     *            �������ַ���
     * @return ��ȡ���ôʻ�
     */
    public List<String> textFetch(String text)
    {
        List<String> result = null;
        try
        {
            // �������
            this.indexMap = null;
            // Ԥ����

            this.preProcess(text.trim());

            // ��ȡ��Ƶ�ʻ�
            result = this.postProcess(this.getHighFrequenceString(text));
            // �������
            Collections.sort(result);
            // ���ش�����
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ���ش�����
            return result;
        }
    }
}
