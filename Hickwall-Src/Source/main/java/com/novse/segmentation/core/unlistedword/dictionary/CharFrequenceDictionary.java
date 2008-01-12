/*
 * @����:Hades , ��������:2007-4-6
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.unlistedword.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;

/**
 * @author Mac Kwan ���ڼ�¼������ʵ�������г��ִ����Ĳ�����
 */
public class CharFrequenceDictionary implements Loadable, Serializable
{
    /**
     * 
     * @author Mac Kwan ���ڼ�¼����ͳ����Ϣ��ʵ��
     * 
     */
    public static class CharFrequenceInfo implements Serializable
    {
        /**
         * <code>serialVersionUID</code> ��ע��
         */
        private static final long serialVersionUID = -3687085604926867396L;

        /**
         * ����ch���ֵĴ���
         */
        public int charFrequence = 0;

        /**
         * ����ch��Ϊ�ʻ����ֳ��ֵĴ���
         */
        public int firstCharFrequence = 0;

        /**
         * ����ch��Ϊ���ִʳ��ֵĴ���
         */
        public int singleCharFrequnece = 0;
    }

    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -3656409214613561989L;

    /**
     * ��¼�ܹ�������
     */
    private int charCount = 0;

    /**
     * ��¼���ֳ��ִ��������ͱ�
     */
    private HashMap<String, CharFrequenceInfo> map = null;

    /**
     * ��¼�ܹ��Ĵʻ���
     */
    private int wordCount = 0;

    /**
     * @return ���� charCount��
     */
    public int getCharCount()
    {
        return charCount;
    }

    /**
     * ���غ���ch��ͳ����Ϣ
     * 
     * @param ch
     *            ����
     * @return ��Ӧ��ͳ����Ϣ
     */
    public CharFrequenceInfo getInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return �ܴʻ�����
     */
    public int getWordCount()
    {
        return wordCount;
    }

    /**
     * ��ָ�����ı��ļ�srcFile�������Ƶ��Ϣ
     * 
     * @param fileName
     *            װ�ش�Ƶ��Ϣ���ı��ļ�
     */
    public void loadDictionary(String fileName)
    {
        try
        {
            // ��ʼ���Ķ���
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            // ��ʼ�����ͱ�
            this.map = new HashMap<String, CharFrequenceInfo>();

            String line = null;
            // �����ļ�
            while ((line = in.readLine()) != null)
            {
                // �ж��Ƿ���ַ���
                if (!StringUtils.isBlank(line))
                {
                    // �ָ��ַ�
                    String[] array = line.split("\t");
                    if (array.length == 2)
                    {
                        // ��ȡ�ʻ�
                        String word = array[0];
                        // ��ȡ�ʻ���ֵĴ���
                        int frequence = Integer.parseInt(array[1]);

                        // �����ʻ���
                        this.wordCount += frequence;
                        // ��������
                        this.charCount += (word.length() * frequence);

                        // �ж��Ƿ�Ϊ���ִ�
                        if (word.length() == 1)
                        {
                            CharFrequenceInfo info = null;
                            // �ж��Ƿ��Ѽ�¼�õ���
                            if (this.map.containsKey(word))
                                info = this.map.get(word);
                            else
                                info = new CharFrequenceInfo();
                            // �������ִ���
                            info.singleCharFrequnece += frequence;
                            info.charFrequence += frequence;
                            this.map.put(word, info);
                        }
                        else if (word.length() > 1)
                        {
                            // �������к���
                            for (int i = 0; i < word.length(); i++)
                            {
                                // ��ȡ����ch
                                String ch = word.substring(i, i + 1);
                                CharFrequenceInfo info = null;
                                // �ж��Ƿ��Ѽ�¼�õ���
                                if (this.map.containsKey(ch))
                                    info = this.map.get(ch);
                                else
                                    info = new CharFrequenceInfo();
                                // �������ִ���
                                if (i == 0)
                                    info.firstCharFrequence += frequence;
                                info.charFrequence += frequence;
                                this.map.put(ch, info);
                            }
                        }
                        // end of if (word.length() == 1)
                    }
                    // end of if (array.length == 2)
                }
            }
            // �ر��Ķ���
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
