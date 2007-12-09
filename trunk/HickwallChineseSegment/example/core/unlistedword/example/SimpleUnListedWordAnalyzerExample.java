/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
 * 
 */
package core.unlistedword.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.novse.segmentation.core.unlistedword.SimpleUnListedWordAnalyzer;
import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;

/**
 * @author Mac Kwan 未登录词识别分析器
 */
public class SimpleUnListedWordAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 词频词典
        CharFrequenceDictionary frequenceDic = new CharFrequenceDictionary();
        frequenceDic.loadDictionary("Dic/Txt/词频词库.txt");

        // 未登录词识别器
        SimpleUnListedWordAnalyzer analyzer = new SimpleUnListedWordAnalyzer(
                frequenceDic);

        // 对字符串进行单字分拆
        String source = "11月29日，原北京师范大学校长、原汕头大学数学研究所所长、中国科学院院士王梓坤先生莅临我校，他通过自己做研究生和指导研究生的亲身经历，在学术交流中心为广大导师和研究生做了题为《研究生教学四十年》的报告。";
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
