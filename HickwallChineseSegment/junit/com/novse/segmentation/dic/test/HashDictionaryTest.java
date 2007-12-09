/*
 * @作者:Hades , 创建日期:2006-11-19
 *
 * 汕头大学03计算机本科
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
        System.out.println("载入词库耗时：" + (end - start));
    }

    public final void testMatch()
    {
        assertTrue(dic.match("广州市"));
        assertFalse(dic.match("关明晖"));
    }

    public final void estPrint()
    {
        dic.print(System.out);
    }

}
