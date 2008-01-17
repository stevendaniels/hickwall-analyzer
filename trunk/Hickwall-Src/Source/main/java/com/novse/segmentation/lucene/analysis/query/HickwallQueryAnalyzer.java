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
package com.novse.segmentation.lucene.analysis.query;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan ���ڲ�ѯ��Lucene�ִʷ�����
 */
public class HickwallQueryAnalyzer extends Analyzer
{
    /**
     * һЩ�������������Ĵʻ�
     */
    public final static String[] STOP_WORDS =
    { "a", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
            "into", "is", "it", "no", "not", "of", "on", "or", "s", "such",
            "t", "that", "the", "their", "then", "there", "these", "they",
            "this", "to", "was", "will", "with", "", "www" };

    /**
     * �ִʹ���
     */
    private SegmentProcessor processor = null;

    /**
     * �ָ����б�
     */
    private Set stopTable;

    /**
     * ʹ��Ĭ�Ϸָ����Ĺ��캯��
     * 
     * @param processor
     *            ��Ҫʹ�÷ִʹ���
     */
    public HickwallQueryAnalyzer(SegmentProcessor processor)
    {
        this.processor = processor;
        this.stopTable = StopWordMaker.retreiveSet();
    }

    /**
     * ʹ��ָ���ָ����Ĺ��캯��
     * 
     * @param processor
     *            ��Ҫʹ�÷ִʹ���
     * @param stopWords
     *            ָ���ķָ���
     */
    public HickwallQueryAnalyzer(SegmentProcessor processor, String[] stopWords)
    {
        this.processor = processor;
        this.stopTable = StopFilter.makeStopSet(stopWords);
    }

    /**
     * @return ���� processor��
     */
    public SegmentProcessor getProcessor()
    {
        return processor;
    }

    /**
     * @param processor
     *            Ҫ���õ� processor��
     */
    public void setProcessor(SegmentProcessor processor)
    {
        this.processor = processor;
    }

    /*
     * ���� Javadoc��
     * 
     * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String,
     *      java.io.Reader)
     */
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader)
    {
        return new StopFilter(
                new HickwallQueryTokenizer(this.processor, reader),
                this.stopTable);
    }

}
