/*
 * @����:Hades , ��������:Apr 18, 2007
 *
 * ��ͷ��ѧ03���������
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
     * Ĭ�Ϲ��캯��
     * 
     * @param fileType
     *            ָ���ļ���׺
     */
    public SpecailFileFilter(String fileType)
    {
        super();
        this.fileType = fileType;
    }

    /*
     * ���� Javadoc��
     * 
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File f)
    {
        // ���˿��ļ�
        if (f == null)
            return false;
        // ������Ŀ¼
        if (f.isDirectory())
            return true;
        // ����xml�ļ�
        return f.getAbsolutePath().endsWith(fileType);
    }

    /*
     * ���� Javadoc��
     * 
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    @Override
    public String getDescription()
    {
        return "*" + fileType;
    }

}
