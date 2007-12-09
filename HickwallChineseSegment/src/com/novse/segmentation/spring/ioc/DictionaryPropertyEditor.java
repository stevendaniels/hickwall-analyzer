/*
 * @����:Hades , ��������:2006-12-25
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring.ioc;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;
import com.novse.segmentation.util.DictionaryUtil;

/**
 * @author Mac Kwan ���ַ�������ת��Ϊ��Ӧ�ֵ�����
 */
public class DictionaryPropertyEditor<T extends Loadable> extends
        PropertyEditorSupport
{
    @SuppressWarnings("unused")
    private Class<T> classType = null;

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param classType
     *            ָ����ʹ�÷��͵�Class����
     */
    public DictionaryPropertyEditor(Class<T> classType)
    {
        this.classType = classType;
    }

    /*
     * ���� Javadoc��
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        // ȥ����β�հ��ַ�
        text = StringUtils.trimToNull(text);
        // ��ʼ���ֵ乤��
        DictionaryUtil<T> tool = new DictionaryUtil<T>();
        // ����ָ�����ļ�·����ʼ���ֵ�ʵ��
        T dic = tool.readDictionary(text);
        // ���÷���ֵ
        this.setValue(dic);
    }

}
