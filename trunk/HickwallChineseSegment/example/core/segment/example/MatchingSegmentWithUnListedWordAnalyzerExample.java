/*
 * @����:Mac Kwan , ��������:2007-12-8
 *
 * ��ͷ��ѧ03���������
 * 
 */
package core.segment.example;

import java.util.List;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.core.unlistedword.UnListedWordAnalyzer;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;
import com.novse.segmentation.proxy.SegmentProcessorUnListedWordProxy;

/**
 * @author Mac Kwan ���δ��¼��ʶ�������ķִ�
 */
public class MatchingSegmentWithUnListedWordAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // �ʵ�ӿ�
        Dictionary dic = new SimpleDictionary();

        // ���ı��ļ�������ʿ�
        dic.loadDictionary("Dic/Txt/SmallDic.txt");

        // ����ƥ��ķִʴ�����
        SegmentProcessor target = new MaxMatchSegmentProcessor(dic);
        // SegmentProcessor target = new
        // ReverseMaxMatchSegmentProcessor(dic);

        // δ��¼��ʶ��
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

        // δ��¼��ʶ�������
        UnListedWordAnalyzer analyzer = new SimpleChineseNameAnalyzer(nameDic,
                notNameDic, leftVergeDic, rightVergeDic);
        // end of δ��¼��ʶ��

        // ����
        SegmentProcessor processor = new SegmentProcessorUnListedWordProxy(
                target, analyzer);

        // ���ļ����зִ�
        processor.fileProcessor("news.txt", "result.txt");

        // ���ַ������зִ�
        List<String> result = processor
                .textProcess("11��29�գ�ԭ����ʦ����ѧУ����ԭ��ͷ��ѧ��ѧ�о����������й���ѧԺԺʿ����������ݰ����У����ͨ���Լ����о�����ָ���о���������������ѧ����������Ϊ���ʦ���о���������Ϊ���о�����ѧ��ʮ�꡷�ı��档");

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }

    }

}
