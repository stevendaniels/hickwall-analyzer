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

/**
 * @author Mac Kwan 计算获得后缀数组操作的抽象类
 */
public abstract class SuffixArrayImpl implements SuffixArray
{
    /**
     * String类型成员
     */
    private String src = null;

    /**
     * 默认构造函数
     */
    public SuffixArrayImpl()
    {

    }

    /**
     * 带String类型参数的构造函数
     * 
     * @param str
     *            需要求它的后缀数组的字符串
     */
    public SuffixArrayImpl(String str)
    {
        this.src = str;
    }

    /**
     * 求src成员相应后缀数组相邻两后缀的最长前缀长度的数组
     * 
     * @return src成员相应后缀数组相邻两后缀的最长前缀长度的数组
     */
    public int[] getLCP()
    {
        return this.getLCP(this.src.toLowerCase());
    }

    /*
     * （非 Javadoc）
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArrayImpl#getLCP(java.lang.String)
     */
    abstract public int[] getLCP(String str);

    /**
     * @return 返回 src。
     */
    public String getSrc()
    {
        return src;
    }

    /**
     * 求成员src后缀数组
     * 
     * @return 成员src对应的后缀数组
     */
    public int[] getSuffixArray()
    {
        return this.getSuffixArray(this.src.toLowerCase());
    }

    /*
     * （非 Javadoc）
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArrayImpl#getSuffixArray(java.lang.String)
     */
    abstract public int[] getSuffixArray(String str);

    /**
     * 求字符串v与w间最长前缀长度
     * 
     * @param v
     *            字符串v
     * @param w
     *            字符串w
     * @return 最长前缀长度
     */
    protected int retrieveLCP(String v, String w)
    {
        int result = 0;
        while (result < v.length() && result < v.length())
        {
            if (v.charAt(result) == w.charAt(result))
                result++;
            else
                break;
        }
        return result;
    }

    /**
     * @param src
     *            要设置的 src。
     */
    public void setSrc(String src)
    {
        this.src = src;
    }

}
