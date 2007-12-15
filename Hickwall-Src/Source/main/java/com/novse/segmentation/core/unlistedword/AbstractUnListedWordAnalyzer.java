/*
 * @作者:Hades , 创建日期:2007-4-10
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan 未登录词识别器的抽象实现类
 */
public abstract class AbstractUnListedWordAnalyzer implements
        UnListedWordAnalyzer
{
    /**
     * 分隔符字符串
     */
    protected String seperator = null;

    /**
     * 对输入的原分词结果srcList进行未登录词识别
     * 
     * @param srcList
     *            原分词结果
     * @return 未登录词识别处理后的分词结果
     */
    abstract public List<String> identify(List<String> srcList);

    /**
     * 初始化分隔符的方法
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveStringWithNumberAndChar();
    }

}
