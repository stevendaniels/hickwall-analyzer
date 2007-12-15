/*
 * @作者:Hades , 创建日期:2006-12-25
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring.ioc;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.Loadable;
import com.novse.segmentation.util.DictionaryUtil;

/**
 * @author Mac Kwan 将字符串描述转换为相应字典类型
 */
public class DictionaryPropertyEditor<T extends Loadable> extends
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
    public DictionaryPropertyEditor(Class<T> classType)
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
        // 初始化字典工具
        DictionaryUtil<T> tool = new DictionaryUtil<T>();
        // 根据指定的文件路径初始化字典实例
        T dic = tool.readDictionary(text);
        // 设置访问值
        this.setValue(dic);
    }

}
