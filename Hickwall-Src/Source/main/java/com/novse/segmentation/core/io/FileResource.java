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
 * @author Mac Kwan 文件资源
 */
public class FileResource implements Resource
{
    /**
     * 文件类
     */
    private File file = null;

    /**
     * 以文件路径为参数的构造函数
     * 
     * @param path
     *            文件路径
     */
    public FileResource(String path)
    {
        this.file = new File(path);
    }

    /**
     * 以文件类实例为参数的构造函数
     * 
     * @param file
     *            文件类实例
     */
    public FileResource(File file)
    {
        this.file = file;
    }

    /**
     * 返回指定文件资源的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return new FileInputStream(this.file);
    }

}
