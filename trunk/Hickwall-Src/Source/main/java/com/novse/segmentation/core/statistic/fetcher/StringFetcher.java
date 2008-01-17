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
package com.novse.segmentation.core.statistic.fetcher;

import java.util.List;

/**
 * @author Mac Kwan 词汇抽取器接口
 */
public interface StringFetcher
{
    /**
     * 从待处理文件srcFile中抽取词汇
     * 
     * @param srcFile
     *            待处理文件
     * @return 抽取所得词汇
     */
    public List<String> fileFetch(String srcFile);

    /**
     * 从待处理字符串doc中抽取词汇
     * 
     * @param doc
     *            待处理字符串
     * @return 抽取所得词汇
     */
    public List<String> textFetch(String doc);
}
