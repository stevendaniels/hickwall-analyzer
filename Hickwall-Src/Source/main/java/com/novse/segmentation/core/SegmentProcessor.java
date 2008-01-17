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
 * @author Mac Kwan 基于词典匹配的中文分词接口
 */
public interface SegmentProcessor
{

    /**
     * 对srcFile文件进行分词，把结果保存为到tagFile文件中
     * 
     * @param srcFile
     *            待分词的文本文件
     * @param tagFile
     *            分词结果保存目的文件
     */
    public void fileProcessor(String srcFile, String tagFile);

    /**
     * @return 返回 dic。
     */
    public Dictionary getDic();

    /**
     * @return 返回 fetcher。
     */
    public StringFetcher getFetcher();

    /**
     * @param dic
     *            要设置的 dic。
     */
    public void setDic(Dictionary dic);

    /**
     * @param fetcher
     *            要设置的 fetcher。
     */
    public void setFetcher(StringFetcher fetcher);

    /**
     * 对text文本进行分词，把结果保存为字符串链表
     * 
     * @param text
     *            待分词的文本
     * @return 分词结果
     */
    public List<String> textProcess(String text);

}
