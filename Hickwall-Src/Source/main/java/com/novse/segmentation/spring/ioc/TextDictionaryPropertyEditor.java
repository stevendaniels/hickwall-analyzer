/*
 * @����:Hades , ��������:May 19, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring.ioc;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;

/**
 * @author Mac Kwan ����txt�ı�·���ַ�������ת��Ϊ��Ӧ�ֵ�����
 * 
 */
public class TextDictionaryPropertyEditor<T extends Loadable> extends
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
    public TextDictionaryPropertyEditor(Class<T> classType)
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
        // ��ʼ���ʵ�ʵ��
        T dic = null;
        try
        {
            dic = classType.newInstance();
            dic.loadDictionary(text);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // ���÷���ֵ
            this.setValue(dic);
        }
    }

}
