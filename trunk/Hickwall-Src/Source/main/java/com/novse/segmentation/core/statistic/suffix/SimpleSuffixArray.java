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
 * @author Mac Kwan 简单实现的求后缀数组操作类
 */
public class SimpleSuffixArray extends SuffixArrayImpl
{
    /**
     * 默认构造函数
     */
    public SimpleSuffixArray()
    {
        super();
    }

    /**
     * 带String类型参数的构造函数
     * 
     * @param str
     *            需要求它的后缀数组的字符串
     */
    public SimpleSuffixArray(String str)
    {
        super(str);
    }

    /*
     * （非 Javadoc）
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArray#getLCP(java.lang.String)
     */
    @Override
    public int[] getLCP(String str)
    {
        if (str == null)
            return null;

        // 求后缀数组
        int[] suffixArray = this.getSuffixArray(str);
        // 求最长前缀长度数组
        int[] lcp = new int[suffixArray.length];
        for (int i = 0; i < suffixArray.length; i++)
        {
            // i=0时LCP必为0
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
     * （非 Javadoc）
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArray#getSuffixArray(java.lang.String)
     */
    @Override
    public int[] getSuffixArray(String str)
    {
        if (str == null)
            return null;

        // 初始化后缀数组
        String[] suffix = new String[str.length()];
        for (int i = 0; i < suffix.length; i++)
            suffix[i] = str.substring(i);

        // 对后缀数组排序
        Arrays.sort(suffix);

        // 求结果数组
        int[] result = new int[str.length()];
        for (int i = 0; i < suffix.length; i++)
        {
            result[i] = str.lastIndexOf(suffix[i]);
        }

        return result;
    }

}
