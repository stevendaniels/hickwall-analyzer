/*
 * @作者:Hades , 创建日期:May 30, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

import java.io.FileReader;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class StandardAnalyzerTest extends TestCase
{
    private StandardAnalyzer analyzer = new StandardAnalyzer();

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    public final void testTokenStream() throws Exception
    {
        FileReader in = new FileReader("news.txt");
        TokenStream ts = analyzer.tokenStream(null, in);
        Token t = null;
        while ((t = ts.next()) != null)
            System.out.println(t);
        in.close();
    }

}
