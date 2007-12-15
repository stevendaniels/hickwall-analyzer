/*
 * @����:Hades , ��������:2006-11-17
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core;

import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;

/**
 * 
 * @author Mac Kwan ���ڴʵ�ƥ������ķִʽӿ�
 */
public interface SegmentProcessor
{

    /**
     * ��srcFile�ļ����зִʣ��ѽ������Ϊ��tagFile�ļ���
     * 
     * @param srcFile
     *            ���ִʵ��ı��ļ�
     * @param tagFile
     *            �ִʽ������Ŀ���ļ�
     */
    public void fileProcessor(String srcFile, String tagFile);

    /**
     * @return ���� dic��
     */
    public Dictionary getDic();

    /**
     * @return ���� fetcher��
     */
    public StringFetcher getFetcher();

    /**
     * @param dic
     *            Ҫ���õ� dic��
     */
    public void setDic(Dictionary dic);

    /**
     * @param fetcher
     *            Ҫ���õ� fetcher��
     */
    public void setFetcher(StringFetcher fetcher);

    /**
     * ��text�ı����зִʣ��ѽ������Ϊ�ַ�������
     * 
     * @param text
     *            ���ִʵ��ı�
     * @return �ִʽ��
     */
    public List<String> textProcess(String text);

}
