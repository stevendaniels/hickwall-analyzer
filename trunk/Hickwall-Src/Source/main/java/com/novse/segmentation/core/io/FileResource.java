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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Mac Kwan �ļ���Դ
 */
public class FileResource implements Resource
{
    /**
     * �ļ���
     */
    private File file = null;

    /**
     * ���ļ�·��Ϊ�����Ĺ��캯��
     * 
     * @param path
     *            �ļ�·��
     */
    public FileResource(String path)
    {
        this.file = new File(path);
    }

    /**
     * ���ļ���ʵ��Ϊ�����Ĺ��캯��
     * 
     * @param file
     *            �ļ���ʵ��
     */
    public FileResource(File file)
    {
        this.file = file;
    }

    /**
     * ����ָ���ļ���Դ��������
     */
    public InputStream getInputStream() throws Exception
    {
        return new FileInputStream(this.file);
    }

}
