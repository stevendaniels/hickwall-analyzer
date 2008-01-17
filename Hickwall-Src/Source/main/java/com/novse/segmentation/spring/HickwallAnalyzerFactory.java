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
package com.novse.segmentation.spring;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.lucene.analysis.index.HickwallIndexAnalyzer;
import com.novse.segmentation.lucene.analysis.query.HickwallQueryAnalyzer;

/**
 * @author Mac Kwan Lucene分词分析器工厂
 */
public class HickwallAnalyzerFactory
{
    /**
     * Lucene分词器工厂单键
     */
    private static HickwallAnalyzerFactory singleton = null;

    /**
     * 根据spring配置文档路径获取相应的Lucene分词器工厂实例
     * 
     * @param contextPath
     *            spring配置文档路径
     * @return Lucene分词器工厂实例
     */
    public static HickwallAnalyzerFactory getInstance(String contextPath)
    {
        if (singleton == null)
        {
            singleton = new HickwallAnalyzerFactory(contextPath);
        }
        return singleton;
    }

    /**
     * Spring控制器
     */
    private ApplicationContext context = null;

    /**
     * 构造函数
     * 
     * @param contextPath
     *            spring配置文档路径
     */
    private HickwallAnalyzerFactory(String contextPath)
    {
        context = new FileSystemXmlApplicationContext(contextPath);
    }

    /**
     * 返回建立索引用的分词器
     * 
     * @return 查询用的分词器
     */
    public Analyzer getIndexAnalyzer()
    {
        if (context == null)
            return null;
        else
            return (HickwallIndexAnalyzer) context
                    .getBean("hickwallIndexAnalyzer");
    }

    /**
     * 返回查询用的分词器
     * 
     * @return 查询用的分词器
     */
    public Analyzer getQueryAnalyzer()
    {
        if (context == null)
            return null;
        else
            return (HickwallQueryAnalyzer) context
                    .getBean("hickwallQueryAnalyzer");
    }

}
