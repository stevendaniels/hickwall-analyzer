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
import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.core.statistic.fetcher.BasedSuffixArrayStringFetcher;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;

/**
 * @author Mac Kwan ��ͳ��ʶ��ʻ����ϵ����ķִ�
 */
public class MatchSegmentWithStatisticExample
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

        // ͳ��ʶ��ʻ�
        StringFetcher suffixArray = new BasedSuffixArrayStringFetcher(0.8, 0.2);

        // ����ƥ��ķִʴ�����
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic,
                suffixArray);
        // SegmentProcessor processor = new
        // ReverseMaxMatchSegmentProcessor(dic);

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
