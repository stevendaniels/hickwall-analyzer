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
package com.novse.segmentation.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Mac Kwan
 * 
 */
public class SpecailFileFilter extends FileFilter
{
    private String fileType = null;

    /**
     * 默认构造函数
     * 
     * @param fileType
     *            指定文件后缀
     */
    public SpecailFileFilter(String fileType)
    {
        super();
        this.fileType = fileType;
    }

    /*
     * （非 Javadoc）
     * 
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File f)
    {
        // 过滤空文件
        if (f == null)
            return false;
        // 不过滤目录
        if (f.isDirectory())
            return true;
        // 返回xml文件
        return f.getAbsolutePath().endsWith(fileType);
    }

    /*
     * （非 Javadoc）
     * 
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    @Override
    public String getDescription()
    {
        return "*" + fileType;
    }

}
