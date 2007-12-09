/*
 * @作者:Hades , 创建日期:2006-11-18
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

import java.io.IOException;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.SegmentProcessor;

public class ReverseMaxMatchSegmentProcessorTest extends TestCase
{
    private SegmentProcessor processor = null;

    protected void setUp() throws Exception
    {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "STUSegmentConfig.xml");
        this.processor = (SegmentProcessor) context
                .getBean("reverseMaxMatchSegmentProxy");
    }

    public final void testFileProcessor() throws IOException
    {
        long start = System.currentTimeMillis();
        processor.fileProcessor("news.txt", "reverse-result.txt");
        long end = System.currentTimeMillis();
        System.out.println("分词耗时：" + (end - start));
    }

    public final void estTextProcess()
    {
        for (String s : processor.textProcess("20世纪80年代以来"))
            System.out.println(s);
    }

}
