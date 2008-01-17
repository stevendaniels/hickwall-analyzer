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

import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;
import com.novse.segmentation.util.DictionaryUtils;

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
        // 文本资源
        Resource dicResource = new FileResource("Dic/Txt/FirstName.txt");

        // 未登录词识别

        // 中文姓氏资源
        Resource firstNameDicResource = new FileResource(
                "Dic/Txt/FirstName.txt");
        // 中文姓名资源
        Resource chineseNameDicResource = new FileResource("Dic/Txt/Name.txt");
        // 伪中文姓名资源
        Resource notChineseNameDicResource = new FileResource(
                "Dic/Txt/UnName.txt");

        // 中文姓名词典
        ChineseNameDictionary nameDic = DictionaryUtils
                .createChineseNameDictionary(firstNameDicResource,
                        chineseNameDicResource);

        // 姓氏开头非中文姓名的词典
        NotChineseNameDictionary notNameDic = DictionaryUtils
                .createNotChineseNameDictionary(firstNameDicResource,
                        notChineseNameDicResource);

        // 左边界词词典资源
        dicResource = new FileResource("Dic/Txt/LeftVerge.txt");
        // 左边界词词典
        Dictionary leftVergeDic = DictionaryUtils
                .createHashDictionary(dicResource);

        // 右边界词词典资源
        dicResource = new FileResource("Dic/Txt/RightVerge.txt");
        // 右边界词词典
        Dictionary rightVergeDic = DictionaryUtils
                .createHashDictionary(dicResource);

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
