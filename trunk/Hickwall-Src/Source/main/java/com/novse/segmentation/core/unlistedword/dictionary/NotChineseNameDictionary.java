/*
 * @����:Hades , ��������:2007-5-21
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

import com.novse.segmentation.core.matching.dictionary.AbstractDictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;

/**
 * @author Mac Kwan ���ڼ�¼����������Ϊ�׵��������������Ĵʵ������
 */
public class NotChineseNameDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * 
     * @author Mac Kwan �������Ͽ�ͷ���������������ĺ���ͳ����Ϣ
     */
    public static class NotChineseNameCharInfo implements Serializable
    {

        /**
         * <code>serialVersionUID</code> ��ע��
         */
        private static final long serialVersionUID = 354751051371918550L;

        /**
         * �ܹ����ֵ�Ƶ��
         */
        public int charFrequence = 0;

        /**
         * ��Ϊ����������λ�ó��ֵ�Ƶ��
         */
        public int otherFrequence = 0;

        /**
         * ��Ϊ�������֣�α���ϣ����ֵ�Ƶ��
         */
        public int firstFrequence = 0;

        /**
         * �жϸú��ֵ�Ƶ����Ϣ�Ƿ��Ϊ������
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
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 6644742138143868633L;

    /**
     * ����α���ϳ��ֵĴ���
     */
    private int firstCount = 0;

    /**
     * �����������ֳ��ֵĴ���
     */
    private int otherCount = 0;

    /**
     * �������ϴʵ�
     */
    private ChineseFirstNameDictionary firstNameDic = null;

    /**
     * ������������ͳ����Ϣ����
     */
    private HashMap<String, NotChineseNameCharInfo> map = null;

    /**
     * ��¼������ķ����ֿ�
     */
    private HashDictionary notNameDic = null;

    /**
     * @param firstNameDic
     *            �������ϴʵ�
     */
    public NotChineseNameDictionary(ChineseFirstNameDictionary firstNameDic)
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
        if (isEmpty())
            return;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return;

        // �ж��Ƿ��д�����
        if (!this.notNameDic.match(word))
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
            NotChineseNameCharInfo firstNameInfo = this.map.get(firstName);
            if (firstNameInfo != null)
            {
                // ������Ƶ���Լ�
                if (firstNameInfo.charFrequence > 0)
                    firstNameInfo.charFrequence--;
                // ���ϳ���Ƶ���Լ�
                if (firstNameInfo.firstFrequence > 0)
                    firstNameInfo.firstFrequence--;
                // ���·���������������ͳ����Ϣ����
                if (!firstNameInfo.isBlank())
                    this.map.put(firstName, firstNameInfo);
                else
                    this.map.remove(firstName);
            }
            else
                agree = false;
            // �������ϳ���Ƶ���Լ�
            this.firstCount--;

            if (agree)
            {
                // ���ִ���
                for (int i = index; i < word.length(); i++)
                {
                    // ��ȡ����
                    String givenName = word.substring(i, i + 1);
                    NotChineseNameCharInfo givenNameInfo = this.map
                            .get(givenName);
                    if (givenNameInfo != null)
                    {
                        // �ܳ���Ƶ������
                        if (givenNameInfo.charFrequence > 0)
                            givenNameInfo.charFrequence--;
                        // �������ֻ�ĩ��Ƶ���Լ�
                        givenNameInfo.otherFrequence--;
                        // ���·���������������ͳ����Ϣ����
                        if (!givenNameInfo.isBlank())
                            this.map.put(givenName, givenNameInfo);
                        else
                            this.map.remove(givenName);
                    }
                    else
                        agree = false;
                    // �����������ֳ��ִ����Լ�
                    this.otherCount--;
                }
            }
            if (agree)
            {
                // ���뵽���ֿ�
                this.notNameDic.deleteWord(word);
            }
        }
    }

    /**
     * @return ���� firstNameCount��
     */
    public int getFirstCount()
    {
        return firstCount;
    }

    /**
     * @return ���� firstNameDic��
     */
    public ChineseFirstNameDictionary getFirstNameDic()
    {
        return firstNameDic;
    }

    /**
     * ��ȡ����ch��α����ͳ����Ϣ
     * 
     * @param ch
     *            ����
     * @return ���ֵ�����ͳ����Ϣ
     */
    public NotChineseNameCharInfo getNotChineseNameCharInfo(String ch)
    {
        return this.map.get(ch);
    }

    /**
     * @return ���� givenNameCount��
     */
    public int getOtherCount()
    {
        return otherCount;
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
            this.map = new HashMap<String, NotChineseNameCharInfo>();
        if (this.notNameDic == null)
            this.notNameDic = new HashDictionary();

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return;

        // �ж��Ƿ����д���������
        if (this.notNameDic.match(word))
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
            NotChineseNameCharInfo firstNameInfo;
            if (this.map.containsKey(firstName))
                firstNameInfo = this.map.get(firstName);
            else
                firstNameInfo = new NotChineseNameCharInfo();
            // �ܳ���Ƶ������
            firstNameInfo.charFrequence++;
            // ���ϳ���Ƶ������
            firstNameInfo.firstFrequence++;
            this.map.put(firstName, firstNameInfo);
            // �������ϳ��ִ�������
            this.firstCount++;
            // ���ִ���
            for (int i = index; i < word.length(); i++)
            {
                // ��ȡ����
                String givenName = word.substring(i, i + 1);
                NotChineseNameCharInfo givenNameInfo;
                if (this.map.containsKey(givenName))
                    givenNameInfo = this.map.get(givenName);
                else
                    givenNameInfo = new NotChineseNameCharInfo();
                // �ܳ���Ƶ������
                givenNameInfo.charFrequence++;
                // �������ֻ�ĩ��Ƶ������
                givenNameInfo.otherFrequence++;
                this.map.put(givenName, givenNameInfo);
                // �����������ֳ��ִ�������
                this.otherCount++;
            }
            // ���뵽���ֿ�
            this.notNameDic.insertWord(word);
        }
    }

    /**
     * �ʵ��Ƿ�Ϊ��
     * 
     * @return �ʵ��Ƿ�Ϊ��
     */
    @Override
    public boolean isEmpty()
    {
        return firstNameDic == null || firstNameDic.isEmpty() || map == null
                || map.isEmpty() || notNameDic == null || notNameDic.isEmpty();
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
            this.map = new HashMap<String, NotChineseNameCharInfo>();
        if (this.notNameDic == null)
            this.notNameDic = new HashDictionary();
        this.firstCount = 0;
        this.otherCount = 0;

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
        if (isEmpty())
            return false;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return false;
        // ȥ������ո�
        word = word.trim();
        if (word.length() > 4 || word.length() < 2)
            return false;

        return this.notNameDic.match(word);
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
        if (this.notNameDic == null || this.map == null)
            return;
        this.notNameDic.print(out);
    }
}
