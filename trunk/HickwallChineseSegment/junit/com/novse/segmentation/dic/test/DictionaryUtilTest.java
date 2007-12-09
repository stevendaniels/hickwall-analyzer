/*
 * @作者:Hades , 创建日期:2006-11-17
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.dic.test;

import junit.framework.TestCase;

import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.util.DictionaryUtil;

public class DictionaryUtilTest extends TestCase
{
    private CharFrequenceDictionary dic = new CharFrequenceDictionary();

    private DictionaryUtil<CharFrequenceDictionary> tool = new DictionaryUtil<CharFrequenceDictionary>();

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
        dic.loadDictionary("Dic/Txt/词频词库.txt");
        assertTrue(tool.writeDictionary(dic, "Dic/CharFrequenceDic.stu"));
    }

}
