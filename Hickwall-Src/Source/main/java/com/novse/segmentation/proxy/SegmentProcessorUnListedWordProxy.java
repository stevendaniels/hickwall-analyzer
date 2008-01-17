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
 * @author Mac Kwan ʹ��δ��¼��ʶ����������������Ļ��ڴʵ�ƥ������ķִʴ���
 */
public class SegmentProcessorUnListedWordProxy extends AbstractSegmentProcessor
{
    private SegmentProcessor target = null;

    private UnListedWordAnalyzer analyzer = null;

    /**
     * Ĭ�Ϲ��캯��
     * 
     * @param target
     *            ������Ķ���
     * @param analyzer
     *            δ��¼��ʶ�������
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
        // ���ñ��������ķִ�
        List<String> tempResult = this.target.textProcess(text);

        // δ��¼�ʷ����������ִʽ��
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
