/*
 * @作者:Hades , 创建日期:May 31, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.spring;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.lucene.analysis.index.HickwallIndexAnalyzer;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;

/**
 * @author Mac Kwan Lucene分词分析器工厂
 */
public class HickwallAnalyzerFactory
{
    /**
     * Lucene分词器工厂单键
     */
    private static HickwallAnalyzerFactory singleton = null;

    /**
     * 根据spring配置文档路径获取相应的Lucene分词器工厂实例
     * 
     * @param contextPath
     *            spring配置文档路径
     * @return Lucene分词器工厂实例
     */
    public static HickwallAnalyzerFactory getInstance(String contextPath)
    {
        if (singleton == null)
        {
            singleton = new HickwallAnalyzerFactory(contextPath);
        }
        return singleton;
    }

    /**
     * Spring控制器
     */
    private ApplicationContext context = null;

    /**
     * 构造函数
     * 
     * @param contextPath
     *            spring配置文档路径
     */
    private HickwallAnalyzerFactory(String contextPath)
    {
        context = new FileSystemXmlApplicationContext(contextPath);
    }

    /**
     * 返回建立索引用的分词器
     * 
     * @return 查询用的分词器
     */
    public Analyzer getIndexAnalyzer()
    {
        if (context == null)
            return null;
        else
            return (HickwallIndexAnalyzer) context
                    .getBean("hickwallIndexAnalyzer");
    }

    /**
     * 返回查询用的分词器
     * 
     * @return 查询用的分词器
     */
    public Analyzer getQueryAnalyzer()
    {
        if (context == null)
            return null;
        else
            return (HickwallQueryAnalyzer) context
                    .getBean("hickwallQueryAnalyzer");
    }

}
