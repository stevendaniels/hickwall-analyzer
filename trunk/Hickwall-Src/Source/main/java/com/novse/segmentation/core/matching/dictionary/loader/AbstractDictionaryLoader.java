/*
 * @作者:Mac Kwan , 创建日期:2007-12-28
 *
 * 汕头大学03计算机本科
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
