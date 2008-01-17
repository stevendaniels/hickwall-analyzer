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
package com.novse.segmentation.core;

import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;

/**
 * 
 * @author Mac Kwan ���ڴʵ�ƥ������ķִʽӿ�
 */
public interface SegmentProcessor
{

    /**
     * ��srcFile�ļ����зִʣ��ѽ������Ϊ��tagFile�ļ���
     * 
     * @param srcFile
     *            ���ִʵ��ı��ļ�
     * @param tagFile
     *            �ִʽ������Ŀ���ļ�
     */
    public void fileProcessor(String srcFile, String tagFile);

    /**
     * @return ���� dic��
     */
    public Dictionary getDic();

    /**
     * @return ���� fetcher��
     */
    public StringFetcher getFetcher();

    /**
     * @param dic
     *            Ҫ���õ� dic��
     */
    public void setDic(Dictionary dic);

    /**
     * @param fetcher
     *            Ҫ���õ� fetcher��
     */
    public void setFetcher(StringFetcher fetcher);

    /**
     * ��text�ı����зִʣ��ѽ������Ϊ�ַ�������
     * 
     * @param text
     *            ���ִʵ��ı�
     * @return �ִʽ��
     */
    public List<String> textProcess(String text);

}
