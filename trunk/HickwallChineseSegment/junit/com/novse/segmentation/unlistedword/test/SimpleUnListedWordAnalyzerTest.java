/*
 * @作者:Hades , 创建日期:2007-4-10
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.unlistedword.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.core.unlistedword.SimpleUnListedWordAnalyzer;
import com.novse.segmentation.core.unlistedword.UnListedWordAnalyzer;
import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.util.DictionaryUtil;

public class SimpleUnListedWordAnalyzerTest extends TestCase
{
    private UnListedWordAnalyzer analyzer = null;

    private CharFrequenceDictionary dic = null;

    private List<String> list = new ArrayList<String>();

    protected void setUp() throws Exception
    {
        super.setUp();
        BufferedReader in = new BufferedReader(new FileReader("火狼王.txt"));
        String text = "";
        String line = null;
        while ((line = in.readLine()) != null)
            text += line;

        ApplicationContext context = new FileSystemXmlApplicationContext(
                "springConfig.xml");
        MaxMatchSegmentProcessor processor = (MaxMatchSegmentProcessor) context
                .getBean("maxMatchSegmentBean");
        this.list = processor.textProcess(text);

        dic = new DictionaryUtil<CharFrequenceDictionary>()
                .readDictionary("Dic/CharFrequenceDic.stu");
        analyzer = new SimpleUnListedWordAnalyzer(dic);
    }

    public final void testIdentify()
    {
        for (String s : analyzer.identify(list))
        {
            System.out.println(s);
        }
    }

}
