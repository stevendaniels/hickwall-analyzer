/*
 * @作者:Hades , 创建日期:2007-3-25
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.matching.dictionary.TrieTreeDictionary;

/**
 * @author Mac Kwan
 * 
 */
public class TrieTreeDictionaryTest extends TestCase
{
    private TrieTreeDictionary dic = new TrieTreeDictionary();

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

    public final void estMatch()
    {
        assertTrue(dic.match("牧人"));
        assertTrue(dic.match("着重"));
        assertTrue(dic.match("着重地"));
        assertFalse(dic.match("江江第"));
    }

    public final void testInsertWord()
    {
        assertFalse(dic.match("关明晖"));
        dic.insertWord("关明晖");
        assertTrue(dic.match("关明晖"));
        dic.deleteWord("关明晖");
        assertFalse(dic.match("关明晖"));
    }

    public final void estDeleteWord()
    {
        assertTrue(dic.match("阿根廷"));
        assertTrue(dic.match("阿根廷人"));

        dic.deleteWord("阿根廷");
        assertFalse(dic.match("阿根廷"));
        assertTrue(dic.match("阿根廷人"));

        dic.deleteWord("阿根廷人");
        assertFalse(dic.match("阿根廷"));
        assertFalse(dic.match("阿根廷人"));

    }
}
