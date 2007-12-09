/*
 * @����:Hades , ��������:May 16, 2007
 *
 * ��ͷ��ѧ03���������
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
     * ���� Javadoc��
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
        assertTrue(dic.match("������"));
        assertFalse(dic.match("asd"));
    }

}
