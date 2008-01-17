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
package spring.exmaple;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.SegmentProcessor;

/**
 * @author Mac Kwan 基于Spring框架地使用中文分词
 */
public class SpringExample
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "hickwall-config.xml");

        // 基于匹配的分词处理器
        SegmentProcessor processor = (SegmentProcessor) context
                .getBean("reverseMaxMatchSegmentProxy");
        // SegmentProcessor processor = (SegmentProcessor)
        // context.getBean("maxMatchSegmentProxy");

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
        System.out.println();
        System.out.println();

        Analyzer analyzer = (Analyzer) context.getBean("hickwallQueryAnalyzer");
        // Analyzer analyzer = (Analyzer) context.getBean("hickwallQAnalyzer");
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
