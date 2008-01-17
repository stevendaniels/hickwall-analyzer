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
package com.novse.segmentation.core.statistic.basic;

import java.util.ArrayList;
import java.util.List;

import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher.WordInfo;

/**
 * @author Mac Kwan 单阀值的前缀后缀处理器
 */
public class SetpointPostProcessor extends BasicStatisticPostProcessor
{
    /**
     * 用户指定的阀值
     */
    private double setpoint = 0.55;

    /**
     * 前缀后缀处理
     * 
     * @param wordList
     *            待处理的高频字串集合
     * @return 处理后的高频字串集合
     * @throws Exception
     */
    @Override
    public List<WordInfo> postProcess(List<WordInfo> wordList) throws Exception
    {
        if (wordList == null)
            throw new Exception("待处理高频字串集合wordList为null");
        // 创建废置数组
        ArrayList<WordInfo> remove = new ArrayList<WordInfo>();

        // 循环遍历高频字串集合
        for (int i = 0; i < wordList.size(); i++)
        {
            // 去除“的”“了”“地”“着”开头的词汇
            if (wordList.get(i).word.indexOf("的") == 0
                    || wordList.get(i).word.indexOf("了") == 0
                    || wordList.get(i).word.indexOf("地") == 0
                    || wordList.get(i).word.indexOf("着") == 0)
            {
                remove.add(wordList.get(i));
                continue;
            }
            for (int j = i + 1; j < wordList.size(); j++)
            {
                // 判断wordList[i]是否为wordList[j]的前缀
                int firstIndex = wordList.get(j).word
                        .indexOf(wordList.get(i).word);
                // 判断wordList[i]是否为wordList[j]的后缀
                int lastIndex = wordList.get(j).word.lastIndexOf(wordList
                        .get(i).word);
                if ((firstIndex >= 0 || lastIndex >= 0)
                        && wordList.get(i).frequence == wordList.get(j).frequence)
                {
                    remove.add(wordList.get(i));
                }
                else if (firstIndex >= 0 || lastIndex >= 0)
                {
                    double t = (double) wordList.get(i).frequence
                            / (double) wordList.get(j).frequence;
                    // wordList[i]是wordList[j]的伪前缀或者伪后缀
                    if (t >= setpoint)
                    {
                        wordList.get(j).value = wordList.get(j).value + 1;
                        wordList.get(i).value = wordList.get(i).value - 1;
                    }
                }
            }
        }

        // 移除不符合条件的高频字串
        wordList.removeAll(remove);
        // 清空废置数组
        remove = null;

        // 返回结果
        return wordList;
    }

    /**
     * @param setpoint
     *            要设置的 setpoint。
     */
    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
    }

}
