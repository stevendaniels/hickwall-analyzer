/*
 * @����:Hades , ��������:2006-11-17
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;

public class MaxMatchSegmentProcessorTest extends TestCase
{
    private MaxMatchSegmentProcessor processor;

    public final void estTextProcess()
    {
        for (String s : processor
                .textProcess("18�����꣬�и��Գ��������������ҵ������˵�������㽫ע�������˾������塣"))
            System.out.print(s + " ");
    }

    protected void setUp() throws Exception
    {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "STUSegmentConfig.xml");
        this.processor = (MaxMatchSegmentProcessor) context
                .getBean("maxMatchSegmentBean");
    }

    public final void estFileProcessor()
    {
        long start = System.currentTimeMillis();
        processor.fileProcessor("��а.txt", "result.txt");
        long end = System.currentTimeMillis();
        System.out.println("�ִʺ�ʱ��" + (end - start));
    }

    public final void testSample()
    {
        HashDictionary dic = new HashDictionary();
        dic.loadDictionary("Dic/SmallDic.txt");
        MaxMatchSegmentProcessor p = new MaxMatchSegmentProcessor(dic);
        for (String s : p.textProcess("�������Ǹ�����ϯ��"))
        {
            System.out.println(s);
        }
    }
}
