/*
 * @����:Hades , ��������:2007-4-6
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

/**
 * @author Mac Kwan ���ڷִ���Ƭ��δ��¼��ʶ��ӿ�
 */
public interface UnListedWordAnalyzer
{
    /**
     * �������ԭ�ִʽ��srcList����δ��¼��ʶ��
     * 
     * @param srcList
     *            ԭ�ִʽ��
     * @return δ��¼��ʶ�����ķִʽ��
     */
    public List<String> identify(List<String> srcList);
}
