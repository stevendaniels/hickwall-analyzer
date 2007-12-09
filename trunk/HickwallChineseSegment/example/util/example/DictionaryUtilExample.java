/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
 * 
 */
package util.example;

import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.util.DictionaryUtil;

/**
 * @author Mac Kwan 词典工具类，用于序列化与反序列化词典实例
 */
public class DictionaryUtilExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        CharFrequenceDictionary dic = new CharFrequenceDictionary();

        DictionaryUtil<CharFrequenceDictionary> tool = new DictionaryUtil<CharFrequenceDictionary>();

        dic.loadDictionary("Dic/Txt/词频词库.txt");
        tool.writeDictionary(dic, "Dic/test.stu");
        
        dic=new CharFrequenceDictionary();
        dic=tool.readDictionary("Dic/test.stu");
    }

}
