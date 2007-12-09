/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
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
        System.out.println("载入词库耗时：" + (end - start));
        System.out.println(dic.getWordCount());
    }

    public final void estPrint()
    {
        // dic.print(System.out);
    }

    public final void testMatch()
    {
        long start = System.currentTimeMillis();
        assertTrue(dic.match("广州市"));
        assertFalse(dic.match("关明晖"));
        long end = System.currentTimeMillis();
        System.out.println("匹配耗时：" + (end - start));
    }

}
