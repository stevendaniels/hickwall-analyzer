/*
 * @����:Hades , ��������:2007-4-2
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan ˫��ֵ��ǰ׺��׺������
 */
public class DoubleSetpointPostProcessor extends BasicStatisticPostProcessor
{
    /**
     * @author Mac Kwan �ڲ��࣬��Ƶ�ִ��Ƚ������Ը�Ƶ�ִ����ֵ�Ƶ�ν��бȽϣ�
     */
    static protected class WordInfoFrequenceComparator implements
            Comparator<WordInfo>
    {
        public int compare(WordInfo o1, WordInfo o2)
        {
            return o2.frequence - o1.frequence;
        }
    }

    /**
     * @author Mac Kwan �ڲ��࣬��Ƶ�ִ��Ƚ������Ը�Ƶ�ִ����бȽϣ�
     */
    static protected class WordInfoStringComparator implements
            Comparator<WordInfo>
    {
        public int compare(WordInfo o1, WordInfo o2)
        {
            return o1.word.compareTo(o2.word);
        }
    }

    /**
     * ���Ŷ�����
     */
    private double maxConfidence = 0.9;

    /**
     * ���Ŷ�����
     */
    private double minConfidence = 0.1;

    /**
     * ǰ׺��׺����
     * 
     * @param wordList
     *            ������ĸ�Ƶ�ִ�����
     * @return �����ĸ�Ƶ�ִ�����
     * @throws Exception
     */
    @Override
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("�������Ƶ�ִ�����wordListΪnull");

        // �ȶԴ�����ĸ�Ƶ�ִ���������
        Collections.sort(wordList, new WordInfoStringComparator());

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

                // wordList[i]��wordList[j]���ִ�ʱ
                if (firstIndex >= 0 || lastIndex >= 0)
                {
                    double confidence = (double) (wordList.get(i).frequence - wordList
                            .get(j).frequence)
                            / (double) wordList.get(j).frequence;
                    // ���Ŷȹ�С
                    if (confidence <= this.minConfidence)
                        remove.add(wordList.get(i));
                    // ���Ŷȹ���
                    if (confidence >= this.maxConfidence)
                        remove.add(wordList.get(j));
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
     * @param maxConfidence
     *            Ҫ���õ� maxConfidence��
     */
    public void setMaxConfidence(double maxConfidence)
    {
        this.maxConfidence = maxConfidence;
    }

    /**
     * @param minConfidence
     *            Ҫ���õ� minConfidence��
     */
    public void setMinConfidence(double minConfidence)
    {
        this.minConfidence = minConfidence;
    }
}
