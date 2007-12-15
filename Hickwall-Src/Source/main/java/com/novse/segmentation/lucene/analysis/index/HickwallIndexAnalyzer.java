/*
 * @作者:Hades , 创建日期:May 31, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.lucene.analysis.index;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan 用于建立索引的Lucene分词分析器
 */
public class HickwallIndexAnalyzer extends Analyzer
{
    /**
     * 一些不常用于搜索的词汇
     */
    public final static String[] STOP_WORDS =
    { "a", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
            "into", "is", "it", "no", "not", "of", "on", "or", "s", "such",
            "t", "that", "the", "their", "then", "there", "these", "they",
            "this", "to", "was", "will", "with", "", "www" };

    /**
     * 分词构件
     */
    private SegmentProcessor processor = null;

    /**
     * 分隔符列表
     */
    private Set stopTable;

    /**
     * 使用默认分隔符的构造函数
     * 
     * @param processor
     *            需要使用分词构件
     */
    public HickwallIndexAnalyzer(SegmentProcessor processor)
    {
        this.processor = processor;
        this.stopTable = StopWordMaker.retreiveSet();
    }

    /**
     * 使用指定分隔符的构造函数
     * 
     * @param processor
     *            需要使用分词构件
     * @param stopWords
     *            指定的分隔符
     */
    public HickwallIndexAnalyzer(SegmentProcessor processor, String[] stopWords)
    {
        this.processor = processor;
        this.stopTable = StopFilter.makeStopSet(stopWords);
    }

    /**
     * @return 返回 processor。
     */
    public SegmentProcessor getProcessor()
    {
        return processor;
    }

    /**
     * @param processor
     *            要设置的 processor。
     */
    public void setProcessor(SegmentProcessor processor)
    {
        this.processor = processor;
    }

    /*
     * （非 Javadoc）
     * 
     * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String,
     *      java.io.Reader)
     */
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader)
    {
        return new StopFilter(
                new HickwallIndexTokenizer(this.processor, reader),
                this.stopTable);
    }
}
