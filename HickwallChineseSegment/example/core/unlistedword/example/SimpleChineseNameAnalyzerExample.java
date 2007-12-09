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

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;

/**
 * @author Mac Kwan 中文姓名识别分析器
 */
public class SimpleChineseNameAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
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

        // 中文姓名识别器
        SimpleChineseNameAnalyzer analyzer = new SimpleChineseNameAnalyzer(
                nameDic, notNameDic, leftVergeDic, rightVergeDic);

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
