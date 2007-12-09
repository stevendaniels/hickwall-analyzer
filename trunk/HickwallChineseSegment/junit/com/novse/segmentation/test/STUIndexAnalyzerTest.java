/*
 * @作者:Hades , 创建日期:May 31, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.novse.segmentation.spring.HickwallAnalyzerFactory;

public class STUIndexAnalyzerTest extends TestCase
{
    private Analyzer analyzer = null;

    public final void testTokenStreamReader1() throws IOException
    {
        PrintStream out1 = new PrintStream(new BufferedOutputStream(
                new FileOutputStream("result/基于词典和统计的结果.txt")));
        // System.setOut(out1);

        long start = System.currentTimeMillis();

        analyzer = HickwallAnalyzerFactory.getInstance("STUSegmentConfig.xml")
                .getIndexAnalyzer();

        Reader r = new FileReader("news.txt");
        // Reader r=new StringReader("太古红军");
        TokenStream ts = analyzer.tokenStream(null, r);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "毫秒");

        Token t;
        while ((t = ts.next()) != null)
        {
            System.out.println(t);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start + "毫秒");
        out1.flush();
        out1.close();
    }

    public final void estTokenStreamReader2() throws Exception
    {
        PrintStream out2 = new PrintStream(new BufferedOutputStream(
                new FileOutputStream("result/基于词典的结果.txt")));
        System.setOut(out2);
        long start = System.currentTimeMillis();

        analyzer = HickwallAnalyzerFactory.getInstance("STUSegmentConfig.xml")
                .getIndexAnalyzer();

        Reader r = new FileReader("火狼王.txt");
        TokenStream ts = analyzer.tokenStream(null, r);

        Token t;
        while ((t = ts.next()) != null)
        {
            System.out.println(t);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "毫秒");
        out2.flush();
        out2.close();
    }
}
