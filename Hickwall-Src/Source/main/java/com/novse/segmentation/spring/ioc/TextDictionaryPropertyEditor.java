/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
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
