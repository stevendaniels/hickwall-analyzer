/*
 * @����:Hades , ��������:2007-4-2
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.statistic.basic;

import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan ����ͳ�Ƶĸ�Ƶ�ʻ��ȡ���ĺ��ڴ�����
 */
public class BasicStatisticPostProcessor
{
    /**
     * ǰ׺��׺����
     * 
     * @param wordList
     *            ������ĸ�Ƶ�ִ�����
     * @return �����ĸ�Ƶ�ִ�����
     * @throws Exception
     */
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("�������Ƶ�ִ�����wordListΪnull");
        return wordList;
    }
}
