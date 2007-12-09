/*
 * @作者:Hades , 创建日期:May 16, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.unlistedword.name.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;

/**
 * @author Mac Kwan
 * 
 */
public class ChineseNameDictionaryTest extends TestCase
{
    private ChineseNameDictionary dic = null;

    /*
     * （非 Javadoc）
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        firstNameDic.loadDictionary("Dic/FirstName.txt");
        dic = new ChineseNameDictionary(firstNameDic);
    }

    public final void testLoadDictionary()
    {
        dic.loadDictionary("Dic/Name.txt");
        dic.print(System.out);
    }

    public final void testMatch()
    {
        dic.loadDictionary("Dic/Name.txt");
        assertTrue(dic.match("关明晖"));
        assertFalse(dic.match("asd"));
    }

}
