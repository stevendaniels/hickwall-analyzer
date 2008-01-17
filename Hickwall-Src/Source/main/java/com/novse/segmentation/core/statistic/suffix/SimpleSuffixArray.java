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
package com.novse.segmentation.core.statistic.suffix;

import java.util.Arrays;

/**
 * @author Mac Kwan ��ʵ�ֵ����׺���������
 */
public class SimpleSuffixArray extends SuffixArrayImpl
{
    /**
     * Ĭ�Ϲ��캯��
     */
    public SimpleSuffixArray()
    {
        super();
    }

    /**
     * ��String���Ͳ����Ĺ��캯��
     * 
     * @param str
     *            ��Ҫ�����ĺ�׺������ַ���
     */
    public SimpleSuffixArray(String str)
    {
        super(str);
    }

    /*
     * ���� Javadoc��
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArray#getLCP(java.lang.String)
     */
    @Override
    public int[] getLCP(String str)
    {
        if (str == null)
            return null;

        // ���׺����
        int[] suffixArray = this.getSuffixArray(str);
        // ���ǰ׺��������
        int[] lcp = new int[suffixArray.length];
        for (int i = 0; i < suffixArray.length; i++)
        {
            // i=0ʱLCP��Ϊ0
            if (i == 0)
                lcp[i] = 0;
            else
            {
                lcp[i] = this.retrieveLCP(str.substring(suffixArray[i - 1]),
                        str.substring(suffixArray[i]));
            }
        }
        return lcp;
    }

    /*
     * ���� Javadoc��
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArray#getSuffixArray(java.lang.String)
     */
    @Override
    public int[] getSuffixArray(String str)
    {
        if (str == null)
            return null;

        // ��ʼ����׺����
        String[] suffix = new String[str.length()];
        for (int i = 0; i < suffix.length; i++)
            suffix[i] = str.substring(i);

        // �Ժ�׺��������
        Arrays.sort(suffix);

        // ��������
        int[] result = new int[str.length()];
        for (int i = 0; i < suffix.length; i++)
        {
            result[i] = str.lastIndexOf(suffix[i]);
        }

        return result;
    }

}
