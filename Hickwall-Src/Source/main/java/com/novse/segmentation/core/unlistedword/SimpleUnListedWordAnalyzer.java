/*
 * @作者:Hades , 创建日期:2007-4-10
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.unlistedword;

import java.util.List;

import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.CharFrequenceDictionary.CharFrequenceInfo;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * @author Mac Kwan 简单的未登录词识别器实现类
 */
public class SimpleUnListedWordAnalyzer extends AbstractUnListedWordAnalyzer
{
    /**
     * 字频统计字典
     */
    private CharFrequenceDictionary dic = null;

    /**
     * 单字成为词汇首字的概率阀值（首字概率大于单字成词概率与非首字概率的和）
     */
    private final double FIRSTCH_SETPOINT = 0.5;

    /**
     * 单字成词的概率阀值
     */
    private final double SINGLE_SETPOINT = 5e-5;

    /**
     * 默认构造函数
     * 
     * @param dic
     *            字频统计字典实例
     */
    public SimpleUnListedWordAnalyzer(CharFrequenceDictionary dic)
    {
        this.dic = dic;
        this.initSeperator();
    }

    /**
     * 自定义分隔符的构造函数
     * 
     * @param dic
     *            字频统计字典实例
     * 
     * @param seperator
     *            自定义分隔符
     */
    public SimpleUnListedWordAnalyzer(CharFrequenceDictionary dic,
            String seperator)
    {
        this.dic = dic;
        this.seperator = seperator;
    }

    /**
     * 计算汉字ch成为词汇首字的概率
     * 
     * @param info
     *            汉字ch的字频统计信息
     * @return 汉字ch成为词汇首字的概率
     */
    private double getFirstCharValue(CharFrequenceInfo info)
    {
        double value = (double) info.firstCharFrequence / info.charFrequence;
        // System.out.println("First Char Value:" + value);
        return value;
    }

    /**
     * 计算单字ch成词概率
     * 
     * @param info
     *            单字ch的字频统计信息
     * @return 单字成词概率
     */
    private double getSingleValue(CharFrequenceInfo info)
    {
        double value = ((double) info.singleCharFrequnece / info.charFrequence)
                * ((double) info.singleCharFrequnece / this.dic.getWordCount());
        // System.out.println("Single Value" + value);
        return value;
    }

    /**
     * 对输入的原分词结果srcList进行未登录词识别
     * 
     * @param srcList
     *            原分词结果
     * @return 未登录词识别处理后的分词结果
     */
    public List<String> identify(List<String> srcList)
    {
        // 设置开始、结束游标
        int startPos = 0, endPos = 0;
        // 遍历寻找分词结果中的单字碎片
        while (startPos < srcList.size())
        {
            // 寻找字长为1且为非分割字符的单字
            while (startPos < srcList.size()
                    && (srcList.get(startPos).length() > 1 || this.seperator
                            .indexOf(srcList.get(startPos)) >= 0))
                startPos++;
            endPos = startPos;
            // 寻找字长为1且为非分割字符的单字
            while (endPos < srcList.size()
                    && (srcList.get(endPos).length() == 1 && this.seperator
                            .indexOf(srcList.get(endPos)) < 0))
                endPos++;

            // hades print
            String str = "";
            for (int i = startPos; i < endPos; i++)
                str += srcList.get(i);

            // 遍历单字碎片区间
            while (startPos < endPos)
            {
                // 获取单字碎片ch
                String ch = srcList.get(startPos);
                // 获取单字ch的频率统计信息
                CharFrequenceInfo info = this.dic.getInfo(ch);

                // 若无单字ch的频率统计信息，则证明单字ch是罕见字，判断它为单字词
                if (info == null)
                    startPos++;
                // 否则
                else
                {
                    // 判断单字成词概率、词汇首字概率满足条件
                    if (this.getSingleValue(info) > SINGLE_SETPOINT
                            || this.getFirstCharValue(info) < FIRSTCH_SETPOINT)
                        startPos++;
                    else
                    {
                        // 创始化字符缓冲
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(ch);

                        // 获取下一个单字以及其相应信息
                        int nextPos = startPos + 1;
                        ch = srcList.get(nextPos);
                        info = this.dic.getInfo(ch);

                        // 合并不能成为单字词的单字
                        while (nextPos <= endPos
                                && StopWordMaker
                                        .retreiveStringWithNumberAndChar()
                                        .indexOf(ch) < 0
                                && info != null
                                && this.getSingleValue(info) <= SINGLE_SETPOINT
                                && this.getFirstCharValue(info) < FIRSTCH_SETPOINT)
                        {
                            buffer.append(ch);
                            // 获取下一个单字以及其相应信息
                            nextPos++;
                            ch = srcList.get(nextPos);
                            info = this.dic.getInfo(ch);
                        }
                        // end of while

                        // 合并后字符串非单字词时
                        if (buffer.length() > 1)
                        {
                            // hades unlisted word print
                            System.out.println("碎片：" + str);
                            System.out.println("结果：" + buffer);
                            System.out.println();

                            // 替换
                            srcList.set(startPos, buffer.toString());
                            // 删除
                            for (int i = 0; i < buffer.length() - 1; i++, endPos--)
                                srcList.remove(startPos + 1);
                        }
                        // 重设开始游标
                        startPos += 1;
                        // end of if(buffer.length()>1)
                    }
                    // end of if (singleValue > SINGLE_SETPOINT || firstChValue
                    // < FIRSTCH_SETPOINT)
                }
                // end of if(info==null)
            }
        }
        return srcList;
    }
}
