/*
 * @����:Hades , ��������:2006-11-17
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.matching.dictionary.DoubleHashDictionary;
import com.novse.segmentation.util.DictionaryUtil;

public class DoubleHashDictionaryTest extends TestCase
{
    private DoubleHashDictionary dic;

    private DictionaryUtil<DoubleHashDictionary> tool = new DictionaryUtil<DoubleHashDictionary>();

    protected void setUp() throws Exception
    {
        super.setUp();
        long start = System.currentTimeMillis();
        dic = tool.readDictionary("dic/DoubleHashDic.stu");
        long end = System.currentTimeMillis();
        System.out.println("����ʿ��ʱ��" + (end - start));
        System.out.println(dic.getWordCount());
    }

    public final void estPrint()
    {
        // dic.print(System.out);
    }

    public final void testMatch()
    {
        long start = System.currentTimeMillis();
        assertTrue(dic.match("������"));
        assertFalse(dic.match("������"));
        long end = System.currentTimeMillis();
        System.out.println("ƥ���ʱ��" + (end - start));
    }

}
