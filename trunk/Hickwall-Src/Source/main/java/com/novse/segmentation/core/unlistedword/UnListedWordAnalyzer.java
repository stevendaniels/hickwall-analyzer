/*
 * @作者:Hades , 创建日期:2007-4-6
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

/**
 * @author Mac Kwan 基于分词碎片的未登录词识别接口
 */
public interface UnListedWordAnalyzer
{
    /**
     * 对输入的原分词结果srcList进行未登录词识别
     * 
     * @param srcList
     *            原分词结果
     * @return 未登录词识别处理后的分词结果
     */
    public List<String> identify(List<String> srcList);
}
