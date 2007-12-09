/*
 * @作者:Hades , 创建日期:Apr 17, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.dic.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.matching.dictionary.DiagramDictionary;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan
 * 
 */
public class DiagramDictionaryTest extends TestCase
{
    private Dictionary dic = new DiagramDictionary();

    /*
     * （非 Javadoc）
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        long start = System.currentTimeMillis();
        dic.loadDictionary("dic/SmallDic.txt");
        long end = System.currentTimeMillis();
        System.out.println("载入词库耗时：" + (end - start));
    }

    public final void testMatch() throws Exception
    {
        BufferedReader in = new BufferedReader(new FileReader(
                "dic/SmallDic.txt"));
        String line = null;
        while ((line = in.readLine()) != null)
        {
            if (!StringUtils.isBlank(line))
            {
                System.out.println(line);
                assertTrue(dic.match(line.trim()));
            }
        }
        in.close();
    }

    public final void estPrint() throws Exception
    {
        PrintStream out = new PrintStream(new BufferedOutputStream(
                new FileOutputStream("result.txt")));
        dic.print(out);
        out.close();
    }

}
