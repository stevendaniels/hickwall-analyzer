/*
 * @作者:Hades , 创建日期:May 20, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.util.DictionaryUtil;

public class ChineseNameDictionaryUtilTest extends TestCase
{
    private ChineseNameDictionary dic;

    private DictionaryUtil<ChineseNameDictionary> tool = new DictionaryUtil<ChineseNameDictionary>();

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
        // // TODO 自动生成 catch 块
        // e.printStackTrace();
        // }
    }

    public final void testWriteDictionary()
    {
        dic = new ChineseNameDictionary(
                new DictionaryUtil<ChineseFirstNameDictionary>()
                        .readDictionary("Dic/FirstName.stu"));
        dic.loadDictionary("Dic/Name.txt");
        assertTrue(tool.writeDictionary(dic, "Dic/Name.stu"));
    }
}
