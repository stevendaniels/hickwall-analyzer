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
package com.novse.segmentation.core.io;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Mac Kwan ͳһ��Դ·���µ���Դ
 */
public class UrlResource implements Resource
{
    /**
     * ͳһ��Դ·��
     */
    private URL url = null;

    /**
     * ���ַ�����ʽ��ͳһ��Դ·��Ϊ�����Ĺ��캯��
     * 
     * @param path
     *            �ַ�����ʽ��ͳһ��Դ·��
     */
    public UrlResource(String path)
    {
        try
        {
            this.url = new URL(path);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ��ͳһ��Դ·��ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param url
     *            ͳһ��Դ·��ʵ��
     */
    public UrlResource(URL url)
    {
        this.url = url;
    }

    /**
     * ����ָ��ͳһ��Դ·������Դ��������
     */
    public InputStream getInputStream() throws Exception
    {
        return this.url.openStream();
    }

}
