/*
 * @作者:Hades , 创建日期:Apr 18, 2007
 *
 * 汕头大学03计算机本科
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
