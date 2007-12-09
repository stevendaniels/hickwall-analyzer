/*
 * @����:Hades , ��������:2006-11-19
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.matching.dictionary.HashDictionary;

public class HashDictionaryTest extends TestCase
{
    private HashDictionary dic = new HashDictionary();

    protected void setUp() throws Exception
    {
        super.setUp();
        long start = System.currentTimeMillis();
        dic.loadDictionary("dic/SmallDic.txt");
        long end = System.currentTimeMillis();
        System.out.println("����ʿ��ʱ��" + (end - start));
    }

    public final void testMatch()
    {
        assertTrue(dic.match("������"));
        assertFalse(dic.match("������"));
    }

    public final void estPrint()
    {
        dic.print(System.out);
    }

}
