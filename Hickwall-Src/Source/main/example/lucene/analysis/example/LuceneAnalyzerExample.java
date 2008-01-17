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
package lucene.analysis.example;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;
import com.novse.segmentation.util.DictionaryUtils;

/**
 * @author Mac Kwan Lucene分析器
 */
public class LuceneAnalyzerExample
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        // 中文分词部件
        // 文本资源
        Resource dicResource = new FileResource("Dic/Txt/SmallDic.txt");

        // 词典接口
        Dictionary dic = DictionaryUtils.createSimpleDictionary(dicResource);

        // 基于匹配的分词处理器
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic);
        // SegmentProcessor processor = new
        // ReverseMaxMatchSegmentProcessor(dic);
        // end of 中文分词部件

        Analyzer analyzer = new HickwallQueryAnalyzer(processor);
        // Analyzer analyzer = new HickwallIndexAnalyzer(processor);
        TokenStream tokenStream = analyzer
                .tokenStream(
                        "",
                        new StringReader(
                                "11月29日，原北京师范大学校长、原汕头大学数学研究所所长、中国科学院院士王梓坤先生莅临我校，他通过自己做研究生和指导研究生的亲身经历，在学术交流中心为广大导师和研究生做了题为《研究生教学四十年》的报告。"));
        Token token = null;
        do
        {
            token = tokenStream.next();
            if (token != null)
                System.out.println(token);
        }
        while (token != null);
    }

}
