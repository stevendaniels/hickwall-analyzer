/*
 * @����:Hades , ��������:May 31, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.spring;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.lucene.analysis.index.HickwallIndexAnalyzer;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;

/**
 * @author Mac Kwan Lucene�ִʷ���������
 */
public class HickwallAnalyzerFactory
{
    /**
     * Lucene�ִ�����������
     */
    private static HickwallAnalyzerFactory singleton = null;

    /**
     * ����spring�����ĵ�·����ȡ��Ӧ��Lucene�ִ�������ʵ��
     * 
     * @param contextPath
     *            spring�����ĵ�·��
     * @return Lucene�ִ�������ʵ��
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
     * Spring������
     */
    private ApplicationContext context = null;

    /**
     * ���캯��
     * 
     * @param contextPath
     *            spring�����ĵ�·��
     */
    private HickwallAnalyzerFactory(String contextPath)
    {
        context = new FileSystemXmlApplicationContext(contextPath);
    }

    /**
     * ���ؽ��������õķִ���
     * 
     * @return ��ѯ�õķִ���
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
     * ���ز�ѯ�õķִ���
     * 
     * @return ��ѯ�õķִ���
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
