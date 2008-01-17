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
package com.novse.segmentation.core.unlistedword;

import java.util.List;

import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan 未登录词识别器的抽象实现类
 */
public abstract class AbstractUnListedWordAnalyzer implements
        UnListedWordAnalyzer
{
    /**
     * 分隔符字符串
     */
    protected String seperator = null;

    /**
     * 对输入的原分词结果srcList进行未登录词识别
     * 
     * @param srcList
     *            原分词结果
     * @return 未登录词识别处理后的分词结果
     */
    abstract public List<String> identify(List<String> srcList);

    /**
     * 初始化分隔符的方法
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveStringWithNumberAndChar();
    }

}
