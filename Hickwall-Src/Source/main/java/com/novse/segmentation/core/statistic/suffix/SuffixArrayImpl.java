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
 * @author Mac Kwan �����ú�׺��������ĳ�����
 */
public abstract class SuffixArrayImpl implements SuffixArray
{
    /**
     * String���ͳ�Ա
     */
    private String src = null;

    /**
     * Ĭ�Ϲ��캯��
     */
    public SuffixArrayImpl()
    {

    }

    /**
     * ��String���Ͳ����Ĺ��캯��
     * 
     * @param str
     *            ��Ҫ�����ĺ�׺������ַ���
     */
    public SuffixArrayImpl(String str)
    {
        this.src = str;
    }

    /**
     * ��src��Ա��Ӧ��׺������������׺���ǰ׺���ȵ�����
     * 
     * @return src��Ա��Ӧ��׺������������׺���ǰ׺���ȵ�����
     */
    public int[] getLCP()
    {
        return this.getLCP(this.src.toLowerCase());
    }

    /*
     * ���� Javadoc��
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArrayImpl#getLCP(java.lang.String)
     */
    abstract public int[] getLCP(String str);

    /**
     * @return ���� src��
     */
    public String getSrc()
    {
        return src;
    }

    /**
     * ���Աsrc��׺����
     * 
     * @return ��Աsrc��Ӧ�ĺ�׺����
     */
    public int[] getSuffixArray()
    {
        return this.getSuffixArray(this.src.toLowerCase());
    }

    /*
     * ���� Javadoc��
     * 
     * @see edu.stu.cn.segment.statistic.suffix.SuffixArrayImpl#getSuffixArray(java.lang.String)
     */
    abstract public int[] getSuffixArray(String str);

    /**
     * ���ַ���v��w���ǰ׺����
     * 
     * @param v
     *            �ַ���v
     * @param w
     *            �ַ���w
     * @return �ǰ׺����
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
     *            Ҫ���õ� src��
     */
    public void setSrc(String src)
    {
        this.src = src;
    }

}
