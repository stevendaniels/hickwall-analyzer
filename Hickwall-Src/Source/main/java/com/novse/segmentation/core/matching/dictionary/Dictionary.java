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
package com.novse.segmentation.core.matching.dictionary;

import java.io.PrintStream;
import java.util.List;

/**
 * @author Mac Kwan 词典操作接口
 */
public interface Dictionary
{

    /**
     * 批量删除词典中的词汇
     * 
     * @param wordList
     *            待删除的词汇列表
     */
    public void deleteWord(List<String> wordList);

    /**
     * 删除词典中的词word
     * 
     * @param word
     *            待删除的词汇
     */
    public void deleteWord(String word);

    /**
     * 将词汇批量插入到词典文件中
     * 
     * @param wordList
     *            待插入词汇列表
     */
    public void insertWord(List<String> wordList);

    /**
     * 将词汇word插入到词典文件中
     * 
     * @param word
     *            待插入的词汇
     */
    public void insertWord(String word);

    /**
     * 词典是否为空
     * 
     * @return 词典是否为空
     */
    public boolean isEmpty();

    /**
     * 判断输入的字符串是否在词典中
     * 
     * @param word
     *            待判断字符串
     * @return 判断结果
     */
    public boolean match(String word);

    /**
     * 输出已载入内存中所有词汇
     * 
     * @param out
     *            输出流
     */
    public void print(PrintStream out);
}
