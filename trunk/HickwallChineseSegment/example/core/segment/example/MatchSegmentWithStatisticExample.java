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
 * @author Mac Kwan ��ͳ��ʶ��ʻ����ϵ����ķִ�
 */
public class MatchSegmentWithStatisticExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // �ı���Դ
        Resource dicResource = new FileResource("Dic/Txt/SmallDic.txt");

        // �ʵ�ӿ�
        Dictionary dic = DictionaryUtils.createSimpleDictionary(dicResource);

        // ͳ��ʶ��ʻ�
        StringFetcher suffixArray = new BasedSuffixArrayStringFetcher(0.8, 0.2);

        // ����ƥ��ķִʴ�����
        SegmentProcessor processor = new MaxMatchSegmentProcessor(dic,
                suffixArray);
        // SegmentProcessor processor = new
        // ReverseMaxMatchSegmentProcessor(dic);

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

    }

}
