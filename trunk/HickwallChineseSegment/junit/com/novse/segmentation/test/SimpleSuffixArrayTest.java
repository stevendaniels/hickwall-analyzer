/*
 * @����:Hades , ��������:2006-11-15
 *
 * ��ͷ��ѧ03���������
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
        this.tool = new SimpleSuffixArray("��������������򼰼�����������");
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
