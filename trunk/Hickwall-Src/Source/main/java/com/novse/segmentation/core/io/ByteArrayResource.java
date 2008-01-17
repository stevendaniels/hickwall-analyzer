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
 * @author Mac Kwan 字节数组资源
 */
public class ByteArrayResource implements Resource
{
    /**
     * 字节数组
     */
    private byte[] byteArray = null;

    /**
     * 以字节数组为参数的构造函数
     * 
     * @param byteArray
     *            字节数组
     */
    public ByteArrayResource(byte[] byteArray)
    {
        this.byteArray = byteArray;
    }

    /**
     * 返回指定字节数组的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return new ByteArrayInputStream(this.byteArray);
    }

}
