/*
 * @����:Hades , ��������:May 15, 2007
 *
 * ��ͷ��ѧ03���������
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
 * @author Mac Kwan ���������ʵ������
 */
public class ChineseNameDictionary extends AbstractDictionary implements
        Serializable, Loadable
{
    /**
     * 
     * @author Mac Kwan ������������ͳ����Ϣ
     */
    public static class ChineseNameCharInfo implements Serializable
    {

        /**
         * <code>serialVersionUID</code> ��ע��
         */
        private static final long serialVersionUID = -2286081784979591303L;

        /**
         * �ܹ����ֵ�Ƶ��
         */
        public int charFrequence = 0;

        /**
         * ��Ϊ�������������ֳ��ֵ�Ƶ��
         */
        public int givenNameFrequence = 0;

        /**
         * ��Ϊ�����������ϳ��ֵ�Ƶ��
         */
        public int firstNameFrequence = 0;

        /**
         * �жϸú��ֵ�Ƶ����Ϣ�Ƿ��Ϊ������
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
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 4346774580395998242L;

    /**
     * �������ϳ��ֵĴ���
     */
    private int firstNameCount = 0;

    /**
     * �����������ֳ��ֵĴ���
     */
    private int givenNameCount = 0;

    /**
     * �������ϴʵ�
     */
    private ChineseFirstNameDictionary firstNameDic = null;

    /**
     * ������������ͳ����Ϣ����
     */
    private HashMap<String, ChineseNameCharInfo> map = null;

    /**
     * ��¼����������ֿ�
     */
    private HashDictionary nameDic = null;

    /**
     * @param firstNameDic
     *            �������ϴʵ�
     */
    public ChineseNameDictionary(ChineseFirstNameDictionary firstNameDic)
    {
        this.firstNameDic = firstNameDic;
    }

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    public void deleteWord(String word)
    {
        // �ж��Ƿ��ѳ�ʼ�����ּ�¼
        if (this.nameDic == null || this.map == null)
            return;
        if (word == null || !StringUtils.isBlank(word))
            return;
        if (word.length() > 4 || word.length() < 2)
            return;
        // �ж��Ƿ��д�����
        if (!this.nameDic.match(word))
            return;

        // ��ʼɾ����������
        // �����Ƿ�Ϊ˫����������
        String firstName = word.substring(0, 2);
        int index = 0;
        if (this.firstNameDic.match(firstName))
        {
            // ˫��������������
            index = 2;
        }
        else
        {
            // �������ϴ���
            firstName = word.substring(0, 1);
            index = 1;
        }
        // �ж��Ƿ���������������������
        if (word.length() - index == 1 || word.length() - index == 2)
        {
            // ��һ�����������־
            boolean agree = true;
            // ��������
            ChineseNameCharInfo firstNameInfo = this.map.get(firstName);
            if (firstNameInfo != null)
            {
                // ������Ƶ���Լ�
                if (firstNameInfo.charFrequence > 0)
                    firstNameInfo.charFrequence--;
                // ���ϳ���Ƶ���Լ�
                if (firstNameInfo.firstNameFrequence > 0)
                    firstNameInfo.firstNameFrequence--;
                // ���·���������������ͳ����Ϣ����
                if (!firstNameInfo.isBlank())
                    this.map.put(firstName, firstNameInfo);
                else
                    this.map.remove(firstName);
            }
            else
                agree = false;
            // �������ϳ���Ƶ���Լ�
            this.firstNameCount--;

            if (agree)
            {
                // ���ִ���
                for (int i = index; i < word.length(); i++)
                {
                    // ��ȡ����
                    String givenName = word.substring(i, i + 1);
                    ChineseNameCharInfo givenNameInfo = this.map.get(givenName);
                    if (givenNameInfo != null)
                    {
                        // �ܳ���Ƶ������
                        if (givenNameInfo.charFrequence > 0)
                            givenNameInfo.charFrequence--;
                        // �������ֻ�ĩ��Ƶ���Լ�
                        givenNameInfo.givenNameFrequence--;
                        // ���·���������������ͳ����Ϣ����
                        if (!givenNameInfo.isBlank())
                            this.map.put(givenName, givenNameInfo);
                        else
                            this.map.remove(givenName);
                    }
                    else
                        agree = false;
                    // �����������ֳ��ִ����Լ�
                    this.givenNameCount--;
                }
            }
            if (agree)
            {
                // ���뵽���ֿ�
                this.nameDic.deleteWord(word);
            }
        }
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    public void insertWord(String word)
    {
        // ��ʼ���ʵ�
        if (this.map == null)
            this.map = new HashMap<String, ChineseNameCharInfo>();
        if (this.nameDic == null)
            this.nameDic = new HashDictionary();
        if (word == null || StringUtils.isBlank(word))
            return;
        if (word.length() > 4 || word.length() < 2)
            return;
        // �ж��Ƿ����д���������
        if (this.nameDic.match(word))
            return;

        // ��ʼ������������
        // �����Ƿ�Ϊ˫����������
        String firstName = word.substring(0, 2);
        int index = 0;
        if (this.firstNameDic.match(firstName))
        {
            // ˫��������������
            index = 2;
        }
        else
        {
            // �������ϴ���
            firstName = word.substring(0, 1);
            index = 1;
        }
        // �ж��Ƿ���������������������
        if (word.length() - index == 1 || word.length() - index == 2)
        {
            // ��������
            ChineseNameCharInfo firstNameInfo;
            if (this.map.containsKey(firstName))
                firstNameInfo = this.map.get(firstName);
            else
                firstNameInfo = new ChineseNameCharInfo();
            // �ܳ���Ƶ������
            firstNameInfo.charFrequence++;
            // ���ϳ���Ƶ������
            firstNameInfo.firstNameFrequence++;
            this.map.put(firstName, firstNameInfo);
            // �������ϳ��ִ�������
            this.firstNameCount++;
            // ���ִ���
            for (int i = index; i < word.length(); i++)
            {
                // ��ȡ����
                String givenName = word.substring(i, i + 1);
                ChineseNameCharInfo givenNameInfo;
                if (this.map.containsKey(givenName))
                    givenNameInfo = this.map.get(givenName);
                else
                    givenNameInfo = new ChineseNameCharInfo();
                // �ܳ���Ƶ������
                givenNameInfo.charFrequence++;
                // �������ֻ�ĩ��Ƶ������
                givenNameInfo.givenNameFrequence++;
                this.map.put(givenName, givenNameInfo);
                // �����������ֳ��ִ�������
                this.givenNameCount++;
            }
            // ���뵽���ֿ�
            this.nameDic.insertWord(word);
        }
    }

    /**
     * �������ı���ʽ�洢�Ĵʵ�
     * 
     * @param fileName
     *            �ʵ���ļ���
     */
    public void loadDictionary(String fileName)
    {
        // ��ʼ���ʵ�
        if (this.map == null)
            this.map = new HashMap<String, ChineseNameCharInfo>();
        if (this.nameDic == null)
            this.nameDic = new HashDictionary();
        this.firstNameCount = 0;
        this.givenNameCount = 0;

        try
        {
            // ��ʼ��������
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String word = null;

            // ��ȡ�ʵ�
            while ((word = in.readLine()) != null)
            {
                // ����ʻ�
                if (!StringUtils.isBlank(word))
                    this.insertWord(word.trim());
            }
            // �ر�������
            in.close();
        }
        catch (IOException e)
        {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
    }

    /**
     * �ж�������ַ����Ƿ��ڴʵ���
     * 
     * @param word
     *            ���ж��ַ���
     * @return �жϽ��
     */
    public boolean match(String word)
    {
        // �ж��Ƿ��ѳ�ʼ�����ּ�¼
        if (nameDic == null || map == null)
            return false;
        if (word == null || StringUtils.isBlank(word))
            return false;
        if (word.length() > 4 || word.length() < 2)
            return false;
        return this.nameDic.match(word);
    }

    /**
     * ����������ڴ������дʻ�
     * 
     * @param out
     *            �����
     */
    public void print(PrintStream out)
    {
        // �ж��Ƿ��ѳ�ʼ�����ּ�¼
        if (this.nameDic == null || this.map == null)
            return;
        this.nameDic.print(out);
    }

    /**
     * @return ���� firstNameDic��
     */
    public ChineseFirstNameDictionary getFirstNameDic()
    {
        return firstNameDic;
    }

    /**
     * ��ȡ����ch������ͳ����Ϣ
     * 
     * @param ch
     *            ����
     * @return ���ֵ�����ͳ����Ϣ
     */
    public ChineseNameCharInfo getChineseNameCharInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return ���� firstNameCount��
     */
    public int getFirstNameCount()
    {
        return firstNameCount;
    }

    /**
     * @return ���� givenNameCount��
     */
    public int getGivenNameCount()
    {
        return givenNameCount;
    }
}
