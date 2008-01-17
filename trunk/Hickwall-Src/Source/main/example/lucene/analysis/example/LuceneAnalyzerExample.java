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
 * @author Mac Kwan Lucene������
 */
public class LuceneAnalyzerExample
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        // ���ķִʲ���
        // �ı���Դ
        Resource dicResource = new FileResource("Dic/Txt/SmallDic.txt");

        // �ʵ�ӿ�
        Dictionary dic = DictionaryUtils.createSimpleDictionary(dicResource);

        // ����ƥ��ķִʴ�����
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic);
        // SegmentProcessor processor = new
        // ReverseMaxMatchSegmentProcessor(dic);
        // end of ���ķִʲ���

        Analyzer analyzer = new HickwallQueryAnalyzer(processor);
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
