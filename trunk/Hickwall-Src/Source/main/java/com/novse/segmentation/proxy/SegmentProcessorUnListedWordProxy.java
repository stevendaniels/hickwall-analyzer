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
package com.novse.segmentation.proxy;

import java.util.List;

import com.novse.segmentation.core.AbstractSegmentProcessor;
import com.novse.segmentation.core.SegmentProcessor;
import com.novse.segmentation.core.unlistedword.UnListedWordAnalyzer;

/**
 * @author Mac Kwan 使用未登录词识别分析器进行修正的基于词典匹配的中文分词代理
 */
public class SegmentProcessorUnListedWordProxy extends AbstractSegmentProcessor
{
    private SegmentProcessor target = null;

    private UnListedWordAnalyzer analyzer = null;

    /**
     * 默认构造函数
     * 
     * @param target
     *            被代理的对象
     * @param analyzer
     *            未登录词识别分析器
     */
    public SegmentProcessorUnListedWordProxy(SegmentProcessor target,
            UnListedWordAnalyzer analyzer)
    {
        this.target = target;
        this.analyzer = analyzer;
    }

    @Override
    public List<String> textProcess(String text)
    {
        // 调用被代理对象的分词
        List<String> tempResult = this.target.textProcess(text);

        // 未登录词分析器修正分词结果
        List<String> result = this.analyzer.identify(tempResult);

        return result;
    }

    public UnListedWordAnalyzer getAnalyzer()
    {
        return analyzer;
    }

    public void setAnalyzer(UnListedWordAnalyzer analyzer)
    {
        this.analyzer = analyzer;
    }

    public SegmentProcessor getTarget()
    {
        return target;
    }

    public void setTarget(SegmentProcessor target)
    {
        this.target = target;
    }

}
