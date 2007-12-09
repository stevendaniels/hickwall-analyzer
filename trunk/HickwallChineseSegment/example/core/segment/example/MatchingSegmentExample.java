/*
 * @作者:Mac Kwan , 创建日期:2007-12-2
 *
 * 汕头大学03计算机本科
 * 
 */
package core.segment.example;

import java.util.List;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;

/**
 * @author Mac Kwan 基于匹配的分词例子
 */
public class MatchingSegmentExample
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
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic);
        // SegmentProcessor processor = new
        // ReverseMaxMatchSegmentProcessor(dic);

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
