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
