/*
 * @作者:Hades , 创建日期:May 27, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.dic.test;

import java.io.BufferedReader;
import java.io.FileReader;

import junit.framework.TestCase;

import org.apache.commons.lang.time.StopWatch;

import com.novse.segmentation.core.matching.dictionary.DiagramDictionary;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan 用于比较各种词典性能的测试用例
 */
public class DictionaryTest extends TestCase
{
    /**
     * 词典实例
     */
    private Dictionary dic = null;

    private static final String DICPATH = "Dic/Txt/Dic.txt";

    private static final String WORDPATH = "TestMatch.txt";

    /*
     * （非 Javadoc）
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        dic = new DiagramDictionary();
    }

    /**
     * 测试载入词库时间
     * 
     * @throws Exception
     */
    public void estLoadTime() throws Exception
    {
        StopWatch sw = new StopWatch();
        sw.start();
        dic.loadDictionary(DICPATH);
        sw.stop();
        System.out.println("载入词典时间：" + sw.getTime() + "ms");
    }

    /**
     * 顺序遍历所有词汇
     * 
     * @throws Exception
     */
    public void estMatchAll() throws Exception
    {
        StopWatch sw = new StopWatch();
        dic.loadDictionary(DICPATH);
        BufferedReader in = new BufferedReader(new FileReader(DICPATH));
        String line = null;

        sw.start();
        while ((line = in.readLine()) != null)
        {
            assertTrue(dic.match(line));
        }
        in.close();
        sw.stop();
        System.out.println("顺序遍历所有词汇时间：" + sw.getTime() + "ms");
    }

    /**
     * 随机查询部分词汇
     * 
     * @throws Exception
     */
    public void testMatch() throws Exception
    {
        StopWatch sw = new StopWatch();
        dic.loadDictionary(DICPATH);
        BufferedReader in = new BufferedReader(new FileReader(WORDPATH));
        String line = null;

        sw.start();
        while ((line = in.readLine()) != null)
        {
            dic.match(line);
        }
        in.close();
        sw.stop();
        System.out.println("随机查询部分词汇时间：" + sw.getTime() + "ms");
    }

}
