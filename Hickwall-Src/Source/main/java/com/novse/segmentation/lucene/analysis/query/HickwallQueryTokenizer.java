/*
 * @作者:Hades , 创建日期:May 31, 2007
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.lucene.analysis.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

import com.novse.segmentation.core.SegmentProcessor;

/**
 * @author Mac Kwan 用于查询的中文分词器
 */
public class HickwallQueryTokenizer extends Tokenizer
{
    /**
     * 存放Token序列的列表
     */
    private List<Token> list = null;

    /**
     * 切分结果游标的位置
     */
    private int listIndex = 0;

    /**
     * 分词构件
     */
    private SegmentProcessor processor = null;

    /**
     * 以分词构建名称beanName、一个阅读器Reader为初始化参数的构造函数
     * 
     * @param beanName
     *            分词构建名称
     * @param reader
     *            阅读器
     */
    public HickwallQueryTokenizer(SegmentProcessor processor, Reader reader)
    {
        super(reader);
        this.processor = processor;
        try
        {
            list = this.process();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * （非 Javadoc）
     * 
     * @see org.apache.lucene.analysis.TokenStream#next()
     */
    @Override
    public Token next() throws IOException
    {
        // 判断游标位置是否超界
        if (this.listIndex >= this.list.size())
            return null;
        else
            return list.get(listIndex++);
    }

    /**
     * 分词处理
     * 
     * @throws IOException
     */
    private List<Token> process() throws IOException
    {
        // 初始化结果序列
        List<Token> tokenList = new ArrayList<Token>();
        // 字符游标位置
        int posIndex = 0;

        // 初始化输入输出
        BufferedReader in = new BufferedReader(this.input);
        // 读入数据
        String line = null;
        while ((line = in.readLine()) != null)
        {
            // 执行分词处理
            List<String> strList = this.processor.textProcess((line + System
                    .getProperties().getProperty("line.separator"))
                    .toLowerCase());
            // 遍历分词结果生成Token序列
            for (int i = 0; i < strList.size(); i++)
            {
                // 获取短语
                String word = strList.get(i);
                // 生成Token
                Token token = new Token(word, posIndex, posIndex
                        + word.length());
                // 修改字符游标位置
                posIndex += word.length();
                tokenList.add(token);
            }

        }
        // 关闭输入流
        this.close();

        // 返回Token序列结果
        return tokenList;
    }

}
