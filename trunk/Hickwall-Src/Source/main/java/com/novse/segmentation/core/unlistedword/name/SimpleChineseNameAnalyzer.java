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
package com.novse.segmentation.core.unlistedword.name;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.unlistedword.AbstractUnListedWordAnalyzer;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary.ChineseNameCharInfo;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary.NotChineseNameCharInfo;

/**
 * @author Mac Kwan 简单的人名识别器实现类
 */
public class SimpleChineseNameAnalyzer extends AbstractUnListedWordAnalyzer
{
    /**
     * 左边界词
     */
    private Dictionary leftVergeDic = null;

    /**
     * 边界概率调整系数
     */
    private final double M = 10.0;

    /**
     * 概率调整系数
     */
    private final double N = 100.0;

    /**
     * 带统计信息的中文姓名词典
     */
    private ChineseNameDictionary nameDic = null;

    /**
     * 带统计信息的非中文姓名的词典
     */
    private NotChineseNameDictionary notNameDic = null;

    /**
     * 右边界词
     */
    private Dictionary rightVergeDic = null;

    /**
     * 判断为人名的阀值
     */
    private double setpoint = 1;

    /**
     * 将识别获得的词组放入临时词典中
     */
    private Map<String, String> tempNameDic = null;

    /**
     * @param nameDic
     *            带统计信息的中文姓名词典
     * @param notNameDic
     *            带统计信息的非中文姓名的词典
     */
    public SimpleChineseNameAnalyzer(ChineseNameDictionary nameDic,
            NotChineseNameDictionary notNameDic)
    {
        this.nameDic = nameDic;
        this.notNameDic = notNameDic;
        this.initSeperator();
    }

    /**
     * @param nameDic
     *            带统计信息的中文姓名词典
     * @param notNameDic
     *            带统计信息的非中文姓名的词典
     * @param leftVergeDic
     *            左边界词词典
     * @param rightVergeDic
     *            右边界词词典
     */
    public SimpleChineseNameAnalyzer(ChineseNameDictionary nameDic,
            NotChineseNameDictionary notNameDic, Dictionary leftVergeDic,
            Dictionary rightVergeDic)
    {
        this.nameDic = nameDic;
        this.notNameDic = notNameDic;
        this.leftVergeDic = leftVergeDic;
        this.rightVergeDic = rightVergeDic;
        this.initSeperator();
    }

    /**
     * @param nameDic
     *            带统计信息的中文姓名词典
     * @param notNameDic
     *            带统计信息的非中文姓名的词典
     * @param tempNameDic
     *            用于记录已识别的临时词典
     */
    public SimpleChineseNameAnalyzer(ChineseNameDictionary nameDic,
            NotChineseNameDictionary notNameDic, Map<String, String> tempNameDic)
    {
        this.nameDic = nameDic;
        this.notNameDic = notNameDic;
        this.tempNameDic = tempNameDic;
        this.initSeperator();
    }

    /**
     * @param nameDic
     *            带统计信息的中文姓名词典
     * @param notNameDic
     *            带统计信息的非中文姓名的词典
     * @param tempNameDic
     *            用于记录已识别的临时词典
     * @param leftVergeDic
     *            左边界词词典
     * @param rightVergeDic
     *            右边界词词典
     */
    public SimpleChineseNameAnalyzer(ChineseNameDictionary nameDic,
            NotChineseNameDictionary notNameDic,
            Map<String, String> tempNameDic, Dictionary leftVergeDic,
            Dictionary rightVergeDic)
    {
        this.nameDic = nameDic;
        this.notNameDic = notNameDic;
        this.tempNameDic = tempNameDic;
        this.leftVergeDic = leftVergeDic;
        this.rightVergeDic = rightVergeDic;
        this.initSeperator();
    }

    /**
     * 计算汉字firstName作为姓氏的价值
     * 
     * @param firstName
     *            汉字
     * @return 作为姓氏的价值
     */
    protected double getFirstNameValue(String firstName)
    {
        if (firstName == null || StringUtils.isBlank(firstName)
                || firstName.length() > 2 || firstName.length() < 1)
            return 0;

        // 获取汉字信息
        ChineseNameCharInfo info = this.nameDic
                .getChineseNameCharInfo(firstName);
        NotChineseNameCharInfo notInfo = this.notNameDic
                .getNotChineseNameCharInfo(firstName);

        // 初始化出现频次
        int firstNameFrequence = 0, charFrequence = 0;
        int notFirstNameFrequence = 0, notCharFrequence = 0;

        // 判断是否为姓氏
        if (!this.nameDic.getFirstNameDic().match(firstName))
            return 0;
        // 在姓名统计库与非姓名统计库均出现过
        else if (info != null && notInfo != null)
        {
            firstNameFrequence = info.firstNameFrequence;
            charFrequence = info.charFrequence;

            notFirstNameFrequence = notInfo.firstFrequence;
            notCharFrequence = notInfo.otherFrequence;
        }
        // 在姓名统计库与非姓名统计库中都没出现的姓氏
        else if (info == null && notInfo == null)
        {
            return 1;
        }
        // 姓氏未在非姓名统计库中出现
        else if (notInfo == null)
        {
            firstNameFrequence = info.firstNameFrequence;
            charFrequence = info.charFrequence;

            notFirstNameFrequence = 0;
            notCharFrequence = 0;
        }
        // 姓氏未在姓名统计库中出现
        else if (info == null)
        {
            firstNameFrequence = 0;
            charFrequence = 0;

            notFirstNameFrequence = notInfo.firstFrequence;
            notCharFrequence = notInfo.charFrequence;
        }

        double absoluteValue, relativeValue;
        // 姓名统计库的信息
        // 绝对姓氏概率
        absoluteValue = N * (double) firstNameFrequence
                / (double) this.nameDic.getFirstNameCount();
        // 相对姓氏概率
        if (charFrequence > 0)
            relativeValue = (double) firstNameFrequence
                    / (double) charFrequence;
        else
            relativeValue = 1;
        double value = absoluteValue * relativeValue;

        // 非姓名统计库的信息
        // 绝对姓氏概率
        absoluteValue = N * (double) notFirstNameFrequence
                / (double) this.notNameDic.getFirstCount();
        // 相对姓氏概率
        if (notCharFrequence > 0)
            relativeValue = (double) notFirstNameFrequence
                    / (double) notCharFrequence;
        else
            relativeValue = 1;
        double notValue = absoluteValue * relativeValue;

        // 返回结果
        return value / (value + notValue);
    }

    /**
     * 计算givenName作为双字名字的价值
     * 
     * @param givenName
     *            汉字
     * @return 作为双字汉字首字的价值
     */
    protected double getGivenNameValue(String givenName)
    {
        if (givenName == null || StringUtils.isBlank(givenName)
                || givenName.length() != 1)
            return 0;

        // 获取汉字信息
        ChineseNameCharInfo info = this.nameDic
                .getChineseNameCharInfo(givenName);
        NotChineseNameCharInfo notInfo = this.notNameDic
                .getNotChineseNameCharInfo(givenName);

        int givenNameFrequence = 0, charFrequence = 0;
        int notGivenNameFrequence = 0, notCharFrequence = 0;

        // 在姓名统计库与非姓名统计库都没有出现
        if (info == null && notInfo == null)
            return 1;
        else if (info == null && notInfo != null && notInfo.otherFrequence == 0)
            return 1;
        else if (notInfo == null && info != null
                && info.givenNameFrequence == 0)
            return 1;
        // 在姓名统计库与非姓名统计库都出现
        else if (info != null && notInfo != null)
        {
            givenNameFrequence = info.givenNameFrequence;
            charFrequence = info.charFrequence;

            notGivenNameFrequence = notInfo.otherFrequence;
            notCharFrequence = notInfo.charFrequence;
        }
        // 在非姓名统计库中无出现
        else if (notInfo == null)
        {
            givenNameFrequence = info.givenNameFrequence;
            charFrequence = info.charFrequence;

            notGivenNameFrequence = 0;
            notCharFrequence = 0;
        }
        // 在姓名统计库中无出现
        else if (info == null)
        {
            givenNameFrequence = 0;
            charFrequence = 0;

            notGivenNameFrequence = notInfo.otherFrequence;
            notCharFrequence = notInfo.charFrequence;
        }

        double absoluteValue, relativeValue;
        // 姓名统计库的信息
        // 绝对名字用字概率
        absoluteValue = N * (double) givenNameFrequence
                / (double) this.nameDic.getGivenNameCount();
        // 相对名字用字概率
        if (charFrequence > 0)
            relativeValue = (double) givenNameFrequence
                    / (double) charFrequence;
        else
            relativeValue = 1;
        double value = absoluteValue * relativeValue;

        // 非姓名统计库的信息
        // 绝对名字用字概率
        absoluteValue = N * (double) notGivenNameFrequence
                / (double) this.notNameDic.getOtherCount();
        // 相对名字用字概率
        if (notCharFrequence > 0)
            relativeValue = (double) notGivenNameFrequence
                    / (double) notCharFrequence;
        else
            relativeValue = 0;
        double notValue = absoluteValue * relativeValue;

        // 返回结果
        return value / (value + notValue);
    }

    /**
     * @return 返回 leftVergeDic。
     */
    public Dictionary getLeftVergeDic()
    {
        return leftVergeDic;
    }

    /**
     * @return 返回 nameDic。
     */
    public ChineseNameDictionary getNameDic()
    {
        return nameDic;
    }

    /**
     * @return 返回 notNameDic。
     */
    public NotChineseNameDictionary getNotNameDic()
    {
        return notNameDic;
    }

    /**
     * @return 返回 rightVergeDic。
     */
    public Dictionary getRightVergeDic()
    {
        return rightVergeDic;
    }

    /**
     * 获取阀值
     * 
     * @return 返回 setpoint。
     */
    public double getSetpoint()
    {
        return setpoint;
    }

    /**
     * @return 返回 tempNameDic。
     */
    public Map<String, String> getTempNameDic()
    {
        return tempNameDic;
    }

    /**
     * 对输入的原分词结果srcList进行中文人名识别
     * 
     * @param srcList
     *            原分词结果
     * @return 中文人名识别处理后的分词结果
     */
    @Override
    public List<String> identify(List<String> srcList)
    {
        // 获取姓氏词典
        ChineseFirstNameDictionary firstNameDic = nameDic.getFirstNameDic();
        // 初始化区间位移
        int startPos = 0, endPos = 0;
        // 循环
        while (startPos < srcList.size())
        {
            // 表示startPos位置不是姓氏的情况
            boolean isNotFirstName = startPos < srcList.size()
                    && (srcList.get(startPos).length() != 1 || srcList.get(
                            startPos).length() != 2)
                    && !firstNameDic.match(srcList.get(startPos));
            // 表示startPos位置是以姓氏开头且词长为2的情况
            boolean isBeginWithFirstName = startPos < srcList.size()
                    && srcList.get(startPos).length() == 2
                    && firstNameDic
                            .match(srcList.get(startPos).substring(0, 1))
                    && this.getFirstNameValue(srcList.get(startPos).substring(
                            0, 1)) > 0.5 && this.leftVergeDic != null
                    && startPos > 0 && startPos + 1 < srcList.size()
                    && this.leftVergeDic.match(srcList.get(startPos - 1))
                    && this.seperator.indexOf(srcList.get(startPos + 1)) < 0;

            // 寻找分词结果中姓氏的位置
            while (isNotFirstName && !isBeginWithFirstName)
            {
                startPos++;
                // 表示startPos位置不是姓氏的情况
                isNotFirstName = startPos < srcList.size()
                        && (srcList.get(startPos).length() != 1 || srcList.get(
                                startPos).length() != 2)
                        && !firstNameDic.match(srcList.get(startPos));
                // 表示startPos位置是以姓氏开头且词长为2的情况
                isBeginWithFirstName = startPos < srcList.size()
                        && srcList.get(startPos).length() == 2
                        && firstNameDic.match(srcList.get(startPos).substring(
                                0, 1))
                        && this.getFirstNameValue(srcList.get(startPos)
                                .substring(0, 1)) > 0.5
                        && this.leftVergeDic != null
                        && startPos > 0
                        && startPos + 1 < srcList.size()
                        && this.leftVergeDic.match(srcList.get(startPos - 1))
                        && this.seperator.indexOf(srcList.get(startPos + 1)) < 0;
            }
            // 如果startPos位置是以姓氏以姓氏开头且词长为2
            if (isBeginWithFirstName)
            {
                String f = srcList.get(startPos).substring(0, 1);
                String s = srcList.get(startPos).substring(1);
                srcList.set(startPos, f);
                srcList.add(startPos + 1, s);
            }

            // 当startPos超出原分词结果集中词汇数则返回
            if (startPos >= srcList.size())
                break;

            // 用于判断[startPos - endPos]之间字符串组成的词组是否存在于姓名词典中
            String tempName = srcList.get(startPos);

            endPos = startPos + 1;
            // 当endPos超出原分词结果集中词汇数则返回
            if (endPos >= srcList.size())
                break;
            // 判断紧跟姓氏后的分词结果长度是否大于2
            if (srcList.get(endPos).length() > 2)
                startPos = endPos + 1;
            else if (srcList.get(endPos).length() == 1
                    && this.seperator.indexOf(srcList.get(endPos)) >= 0)
                // 判断endPos位置上的是否为标点符号
                startPos = endPos + 1;
            else
            {
                tempName += srcList.get(endPos);
                // endPos位置上的分词结果长度为1时，并且endPos+1位置上的分词结果长度也为1且endPos+1不为标点符号
                if (srcList.get(endPos).length() == 1
                        && endPos + 1 < srcList.size()
                        && srcList.get(endPos + 1).length() == 1
                        && this.seperator.indexOf(srcList.get(endPos + 1)) < 0)
                {
                    endPos++;
                    tempName += srcList.get(endPos);
                }

                // 判断临时姓名是否在姓名词典中出现过
                if (this.nameDic.match(tempName))
                {
                    // hades 姓名识别
                    // 合并字符串
                    srcList.set(startPos, tempName);
                    for (int i = startPos; i < endPos; i++)
                        srcList.remove(startPos + 1);
                    startPos++;
                }
                // 判断临时姓名是否在非姓名词典中出现过
                else if (this.notNameDic.match(tempName))
                {
                    // hades 姓名识别
                    startPos = endPos + 1;
                }
                else
                {
                    boolean doubleOrNot = (endPos - startPos == 2)
                            || (endPos - startPos == 1 && srcList.get(endPos)
                                    .length() == 2);
                    if (doubleOrNot)
                    {
                        // startPos后字符串已组合成一词组
                        if (endPos - startPos == 1
                                && this
                                        .getFirstNameValue(srcList
                                                .get(startPos)) < 0.01)
                        {
                            startPos = endPos + 1;
                            continue;
                        }

                        // 双字姓名处理
                        String fch, sch;
                        if (endPos - startPos == 1)
                        {
                            // 姓氏后的字符串是一个2字词组
                            fch = srcList.get(endPos).substring(0, 1);
                            sch = srcList.get(endPos).substring(1);
                        }
                        else
                        {
                            // 姓氏后的2个字符串均为单字
                            fch = srcList.get(endPos - 1);
                            sch = srcList.get(endPos);
                        }
                        // 情况1：单双名竞争
                        double singleValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * this.getGivenNameValue(fch);
                        double doubleValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * Math.sqrt(this.getGivenNameValue(fch)
                                        * this.getGivenNameValue(sch));

                        // 对startPos-1处为左边界的价值进行递增
                        if (startPos == 0
                                || (startPos > 0 && ((this.leftVergeDic != null && this.leftVergeDic
                                        .match(srcList.get(startPos - 1))) || srcList
                                        .get(startPos - 1).equals("\n"))))
                        {
                            singleValue *= M;
                            doubleValue *= M;
                        }
                        // 对endPos+1处为右边界词的价值进行递增
                        if (endPos + 1 < srcList.size()
                                && this.rightVergeDic != null
                                && this.rightVergeDic.match(srcList
                                        .get(endPos + 1)))
                            doubleValue *= M;
                        // 如果临时数据库中有这个姓名则价值递增
                        if (this.tempNameDic != null
                                && this.tempNameDic.containsKey(srcList
                                        .get(startPos)
                                        + fch))
                            singleValue *= M;
                        if (this.tempNameDic != null
                                && this.tempNameDic.containsKey(tempName))
                            doubleValue *= M;

                        // // hades print
                        // System.out.println(tempName + "\tSingle:" +
                        // singleValue
                        // + "\tDouble:" + doubleValue);

                        // 标示是否已进行合并操作
                        boolean finish = false;
                        // 当fch也为姓氏是激发情况2
                        if (srcList.get(startPos).length() == 1
                                && firstNameDic.match(fch))
                        {
                            // 情况2：姓氏竞争
                            double nameValue = this.getFirstNameValue(fch)
                                    * this.getGivenNameValue(sch);

                            // 对endPos+1处为右边界词的价值进行递增
                            if (endPos + 1 < srcList.size()
                                    && this.rightVergeDic != null
                                    && this.rightVergeDic.match(srcList
                                            .get(endPos + 1)))
                                nameValue *= M;
                            // 如果临时数据库中有这个姓名则价值递增
                            if (this.tempNameDic != null
                                    && this.tempNameDic.containsKey(fch + sch))
                                nameValue *= M;

                            // // hades print
                            // System.out
                            // .println(tempName + "\tLast:" + nameValue);
                            // System.out.println();

                            // 当最后两字成为名字的概率最大且大于阀值时
                            if (nameValue > this.setpoint
                                    && nameValue > singleValue
                                    && nameValue > doubleValue)
                            {
                                // 判断fch与sch是否已组合为词
                                if (endPos - startPos == 1)
                                {
                                    startPos = endPos + 1;
                                    finish = true;
                                }
                                else
                                {
                                    // hades 姓名识别
                                    srcList.set(endPos - 1, fch + sch);
                                    // 放入临时数据库
                                    if (this.tempNameDic != null)
                                    {
                                        this.tempNameDic.put(srcList
                                                .get(endPos - 1), tempName);
                                    }
                                    srcList.remove(endPos);
                                    // 若果fch也是姓氏则以它为startPos多计算一次
                                    if (firstNameDic.match(fch))
                                        startPos = endPos - 1;
                                    else
                                        startPos = endPos;
                                    finish = true;
                                }
                                // end of if(endPos-startPos==1)
                            }
                        }
                        // end of
                        // if(srcList.get(startPos).length()==1&&firstNameDic.match(fch))
                        if (!finish)
                        {
                            // 单字姓名胜出
                            if (singleValue > this.setpoint
                                    && singleValue > doubleValue)
                            {
                                // hades 姓名识别
                                srcList.set(startPos, srcList.get(startPos)
                                        + fch);
                                // 放入临时数据库
                                if (this.tempNameDic != null)
                                    this.tempNameDic.put(srcList.get(startPos),
                                            tempName);
                                srcList.set(startPos + 1, sch);
                                // 双单字情况
                                if (endPos - startPos == 2)
                                {
                                    srcList.remove(endPos);
                                }
                                startPos += 2;
                            }
                            // 双字姓名胜出
                            else if (doubleValue > this.setpoint
                                    && doubleValue >= singleValue)
                            {
                                // hades 姓名识别
                                srcList.set(startPos, tempName);
                                // 放入临时数据库
                                if (this.tempNameDic != null)
                                    this.tempNameDic.put(tempName, tempName);
                                for (int i = startPos; i < endPos; i++)
                                    srcList.remove(startPos + 1);
                                startPos++;
                            }
                            else
                            {
                                startPos = endPos + 1;
                            }
                            // end of if(singleValue>doubleValue)
                        }
                        // end of if(!finish);
                    }
                    else
                    {
                        // 单字姓名处理
                        double nameValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * this.getGivenNameValue(srcList.get(endPos));
                        // 对startPos-1处为左边界词的价值进行递增
                        if (startPos > 0
                                && ((this.leftVergeDic != null && this.leftVergeDic
                                        .match(srcList.get(startPos - 1))) || srcList
                                        .get(startPos - 1).equals("\n")))
                            nameValue *= M;

                        // 对endPos+1处为右边界的价值进行递增
                        if (endPos + 1 < srcList.size()
                                && this.rightVergeDic != null
                                && this.rightVergeDic.match(srcList
                                        .get(endPos + 1)))
                            nameValue *= M;
                        // 如果临时数据库中有这个姓名则价值递增
                        if (this.tempNameDic != null
                                && this.tempNameDic.containsKey(tempName))
                            nameValue *= M;

                        if (nameValue > this.setpoint)
                        {
                            // hades 姓名识别
                            // 合并字符串
                            srcList.set(startPos, tempName);
                            // 放入临时数据库
                            if (this.tempNameDic != null)
                                this.tempNameDic.put(tempName, tempName);
                            for (int i = startPos; i < endPos; i++)
                                srcList.remove(startPos + 1);
                            startPos++;
                        }
                        else
                        {
                            startPos = endPos + 1;
                        }
                        // end of if(nameValue>this.setpoint)
                    }
                    // end of if(doubleOrNot)
                }
                // end of if(this.nameDic.match(tempName))

            }
            // end of if(srcList.get(endPos).length()>2)
        }
        return srcList;
    }

    /**
     * 设置阀值
     * 
     * @param setpoint
     *            要设置的 setpoint。
     */
    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
    }
}
