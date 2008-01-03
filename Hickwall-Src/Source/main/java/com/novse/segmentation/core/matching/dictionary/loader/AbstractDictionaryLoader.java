/*
 * @����:Mac Kwan , ��������:2007-12-28
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary.loader;

import java.util.List;

import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan �ʵ����������ʵ��
 */
public abstract class AbstractDictionaryLoader implements DictionaryLoader
{
    /**
     * �Ӵʵ���Դ�г�ȡ��ôʻ��б�
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return �ʻ��б�
     */
    abstract protected List<String> extract(Resource dicResource);

    /**
     * Ϊָ���Ĵʵ�ʵ�������ⲿ�ʵ���Դ
     * 
     * @param dic
     *            �ʵ�ʵ��
     * @param dicResource
     *            �ʵ���Դ
     */
    public void load(Dictionary dic, Resource dicResource)
    {
        // ����Դ�г�ȡ�ʻ�
        List<String> extractList = this.extract(dicResource);
        // �������뵽�ʵ���
        dic.insertWord(extractList);
    }

}
