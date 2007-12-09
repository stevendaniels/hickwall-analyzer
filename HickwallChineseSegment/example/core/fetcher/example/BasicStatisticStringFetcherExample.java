/*
 * @作者:Mac Kwan , 创建日期:2007-12-2
 *
 * 汕头大学03计算机本科
 * 
 */
package core.fetcher.example;

import java.util.List;

import com.novse.segmentation.core.statistic.basic.DoubleSetpointPostProcessor;
import com.novse.segmentation.core.statistic.basic.SetpointPostProcessor;
import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher;

/**
 * @author Mac Kwan 基于基本统计的高频字符串抽取器
 */
public class BasicStatisticStringFetcherExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SetpointPostProcessor setPointPostProcessor = new SetpointPostProcessor();
        setPointPostProcessor.setSetpoint(0.55);

        DoubleSetpointPostProcessor doubleSetPointPostProcessor = new DoubleSetpointPostProcessor();
        doubleSetPointPostProcessor.setMaxConfidence(0.8);
        doubleSetPointPostProcessor.setMinConfidence(0.2);

        List<String> result = null;

        BasicStatisticStringFetcher basic = new BasicStatisticStringFetcher(
                doubleSetPointPostProcessor);
        // BasicStatisticStringFetcher basic=new
        // BasicStatisticStringFetcher(setPointPostProcessor);
        result = basic.fileFetch("news.txt");

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        result = basic
                .textFetch("11月29日，原北京师范大学校长、原汕头大学数学研究所所长、中国科学院院士王梓坤先生莅临我校，他通过自己做研究生和指导研究生的亲身经历，在学术交流中心为广大导师和研究生做了题为《研究生教学四十年》的报告。");
        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
    }

}
