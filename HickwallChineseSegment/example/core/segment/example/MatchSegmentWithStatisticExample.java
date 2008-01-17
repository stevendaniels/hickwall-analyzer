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
package core.segment.example;

import java.util.List;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.core.statistic.fetcher.BasedSuffixArrayStringFetcher;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;
import com.novse.segmentation.util.DictionaryUtils;

/**
 * @author Mac Kwan 与统计识别词汇相结合的中文分词
 */
public class MatchSegmentWithStatisticExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 文本资源
        Resource dicResource = new FileResource("Dic/Txt/SmallDic.txt");

        // 词典接口
        Dictionary dic = DictionaryUtils.createSimpleDictionary(dicResource);

        // 统计识别词汇
        StringFetcher suffixArray = new BasedSuffixArrayStringFetcher(0.8, 0.2);

        // 基于匹配的分词处理器
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic,
                suffixArray);
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
