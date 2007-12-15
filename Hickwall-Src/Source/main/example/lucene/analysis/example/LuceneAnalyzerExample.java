/*
 * @作者:Mac Kwan , 创建日期:2007-12-8
 *
 * 汕头大学03计算机本科
 * 
 */
package lucene.analysis.example;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.SimpleDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;

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
        // 词典接口
        Dictionary dic = new SimpleDictionary();

        // 从文本文件中载入词库
        dic.loadDictionary("Dic/Txt/SmallDic.txt");

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
