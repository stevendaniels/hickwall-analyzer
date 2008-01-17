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
 * @author Mac Kwan 统一资源路径下的资源
 */
public class UrlResource implements Resource
{
    /**
     * 统一资源路径
     */
    private URL url = null;

    /**
     * 以字符串形式的统一资源路径为参数的构造函数
     * 
     * @param path
     *            字符串形式的统一资源路径
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
     * 以统一资源路径实例为参数的构造函数
     * 
     * @param url
     *            统一资源路径实例
     */
    public UrlResource(URL url)
    {
        this.url = url;
    }

    /**
     * 返回指定统一资源路径下资源的输入流
     */
    public InputStream getInputStream() throws Exception
    {
        return this.url.openStream();
    }

}
