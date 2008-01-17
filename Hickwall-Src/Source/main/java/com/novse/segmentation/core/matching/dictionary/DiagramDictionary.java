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
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mac Kwan ���ڶ�άͼ��Ĵʵ������
 */
public class DiagramDictionary extends AbstractDictionary implements
        Serializable
{
    /**
     * ��άͼ��ʵ�Ĵ�С
     */
    private static final int ARRAY_SIZE = 1692;

    /**
     * GB2312�����к��ֵĸ���
     */
    private static final int CHAR_SIZE = 6768;

    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 8916348479425642741L;

    /**
     * �ʳ�Ϊ2�Ĵʵ�ʵ��
     */
    private short[][] diagramDic = null;

    /**
     * �ʳ�����2�Ĵʵ�ʵ��
     */
    private HashDictionary otherDic = null;

    /**
     * ɾ���ʵ��еĴ�word
     * 
     * @param word
     *            ��ɾ���Ĵʻ�
     */
    public void deleteWord(String word)
    {
        if (isEmpty())
            return;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ������ո�
        word = word.trim();
        // ���˵��ִʲ���
        if (word.length() < 2)
            return;

        if (word.length() == 2)
        {
            // ����λ��
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // �ж��ַ�1��2�Ƿ�ΪGB2312�����еĺ���
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return;
            this.setDiagram(firstIndex, secondIndex, false);
        }
        else
            otherDic.deleteWord(word);
    }

    /**
     * ����������Ķ�Ӧλ�Ʒ���gb2312����ĺ���
     * 
     * @param index
     *            ��Ӧλ��
     * @return gb2312����ĺ���
     */
    protected String getChar(int index)
    {
        // �ж�λ���Ƿ�С��0
        if (index < 0)
            return null;

        int div = index / 94;
        int mod = index % 94;
        byte[] strBytes =
        { (byte) (div - 80), (byte) (mod - 95) };
        try
        {
            return new String(strBytes, "gb2312");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * �ж�λ��ΪfirstIndex��secondIndex�Ĵ����Ƿ����
     * 
     * @param firstIndex
     *            ���ִ����ֵ�λ��
     * @param secondIndex
     *            ���ִ�ĩ�ֵ�λ��
     * @return true���ڣ�false������
     */
    protected boolean getDiagram(int firstIndex, int secondIndex)
    {
        // ����
        short base = 2;

        // ���������ڶ�ά�����е�λ��
        int firstArray = firstIndex / 4;
        int firstOffset = firstIndex % 4;
        // ����ĩ���ڶ�ά�����е�λ��
        int secondArray = secondIndex / 4;
        int secondOffset = secondIndex % 4;

        // ��ȡ��ά�����е���ֵ
        short num = this.diagramDic[firstArray][secondArray];
        // ����offsetλ��short������ֵ�ڼ�λ��bit��
        int pos = firstOffset + 4 * secondOffset;
        short compare = (short) Math.pow(base, pos);
        // �����ֵ��2^pos���������Ȼ����2^pos��posλΪ1
        if ((num & compare) == compare)
            return true;
        else
            return false;
    }

    /**
     * �����������gb2312����ĺ��ַ��ض�Ӧ��λ��
     * 
     * @param ch
     *            ����
     * @return ���ֶ�Ӧ�ı��룬���쳣�򷵻�-1
     */
    protected int getIndex(String ch)
    {
        // ��������ַ������ȴ���1
        if (ch.length() > 1)
            return -1;
        // ������'\0'�򷵻�λ��0
        if (ch.equals("\0"))
            return -1;
        try
        {
            // ���غ���ch��Ӧ��byte����
            byte[] bArray = ch.getBytes("gb2312");
            // byte�����С����2ʱ
            if (bArray.length > 2)
                return -1;
            // ȷ�� -80<=bArray[0]<=-9 -95<=bArray[1]<=-2
            if (bArray[0] > -9 || bArray[0] < -80 || bArray[1] > -2
                    || bArray[1] < -95)
                return -1;
            // ���ض�Ӧ���ֵ�λ��
            return 94 * (bArray[0] + 80) + (bArray[1] + 95);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * ���ʻ�word���뵽�ʵ��ļ���
     * 
     * @param word
     *            ������Ĵʻ�
     */
    @SuppressWarnings("unchecked")
    public void insertWord(String word)
    {
        // ��ʼ���ʵ�
        if (diagramDic == null)
            diagramDic = new short[ARRAY_SIZE][ARRAY_SIZE];
        if (otherDic == null)
            otherDic = new HashDictionary();

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return;
        // ȥ�����ڿո�
        word = word.trim();
        // �ж��Ƿ��ִ�
        if (word.length() < 2)
            return;

        // �ʳ�Ϊ2
        if (word.length() == 2)
        {
            // ����λ��
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // �ж��ַ�1��2�Ƿ�ΪGB2312�����еĺ���
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return;
            this.setDiagram(firstIndex, secondIndex, true);
        }
        else
            otherDic.insertWord(word);
    }

    /**
     * �ʵ��Ƿ�Ϊ��
     * 
     * @return �ʵ��Ƿ�Ϊ��
     */
    @Override
    public boolean isEmpty()
    {
        return diagramDic == null && (otherDic == null || otherDic.isEmpty());
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
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (isEmpty())
            return false;

        // �жϴʻ��Ƿ�Ϊ���ַ���
        if (StringUtils.isBlank(word))
            return false;
        // ȥ������ո�
        word = word.trim();
        // ���˵��ִʲ���
        if (word.length() < 2)
            return false;

        if (word.length() == 2)
        {
            // ����λ��
            int firstIndex = this.getIndex(word.substring(0, 1));
            int secondIndex = this.getIndex(word.substring(1, 2));
            // �ж��ַ�1��2�Ƿ�ΪGB2312�����еĺ���
            if (firstIndex < 0 || secondIndex < 0 || firstIndex >= CHAR_SIZE
                    || secondIndex >= CHAR_SIZE)
                return false;
            return this.getDiagram(firstIndex, secondIndex);
        }
        else
            return otherDic.match(word);
    }

    /**
     * ����������ڴ������дʻ�
     * 
     * @param out
     *            �����
     */
    public void print(PrintStream out)
    {
        // �жϴʵ��Ƿ��ѳ�ʼ��
        if (diagramDic == null)
            return;
        for (int i = 0; i < CHAR_SIZE; i++)
            for (int j = 0; j < CHAR_SIZE; j++)
            {
                if (this.getDiagram(i, j))
                {
                    out.print(this.getChar(i));
                    out.println(this.getChar(j));
                }
            }
        otherDic.print(out);
        out.flush();
    }

    /**
     * ��ӻ�ɾ��λ��ΪfirstIndex��secondIndex�Ĵ���
     * 
     * @param firstIndex
     *            ���ִ����ֵ�λ��
     * @param secondIndex
     *            ���ִ�ĩ�ֵ�λ��
     * @param value
     *            true��Ӵ��飬falseɾ������
     */
    protected void setDiagram(int firstIndex, int secondIndex, boolean value)
    {
        // ����
        short base = 2;

        // ���������ڶ�ά�����е�λ��
        int firstArray = firstIndex / 4;
        int firstOffset = firstIndex % 4;
        // ����ĩ���ڶ�ά�����е�λ��
        int secondArray = secondIndex / 4;
        int secondOffset = secondIndex % 4;

        // ��ȡ��ά�����е���ֵ
        short num = this.diagramDic[firstArray][secondArray];
        // ����offsetλ��short������ֵ�ڼ�λ��bit��
        int pos = firstOffset + 4 * secondOffset;
        // ����valueֵ����num��ֵ
        if (value)
            num |= (short) Math.pow(base, pos);
        else
            num &= (~(short) Math.pow(base, pos));
        // ����������ֵ
        this.diagramDic[firstArray][secondArray] = num;
    }

}
