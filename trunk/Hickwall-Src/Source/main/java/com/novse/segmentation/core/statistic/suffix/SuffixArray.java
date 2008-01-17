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
 * @author Mac Kwan 计算获得后缀数组操作的接口
 */
public interface SuffixArray
{
    /**
     * 求字符串str后缀数组
     * 
     * @param str
     *            需要求它后缀数组的字符串
     * @return str对应的后缀数组
     */
    public int[] getSuffixArray(String str);

    /**
     * 求存放str对应后缀数组相邻两后缀的最长前缀长度的数组
     * 
     * @param str
     *            需要求它后缀数组的字符串
     * @return 存放str对应后缀数组相邻两后缀的最长前缀长度的数组
     */
    public int[] getLCP(String str);
}
