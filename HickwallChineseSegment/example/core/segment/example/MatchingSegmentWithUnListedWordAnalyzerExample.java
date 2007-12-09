/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
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
 * @author Mac Kwan 结合未登录词识别修正的分词
 */
public class MatchingSegmentWithUnListedWordAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 词典接口
        Dictionary dic = new SimpleDictionary();

        // 从文本文件中载入词库
        dic.loadDictionary("Dic/Txt/SmallDic.txt");

        // 基于匹配的分词处理器
        SegmentProcessor target = new MaxMatchSegmentProcessor(dic);
        // SegmentProcessor target = new
        // ReverseMaxMatchSegmentProcessor(dic);

        // 未登录词识别
        // 姓氏词典
        ChineseFirstNameDictionary firstNameDic = new ChineseFirstNameDictionary();
        firstNameDic.loadDictionary("Dic/Txt/FirstName.txt");

        // 中文姓名词典
        ChineseNameDictionary nameDic = new ChineseNameDictionary(firstNameDic);
        nameDic.loadDictionary("Dic/Txt/Name.txt");

        // 姓氏开头非中文姓名的词典
        NotChineseNameDictionary notNameDic = new NotChineseNameDictionary(
                firstNameDic);
        notNameDic.loadDictionary("Dic/Txt/UnName.txt");

        // 左边界词词典
        Dictionary leftVergeDic = new HashDictionary();
        leftVergeDic.loadDictionary("Dic/Txt/LeftVerge.txt");

        // 右边界词词典
        Dictionary rightVergeDic = new HashDictionary();
        rightVergeDic.loadDictionary("Dic/Txt/RightVerge.txt");

        // 未登录词识别分析器
        UnListedWordAnalyzer analyzer = new SimpleChineseNameAnalyzer(nameDic,
                notNameDic, leftVergeDic, rightVergeDic);
        // end of 未登录词识别

        // 代理
        SegmentProcessor processor = new SegmentProcessorUnListedWordProxy(
                target, analyzer);

        // 对文件进行分词
        processor.fileProcessor("news.txt", "result.txt");

        // 对字符串进行分词
        List<String> result = processor
                .textProcess("11月29日，原北京师范大学校长、原汕头大学数学研究所所长、中国科学院院士王梓坤先生莅临我校，他通过自己做研究生和指导研究生的亲身经历，在学术交流中心为广大导师和研究生做了题为《研究生教学四十年》的报告。");

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }

    }

}
