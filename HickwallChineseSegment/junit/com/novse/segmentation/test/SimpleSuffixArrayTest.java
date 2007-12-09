/*
 * @作者:Hades , 创建日期:2006-11-15
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.statistic.suffix.SimpleSuffixArray;

public class SimpleSuffixArrayTest extends TestCase
{
    private SimpleSuffixArray tool = null;

    protected void setUp() throws Exception
    {
        this.tool = new SimpleSuffixArray("计算机与计算机程序及计算机程序设计");
    }

    public final void testSimpleSuffixArray()
    {
        for (int i : tool.getSuffixArray())
        {
            System.out.println("suffix:\t" + i);
        }
    }

    public final void testSimpleSuffixArrayString()
    {
        for (int i : tool.getLCP())
        {
            System.out.println("lcp:\t" + i);
        }
    }

}
