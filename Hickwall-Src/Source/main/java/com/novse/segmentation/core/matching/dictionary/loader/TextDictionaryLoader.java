/*
 * @作者:Mac Kwan , 创建日期:2007-12-28
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.matching.dictionary.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.novse.segmentation.core.io.Resource;

/**
 * @author Mac Kwan TXT文本形式的词典资源加载器
 */
public class TextDictionaryLoader extends AbstractDictionaryLoader
{

    /**
     * 从词典资源中抽取获得词汇列表
     * 
     * @param dicResource
     *            词典资源
     * @return 词汇列表
     */
    @Override
    protected List<String> extract(Resource dicResource)
    {
        // 初始化结果集合
        List<String> result = new ArrayList<String>();

        try
        {
            // 初始化阅读器
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    dicResource.getInputStream()));
            // 载入文本
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                result.add(line.trim());
            }
            // 关闭输入流
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }
}
