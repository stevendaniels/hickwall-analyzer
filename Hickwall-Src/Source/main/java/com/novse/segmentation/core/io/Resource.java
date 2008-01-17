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

/**
 * @author Mac Kwan 资源接口
 */
public interface Resource
{
    /**
     * 获取指定资源的输入流
     * 
     * @return 指定资源的输入流
     * @throws Exception
     */
    public InputStream getInputStream() throws Exception;
}
