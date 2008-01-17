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
package com.novse.segmentation.core.statistic.fetcher;

import java.util.List;

/**
 * @author Mac Kwan �ʻ��ȡ���ĳ���ʵ����
 */
public abstract class AbstractStringFetcher implements StringFetcher
{
    /**
     * Ӣ�������ַ���
     */
    protected final String CHAR_AND_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * �ָ����ַ���
     */
    protected String seperator = null;

    /**
     * �Ӵ������ļ�srcFile�г�ȡ�ʻ�
     * 
     * @param srcFile
     *            �������ļ�
     * @return ��ȡ���ôʻ�
     */
    abstract public List<String> fileFetch(String srcFile);

    /**
     * ��ʼ���ָ����ķ���
     */
    protected void initSeperator()
    {
        // ��ʼ���ָ���
        StringBuffer buffer = new StringBuffer();
        for (char c = '\u0000'; c <= '\u007F'; c++)
        {
            // ������Ӣ�ġ������ַ�
            if (this.CHAR_AND_NUM.indexOf(c) < 0)
                buffer.append(c);
        }
        for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            buffer.append(c);
        buffer.append(" \r\n�����������������������������������D�������£��졤�����򣣣���������������");
        this.seperator = buffer.toString();
    }

    /**
     * �Ӵ������ַ���doc�г�ȡ�ʻ�
     * 
     * @param doc
     *            �������ַ���
     * @return ��ȡ���ôʻ�
     */
    abstract public List<String> textFetch(String doc);

}
