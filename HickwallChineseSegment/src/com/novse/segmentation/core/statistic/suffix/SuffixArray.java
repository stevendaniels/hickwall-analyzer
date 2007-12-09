/*
 * @作者:Hades , 创建日期:2006-11-15
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.suffix;

/**
 * @author Mac Kwan 计算获得后缀数组操作的接口
 */
public interface SuffixArray
{
    /**
     * 求字符串str后缀数组
     * 
     * @param str
     *            需要求它后缀数组的字符串
     * @return str对应的后缀数组
     */
    public int[] getSuffixArray(String str);

    /**
     * 求存放str对应后缀数组相邻两后缀的最长前缀长度的数组
     * 
     * @param str
     *            需要求它后缀数组的字符串
     * @return 存放str对应后缀数组相邻两后缀的最长前缀长度的数组
     */
    public int[] getLCP(String str);
}
