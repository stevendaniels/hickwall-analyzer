/*
 * @����:Hades , ��������:2007-4-2
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.basic;

import java.util.ArrayList;
import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan ����ֵ��ǰ׺��׺������
 */
public class SetpointPostProcessor extends BasicStatisticPostProcessor
{
    /**
     * �û�ָ���ķ�ֵ
     */
    private double setpoint = 0.55;

    /**
     * ǰ׺��׺����
     * 
     * @param wordList
     *            �������ĸ�Ƶ�ִ�����
     * @return ������ĸ�Ƶ�ִ�����
     * @throws Exception
     */
    @Override
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("��������Ƶ�ִ�����wordListΪnull");
        // ������������
        ArrayList<WordInfo> remove = new ArrayList<WordInfo>();

        // ѭ��������Ƶ�ִ�����
        for (int i = 0; i < wordList.size(); i++)
        {
            // ȥ�����ġ����ˡ����ء����š���ͷ�Ĵʻ�
            if (wordList.get(i).word.indexOf("��") == 0
                    || wordList.get(i).word.indexOf("��") == 0
                    || wordList.get(i).word.indexOf("��") == 0
                    || wordList.get(i).word.indexOf("��") == 0)
            {
                remove.add(wordList.get(i));
                continue;
            }
            for (int j = i + 1; j < wordList.size(); j++)
            {
                // �ж�wordList[i]�Ƿ�ΪwordList[j]��ǰ׺
                int firstIndex = wordList.get(j).word
                        .indexOf(wordList.get(i).word);
                // �ж�wordList[i]�Ƿ�ΪwordList[j]�ĺ�׺
                int lastIndex = wordList.get(j).word.lastIndexOf(wordList
                        .get(i).word);
                if ((firstIndex >= 0 || lastIndex >= 0)
                        && wordList.get(i).frequence == wordList.get(j).frequence)
                {
                    remove.add(wordList.get(i));
                }
                else if (firstIndex >= 0 || lastIndex >= 0)
                {
                    double t = (double) wordList.get(i).frequence
                            / (double) wordList.get(j).frequence;
                    // wordList[i]��wordList[j]��αǰ׺����α��׺
                    if (t >= setpoint)
                    {
                        wordList.get(j).value = wordList.get(j).value + 1;
                        wordList.get(i).value = wordList.get(i).value - 1;
                    }
                }
            }
        }

        // �Ƴ������������ĸ�Ƶ�ִ�
        wordList.removeAll(remove);
        // ��շ�������
        remove = null;

        // ���ؽ��
        return wordList;
    }

    /**
     * @param setpoint
     *            Ҫ���õ� setpoint��
     */
    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
    }

}