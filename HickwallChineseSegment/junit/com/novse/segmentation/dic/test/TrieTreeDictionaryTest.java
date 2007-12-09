/*
 * @����:Hades , ��������:2007-3-25
 *
 * ��ͷ��ѧ03���������
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
     * ���� Javadoc��
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        long start = System.currentTimeMillis();
        dic.loadDictionary("dic/SmallDic.txt");
        long end = System.currentTimeMillis();
        System.out.println("����ʿ��ʱ��" + (end - start));
    }

    public final void estMatch()
    {
        assertTrue(dic.match("����"));
        assertTrue(dic.match("����"));
        assertTrue(dic.match("���ص�"));
        assertFalse(dic.match("������"));
    }

    public final void testInsertWord()
    {
        assertFalse(dic.match("������"));
        dic.insertWord("������");
        assertTrue(dic.match("������"));
        dic.deleteWord("������");
        assertFalse(dic.match("������"));
    }

    public final void estDeleteWord()
    {
        assertTrue(dic.match("����͢"));
        assertTrue(dic.match("����͢��"));

        dic.deleteWord("����͢");
        assertFalse(dic.match("����͢"));
        assertTrue(dic.match("����͢��"));

        dic.deleteWord("����͢��");
        assertFalse(dic.match("����͢"));
        assertFalse(dic.match("����͢��"));

    }
}
