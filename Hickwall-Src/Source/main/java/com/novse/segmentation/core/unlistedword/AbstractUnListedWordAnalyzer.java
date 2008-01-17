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
 * @author Mac Kwan δ��¼��ʶ�����ĳ���ʵ����
 */
public abstract class AbstractUnListedWordAnalyzer implements
        UnListedWordAnalyzer
{
    /**
     * �ָ����ַ���
     */
    protected String seperator = null;

    /**
     * �������ԭ�ִʽ��srcList����δ��¼��ʶ��
     * 
     * @param srcList
     *            ԭ�ִʽ��
     * @return δ��¼��ʶ�����ķִʽ��
     */
    abstract public List<String> identify(List<String> srcList);

    /**
     * ��ʼ���ָ����ķ���
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveStringWithNumberAndChar();
    }

}
