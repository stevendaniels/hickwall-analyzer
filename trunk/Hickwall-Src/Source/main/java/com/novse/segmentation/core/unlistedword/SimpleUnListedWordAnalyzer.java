/*
 * @����:Hades , ��������:2007-4-10
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary.CharFrequenceInfo;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan �򵥵�δ��¼��ʶ����ʵ����
 */
public class SimpleUnListedWordAnalyzer extends AbstractUnListedWordAnalyzer
{
    /**
     * ��Ƶͳ���ֵ�
     */
    private CharFrequenceDictionary dic = null;

    /**
     * ���ֳ�Ϊ�ʻ����ֵĸ��ʷ�ֵ�����ָ��ʴ��ڵ��ֳɴʸ���������ָ��ʵĺͣ�
     */
    private final double FIRSTCH_SETPOINT = 0.5;

    /**
     * ���ֳɴʵĸ��ʷ�ֵ
     */
    private final double SINGLE_SETPOINT = 5e-5;

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param dic
     *            ��Ƶͳ���ֵ�ʵ��
     */
    public SimpleUnListedWordAnalyzer(CharFrequenceDictionary dic)
    {
        this.dic = dic;
        this.initSeperator();
    }

    /**
     * �Զ���ָ����Ĺ��캯��
     * 
     * @param dic
     *            ��Ƶͳ���ֵ�ʵ��
     * 
     * @param seperator
     *            �Զ���ָ���
     */
    public SimpleUnListedWordAnalyzer(CharFrequenceDictionary dic,
            String seperator)
    {
        this.dic = dic;
        this.seperator = seperator;
    }

    /**
     * ���㺺��ch��Ϊ�ʻ����ֵĸ���
     * 
     * @param info
     *            ����ch����Ƶͳ����Ϣ
     * @return ����ch��Ϊ�ʻ����ֵĸ���
     */
    private double getFirstCharValue(CharFrequenceInfo info)
    {
        double value = (double) info.firstCharFrequence / info.charFrequence;
        // System.out.println("First Char Value:" + value);
        return value;
    }

    /**
     * ���㵥��ch�ɴʸ���
     * 
     * @param info
     *            ����ch����Ƶͳ����Ϣ
     * @return ���ֳɴʸ���
     */
    private double getSingleValue(CharFrequenceInfo info)
    {
        double value = ((double) info.singleCharFrequnece / info.charFrequence)
                * ((double) info.singleCharFrequnece / this.dic.getWordCount());
        // System.out.println("Single Value" + value);
        return value;
    }

    /**
     * �������ԭ�ִʽ��srcList����δ��¼��ʶ��
     * 
     * @param srcList
     *            ԭ�ִʽ��
     * @return δ��¼��ʶ�����ķִʽ��
     */
    public List<String> identify(List<String> srcList)
    {
        // ���ÿ�ʼ�������α�
        int startPos = 0, endPos = 0;
        // ����Ѱ�ҷִʽ���еĵ�����Ƭ
        while (startPos < srcList.size())
        {
            // Ѱ���ֳ�Ϊ1��Ϊ�Ƿָ��ַ��ĵ���
            while (startPos < srcList.size()
                    && (srcList.get(startPos).length() > 1 || this.seperator
                            .indexOf(srcList.get(startPos)) >= 0))
                startPos++;
            endPos = startPos;
            // Ѱ���ֳ�Ϊ1��Ϊ�Ƿָ��ַ��ĵ���
            while (endPos < srcList.size()
                    && (srcList.get(endPos).length() == 1 && this.seperator
                            .indexOf(srcList.get(endPos)) < 0))
                endPos++;

            // hades print
            String str = "";
            for (int i = startPos; i < endPos; i++)
                str += srcList.get(i);

            // ����������Ƭ����
            while (startPos < endPos)
            {
                // ��ȡ������Ƭch
                String ch = srcList.get(startPos);
                // ��ȡ����ch��Ƶ��ͳ����Ϣ
                CharFrequenceInfo info = this.dic.getInfo(ch);

                // ���޵���ch��Ƶ��ͳ����Ϣ����֤������ch�Ǻ����֣��ж���Ϊ���ִ�
                if (info == null)
                    startPos++;
                // ����
                else
                {
                    // �жϵ��ֳɴʸ��ʡ��ʻ����ָ�����������
                    if (this.getSingleValue(info) > SINGLE_SETPOINT
                            || this.getFirstCharValue(info) < FIRSTCH_SETPOINT)
                        startPos++;
                    else
                    {
                        // ��ʼ���ַ�����
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(ch);

                        // ��ȡ��һ�������Լ�����Ӧ��Ϣ
                        int nextPos = startPos + 1;
                        ch = srcList.get(nextPos);
                        info = this.dic.getInfo(ch);

                        // �ϲ����ܳ�Ϊ���ִʵĵ���
                        while (nextPos <= endPos
                                && StopWordMaker
                                        .retreiveStringWithNumberAndChar()
                                        .indexOf(ch) < 0
                                && info != null
                                && this.getSingleValue(info) <= SINGLE_SETPOINT
                                && this.getFirstCharValue(info) < FIRSTCH_SETPOINT)
                        {
                            buffer.append(ch);
                            // ��ȡ��һ�������Լ�����Ӧ��Ϣ
                            nextPos++;
                            ch = srcList.get(nextPos);
                            info = this.dic.getInfo(ch);
                        }
                        // end of while

                        // �ϲ����ַ����ǵ��ִ�ʱ
                        if (buffer.length() > 1)
                        {
                            // hades unlisted word print
                            System.out.println("��Ƭ��" + str);
                            System.out.println("�����" + buffer);
                            System.out.println();

                            // �滻
                            srcList.set(startPos, buffer.toString());
                            // ɾ��
                            for (int i = 0; i < buffer.length() - 1; i++, endPos--)
                                srcList.remove(startPos + 1);
                        }
                        // ���迪ʼ�α�
                        startPos += 1;
                        // end of if(buffer.length()>1)
                    }
                    // end of if (singleValue > SINGLE_SETPOINT || firstChValue
                    // < FIRSTCH_SETPOINT)
                }
                // end of if(info==null)
            }
        }
        return srcList;
    }
}
