/*
 * @作者:Mac Kwan , 创建日期:2007-12-25
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.dictionary.loader;

import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan 词典加载器接口
 */
public interface DictionaryLoader
{
    /**
     * 为指定的词典实例载入外部词典资源
     * 
     * @param dic
     *            词典实例
     * @param dicResource
     *            词典资源
     */
    public void load(Dictionary dic, Resource dicResource);
}
