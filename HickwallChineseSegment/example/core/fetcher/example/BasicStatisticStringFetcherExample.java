/* 
 * Copyright hickwall 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
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
