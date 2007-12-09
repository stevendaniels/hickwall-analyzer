/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
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
                .textProcess("18岁那年，有个自称算命先生看了我的手相后说，此生你将注定与男人纠缠不清。"))
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
        processor.fileProcessor("无邪.txt", "result.txt");
        long end = System.currentTimeMillis();
        System.out.println("分词耗时：" + (end - start));
    }

    public final void testSample()
    {
        HashDictionary dic = new HashDictionary();
        dic.loadDictionary("Dic/SmallDic.txt");
        MaxMatchSegmentProcessor p = new MaxMatchSegmentProcessor(dic);
        for (String s : p.textProcess("江泽民是个好主席。"))
        {
            System.out.println(s);
        }
    }
}
