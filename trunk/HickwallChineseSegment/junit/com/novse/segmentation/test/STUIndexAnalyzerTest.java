/*
 * @����:Hades , ��������:May 31, 2007
 *
 * ��ͷ��ѧ03���������
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
                new FileOutputStream("result/���ڴʵ��ͳ�ƵĽ��.txt")));
        // System.setOut(out1);

        long start = System.currentTimeMillis();

        analyzer = HickwallAnalyzerFactory.getInstance("STUSegmentConfig.xml")
                .getIndexAnalyzer();

        Reader r = new FileReader("news.txt");
        // Reader r=new StringReader("̫�ź��");
        TokenStream ts = analyzer.tokenStream(null, r);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "����");

        Token t;
        while ((t = ts.next()) != null)
        {
            System.out.println(t);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start + "����");
        out1.flush();
        out1.close();
    }

    public final void estTokenStreamReader2() throws Exception
    {
        PrintStream out2 = new PrintStream(new BufferedOutputStream(
                new FileOutputStream("result/���ڴʵ�Ľ��.txt")));
        System.setOut(out2);
        long start = System.currentTimeMillis();

        analyzer = HickwallAnalyzerFactory.getInstance("STUSegmentConfig.xml")
                .getIndexAnalyzer();

        Reader r = new FileReader("������.txt");
        TokenStream ts = analyzer.tokenStream(null, r);

        Token t;
        while ((t = ts.next()) != null)
        {
            System.out.println(t);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + "����");
        out2.flush();
        out2.close();
    }
}
