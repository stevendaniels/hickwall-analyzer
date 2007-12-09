/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
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
