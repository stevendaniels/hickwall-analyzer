/*
 * @作者:Hades , 创建日期:May 19, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring.ioc;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;

/**
 * @author Mac Kwan 将已txt文本路径字符串描述转换为相应字典类型
 * 
 */
public class TextDictionaryPropertyEditor<T extends Loadable> extends
        PropertyEditorSupport
{
    @SuppressWarnings("unused")
    private Class<T> classType = null;

    /**
     * 默认构造函数
     * 
     * @param classType
     *            指定所使用泛型的Class类型
     */
    public TextDictionaryPropertyEditor(Class<T> classType)
    {
        this.classType = classType;
    }

    /*
     * （非 Javadoc）
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        // 去除首尾空白字符
        text = StringUtils.trimToNull(text);
        // 初始化词典实例
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
            // 设置访问值
            this.setValue(dic);
        }
    }

}
