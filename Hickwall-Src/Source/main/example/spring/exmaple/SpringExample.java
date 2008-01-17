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
 * @author Mac Kwan ����Spring��ܵ�ʹ�����ķִ�
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

        // ����ƥ��ķִʴ�����
        SegmentProcessor processor = (SegmentProcessor) context
                .getBean("reverseMaxMatchSegmentProxy");
        // SegmentProcessor processor = (SegmentProcessor)
        // context.getBean("maxMatchSegmentProxy");

        // ���ļ����зִ�
        processor.fileProcessor("news.txt", "result.txt");

        // ���ַ������зִ�
        List<String> result = processor
                .textProcess("11��29�գ�ԭ����ʦ����ѧУ����ԭ��ͷ��ѧ��ѧ�о����������й���ѧԺԺʿ����������ݰ����У����ͨ���Լ����о�����ָ���о���������������ѧ����������Ϊ���ʦ���о���������Ϊ���о�����ѧ��ʮ�꡷�ı��档");

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
                                "11��29�գ�ԭ����ʦ����ѧУ����ԭ��ͷ��ѧ��ѧ�о����������й���ѧԺԺʿ����������ݰ����У����ͨ���Լ����о�����ָ���о���������������ѧ����������Ϊ���ʦ���о���������Ϊ���о�����ѧ��ʮ�꡷�ı��档"));
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
