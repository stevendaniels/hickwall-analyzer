/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
 * 
 */
package core.fetcher.example;

import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasedSuffixArrayStringFetcher;

/**
 * @author Mac Kwan 基于后缀数组的高频字符串抽取器
 */
public class BasedSuffixArrayStringFetcherExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        BasedSuffixArrayStringFetcher suffixArray = new BasedSuffixArrayStringFetcher();
        suffixArray.setMaxConfidence(0.8);
        suffixArray.setMinConfidence(0.2);

        List<String> result = null;

        result = suffixArray.fileFetch("news.txt");

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        result = suffixArray
                .textFetch("11月29日，原北京师范大学校长、原汕头大学数学研究所所长、中国科学院院士王梓坤先生莅临我校，他通过自己做研究生和指导研究生的亲身经历，在学术交流中心为广大导师和研究生做了题为《研究生教学四十年》的报告。");
        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
    }

}
