/*
 * @����:Hades , ��������:2006-11-18
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.util.DictionaryUtil;

public class SimpleDictionaryTest extends TestCase
{
    private SimpleDictionary dic;

    private DictionaryUtil<SimpleDictionary> tool = new DictionaryUtil<SimpleDictionary>();

    protected void setUp() throws Exception
    {
        super.setUp();
        long start = System.currentTimeMillis();
        dic = tool.readDictionary("dic/SimpleDic.stu");
        long end = System.currentTimeMillis();
        System.out.println("����ʿ��ʱ��" + (end - start));
    }

    public final void testMatch()
    {
        assertTrue(dic.match("������"));
        assertFalse(dic.match("������"));
    }

}
