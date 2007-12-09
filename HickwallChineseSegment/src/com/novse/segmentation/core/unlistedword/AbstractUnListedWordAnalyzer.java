/*
 * @����:Hades , ��������:2007-4-10
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan δ��¼��ʶ�����ĳ���ʵ����
 */
public abstract class AbstractUnListedWordAnalyzer implements
        UnListedWordAnalyzer
{
    /**
     * �ָ����ַ���
     */
    protected String seperator = null;

    /**
     * �������ԭ�ִʽ��srcList����δ��¼��ʶ��
     * 
     * @param srcList
     *            ԭ�ִʽ��
     * @return δ��¼��ʶ�����ķִʽ��
     */
    abstract public List<String> identify(List<String> srcList);

    /**
     * ��ʼ���ָ����ķ���
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveStringWithNumberAndChar();
    }

}
