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
