/*
 * @作者:Hades , 创建日期:2007-4-6
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core;

/**
 * @author Mac Kwan 文本文件信息可载入接口
 */
public interface Loadable
{
    /**
     * 载入以文本格式存储的词典
     * 
     * @param fileName
     *            词典的文件名
     */
    public void loadDictionary(String fileName);
}
