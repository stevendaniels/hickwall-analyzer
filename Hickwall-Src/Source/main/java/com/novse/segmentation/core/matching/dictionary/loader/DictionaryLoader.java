/*
 * @����:Mac Kwan , ��������:2007-12-25
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary.loader;

import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan �ʵ�������ӿ�
 */
public interface DictionaryLoader
{
    /**
     * Ϊָ���Ĵʵ�ʵ�������ⲿ�ʵ���Դ
     * 
     * @param dic
     *            �ʵ�ʵ��
     * @param dicResource
     *            �ʵ���Դ
     */
    public void load(Dictionary dic, Resource dicResource);
}
