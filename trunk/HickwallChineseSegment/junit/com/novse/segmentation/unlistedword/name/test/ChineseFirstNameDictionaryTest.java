/*
 * @作者:Hades , 创建日期:May 15, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.unlistedword.name.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;

/**
 * @author Mac Kwan
 * 
 */
public class ChineseFirstNameDictionaryTest extends TestCase
{
    private ChineseFirstNameDictionary dic = null;

    /*
     * （非 Javadoc）
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        this.dic = new ChineseFirstNameDictionary();
        this.dic.loadDictionary("Dic/FirstName.txt");
    }

    public final void testPrint()
    {
        dic.print(System.out);
    }

    public final void testMatch()
    {
        assertTrue(this.dic.match("关"));
        assertFalse(this.dic.match("就"));
    }

}
