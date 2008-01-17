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
package com.novse.segmentation.core.matching.dictionary.loader;

import java.util.List;

import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan 词典加载器抽象实现
 */
public abstract class AbstractDictionaryLoader implements DictionaryLoader
{
    /**
     * 从词典资源中抽取获得词汇列表
     * 
     * @param dicResource
     *            词典资源
     * @return 词汇列表
     */
    abstract protected List<String> extract(Resource dicResource);

    /**
     * 为指定的词典实例载入外部词典资源
     * 
     * @param dic
     *            词典实例
     * @param dicResource
     *            词典资源
     */
    public void load(Dictionary dic, Resource dicResource)
    {
        // 从资源中抽取词汇
        List<String> extractList = this.extract(dicResource);
        // 批量导入到词典中
        dic.insertWord(extractList);
    }

}
