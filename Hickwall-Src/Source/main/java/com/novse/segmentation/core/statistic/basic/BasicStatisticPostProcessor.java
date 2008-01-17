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
package com.novse.segmentation.core.statistic.basic;

import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan ����ͳ�Ƶĸ�Ƶ�ʻ��ȡ���ĺ��ڴ�����
 */
public class BasicStatisticPostProcessor
{
    /**
     * ǰ׺��׺����
     * 
     * @param wordList
     *            ������ĸ�Ƶ�ִ�����
     * @return �����ĸ�Ƶ�ִ�����
     * @throws Exception
     */
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("�������Ƶ�ִ�����wordListΪnull");
        return wordList;
    }
}
