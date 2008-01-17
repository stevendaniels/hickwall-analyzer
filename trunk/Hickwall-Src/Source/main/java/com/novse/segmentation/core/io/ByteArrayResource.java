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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Mac Kwan �ֽ�������Դ
 */
public class ByteArrayResource implements Resource
{
    /**
     * �ֽ�����
     */
    private byte[] byteArray = null;

    /**
     * ���ֽ�����Ϊ�����Ĺ��캯��
     * 
     * @param byteArray
     *            �ֽ�����
     */
    public ByteArrayResource(byte[] byteArray)
    {
        this.byteArray = byteArray;
    }

    /**
     * ����ָ���ֽ������������
     */
    public InputStream getInputStream() throws Exception
    {
        return new ByteArrayInputStream(this.byteArray);
    }

}
