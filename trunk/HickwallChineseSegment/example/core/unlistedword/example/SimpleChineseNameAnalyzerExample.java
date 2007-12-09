/*
 * @����:Mac Kwan , ��������:2007-12-8
 *
 * ��ͷ��ѧ03���������
 * 
 */
package core.unlistedword.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;

/**
 * @author Mac Kwan ��������ʶ�������
 */
public class SimpleChineseNameAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // ���ϴʵ�
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        firstNameDic.loadDictionary("Dic/Txt/FirstName.txt");

        // ���������ʵ�
        ChineseNameDictionary nameDic = new ChineseNameDictionary(firstNameDic);
        nameDic.loadDictionary("Dic/Txt/Name.txt");

        // ���Ͽ�ͷ�����������Ĵʵ�
        NotChineseNameDictionary notNameDic = new NotChineseNameDictionary(
                firstNameDic);
        notNameDic.loadDictionary("Dic/Txt/UnName.txt");

        // ��߽�ʴʵ�
        Dictionary leftVergeDic = new HashDictionary();
        leftVergeDic.loadDictionary("Dic/Txt/LeftVerge.txt");

        // �ұ߽�ʴʵ�
        Dictionary rightVergeDic = new HashDictionary();
        rightVergeDic.loadDictionary("Dic/Txt/RightVerge.txt");

        // ��������ʶ����
        SimpleChineseNameAnalyzer analyzer = new SimpleChineseNameAnalyzer(
                nameDic, notNameDic, leftVergeDic, rightVergeDic);

        // ���ַ������е��ֲַ�
        String source = "11��29�գ�ԭ����ʦ����ѧУ����ԭ��ͷ��ѧ��ѧ�о����������й���ѧԺԺʿ����������ݰ����У����ͨ���Լ����о�����ָ���о���������������ѧ����������Ϊ���ʦ���о���������Ϊ���о�����ѧ��ʮ�꡷�ı��档";
        String[] array = source.split("");
        List<String> result = new ArrayList<String>(Arrays.asList(array));

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        result = analyzer.identify(result);

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
    }

}
