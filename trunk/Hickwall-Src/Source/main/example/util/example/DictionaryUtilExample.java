/*
 * @����:Mac Kwan , ��������:2007-12-8
 *
 * ��ͷ��ѧ03���������
 * 
 */
package util.example;

import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.util.DictionaryUtil;

/**
 * @author Mac Kwan �ʵ乤���࣬�������л��뷴���л��ʵ�ʵ��
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

        dic.loadDictionary("Dic/Txt/��Ƶ�ʿ�.txt");
        tool.writeDictionary(dic, "Dic/test.stu");
        
        dic=new CharFrequenceDictionary();
        dic=tool.readDictionary("Dic/test.stu");
    }

}
