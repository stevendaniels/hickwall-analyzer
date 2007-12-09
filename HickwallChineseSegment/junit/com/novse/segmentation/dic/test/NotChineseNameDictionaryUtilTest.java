/*
 * @����:Hades , ��������:2007-5-21
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.util.DictionaryUtil;

/**
 * @author Mac Kwan
 * 
 */
public class NotChineseNameDictionaryUtilTest extends TestCase
{
    private NotChineseNameDictionary dic;

    private DictionaryUtil<NotChineseNameDictionary> tool = new DictionaryUtil<NotChineseNameDictionary>();

    public final void estReadDictionary()
    {
        // SimpleDictionary dictionary = tool
        // .readDictionary("dic/TrieTreeDic.stu");
        // try
        // {
        // PrintStream out = new PrintStream(new BufferedOutputStream(
        // new FileOutputStream("result.txt")));
        // dictionary.print(out);
        // out.close();
        // }
        // catch (FileNotFoundException e)
        // {
        // // TODO �Զ����� catch ��
        // e.printStackTrace();
        // }
    }

    public final void testWriteDictionary()
    {
        dic = new NotChineseNameDictionary(
                new DictionaryUtil<ChineseFirstNameDictionary>()
                        .readDictionary("Dic/FirstName.stu"));
        dic.loadDictionary("Dic/UnName.txt");
        assertTrue(tool.writeDictionary(dic, "Dic/UnName.stu"));
    }
}
