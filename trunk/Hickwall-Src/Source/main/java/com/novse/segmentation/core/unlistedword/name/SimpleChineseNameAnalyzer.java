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
 * @author Mac Kwan �򵥵�����ʶ����ʵ����
 */
public class SimpleChineseNameAnalyzer extends AbstractUnListedWordAnalyzer
{
    /**
     * ��߽��
     */
    private Dictionary leftVergeDic = null;

    /**
     * �߽���ʵ���ϵ��
     */
    private final double M = 10.0;

    /**
     * ���ʵ���ϵ��
     */
    private final double N = 100.0;

    /**
     * ��ͳ����Ϣ�����������ʵ�
     */
    private ChineseNameDictionary nameDic = null;

    /**
     * ��ͳ����Ϣ�ķ����������Ĵʵ�
     */
    private NotChineseNameDictionary notNameDic = null;

    /**
     * �ұ߽��
     */
    private Dictionary rightVergeDic = null;

    /**
     * �ж�Ϊ�����ķ�ֵ
     */
    private double setpoint = 1;

    /**
     * ��ʶ���õĴ��������ʱ�ʵ���
     */
    private Map<String, String> tempNameDic = null;

    /**
     * @param nameDic
     *            ��ͳ����Ϣ�����������ʵ�
     * @param notNameDic
     *            ��ͳ����Ϣ�ķ����������Ĵʵ�
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
     *            ��ͳ����Ϣ�����������ʵ�
     * @param notNameDic
     *            ��ͳ����Ϣ�ķ����������Ĵʵ�
     * @param leftVergeDic
     *            ��߽�ʴʵ�
     * @param rightVergeDic
     *            �ұ߽�ʴʵ�
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
     *            ��ͳ����Ϣ�����������ʵ�
     * @param notNameDic
     *            ��ͳ����Ϣ�ķ����������Ĵʵ�
     * @param tempNameDic
     *            ���ڼ�¼��ʶ�����ʱ�ʵ�
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
     *            ��ͳ����Ϣ�����������ʵ�
     * @param notNameDic
     *            ��ͳ����Ϣ�ķ����������Ĵʵ�
     * @param tempNameDic
     *            ���ڼ�¼��ʶ�����ʱ�ʵ�
     * @param leftVergeDic
     *            ��߽�ʴʵ�
     * @param rightVergeDic
     *            �ұ߽�ʴʵ�
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
     * ���㺺��firstName��Ϊ���ϵļ�ֵ
     * 
     * @param firstName
     *            ����
     * @return ��Ϊ���ϵļ�ֵ
     */
    protected double getFirstNameValue(String firstName)
    {
        if (firstName == null || StringUtils.isBlank(firstName)
                || firstName.length() > 2 || firstName.length() < 1)
            return 0;

        // ��ȡ������Ϣ
        ChineseNameCharInfo info = this.nameDic
                .getChineseNameCharInfo(firstName);
        NotChineseNameCharInfo notInfo = this.notNameDic
                .getNotChineseNameCharInfo(firstName);

        // ��ʼ������Ƶ��
        int firstNameFrequence = 0, charFrequence = 0;
        int notFirstNameFrequence = 0, notCharFrequence = 0;

        // �ж��Ƿ�Ϊ����
        if (!this.nameDic.getFirstNameDic().match(firstName))
            return 0;
        // ������ͳ�ƿ��������ͳ�ƿ�����ֹ�
        else if (info != null && notInfo != null)
        {
            firstNameFrequence = info.firstNameFrequence;
            charFrequence = info.charFrequence;

            notFirstNameFrequence = notInfo.firstFrequence;
            notCharFrequence = notInfo.otherFrequence;
        }
        // ������ͳ�ƿ��������ͳ�ƿ��ж�û���ֵ�����
        else if (info == null && notInfo == null)
        {
            return 1;
        }
        // ����δ�ڷ�����ͳ�ƿ��г���
        else if (notInfo == null)
        {
            firstNameFrequence = info.firstNameFrequence;
            charFrequence = info.charFrequence;

            notFirstNameFrequence = 0;
            notCharFrequence = 0;
        }
        // ����δ������ͳ�ƿ��г���
        else if (info == null)
        {
            firstNameFrequence = 0;
            charFrequence = 0;

            notFirstNameFrequence = notInfo.firstFrequence;
            notCharFrequence = notInfo.charFrequence;
        }

        double absoluteValue, relativeValue;
        // ����ͳ�ƿ����Ϣ
        // �������ϸ���
        absoluteValue = N * (double) firstNameFrequence
                / (double) this.nameDic.getFirstNameCount();
        // ������ϸ���
        if (charFrequence > 0)
            relativeValue = (double) firstNameFrequence
                    / (double) charFrequence;
        else
            relativeValue = 1;
        double value = absoluteValue * relativeValue;

        // ������ͳ�ƿ����Ϣ
        // �������ϸ���
        absoluteValue = N * (double) notFirstNameFrequence
                / (double) this.notNameDic.getFirstCount();
        // ������ϸ���
        if (notCharFrequence > 0)
            relativeValue = (double) notFirstNameFrequence
                    / (double) notCharFrequence;
        else
            relativeValue = 1;
        double notValue = absoluteValue * relativeValue;

        // ���ؽ��
        return value / (value + notValue);
    }

    /**
     * ����givenName��Ϊ˫�����ֵļ�ֵ
     * 
     * @param givenName
     *            ����
     * @return ��Ϊ˫�ֺ������ֵļ�ֵ
     */
    protected double getGivenNameValue(String givenName)
    {
        if (givenName == null || StringUtils.isBlank(givenName)
                || givenName.length() != 1)
            return 0;

        // ��ȡ������Ϣ
        ChineseNameCharInfo info = this.nameDic
                .getChineseNameCharInfo(givenName);
        NotChineseNameCharInfo notInfo = this.notNameDic
                .getNotChineseNameCharInfo(givenName);

        int givenNameFrequence = 0, charFrequence = 0;
        int notGivenNameFrequence = 0, notCharFrequence = 0;

        // ������ͳ�ƿ��������ͳ�ƿⶼû�г���
        if (info == null && notInfo == null)
            return 1;
        else if (info == null && notInfo != null && notInfo.otherFrequence == 0)
            return 1;
        else if (notInfo == null && info != null
                && info.givenNameFrequence == 0)
            return 1;
        // ������ͳ�ƿ��������ͳ�ƿⶼ����
        else if (info != null && notInfo != null)
        {
            givenNameFrequence = info.givenNameFrequence;
            charFrequence = info.charFrequence;

            notGivenNameFrequence = notInfo.otherFrequence;
            notCharFrequence = notInfo.charFrequence;
        }
        // �ڷ�����ͳ�ƿ����޳���
        else if (notInfo == null)
        {
            givenNameFrequence = info.givenNameFrequence;
            charFrequence = info.charFrequence;

            notGivenNameFrequence = 0;
            notCharFrequence = 0;
        }
        // ������ͳ�ƿ����޳���
        else if (info == null)
        {
            givenNameFrequence = 0;
            charFrequence = 0;

            notGivenNameFrequence = notInfo.otherFrequence;
            notCharFrequence = notInfo.charFrequence;
        }

        double absoluteValue, relativeValue;
        // ����ͳ�ƿ����Ϣ
        // �����������ָ���
        absoluteValue = N * (double) givenNameFrequence
                / (double) this.nameDic.getGivenNameCount();
        // ����������ָ���
        if (charFrequence > 0)
            relativeValue = (double) givenNameFrequence
                    / (double) charFrequence;
        else
            relativeValue = 1;
        double value = absoluteValue * relativeValue;

        // ������ͳ�ƿ����Ϣ
        // �����������ָ���
        absoluteValue = N * (double) notGivenNameFrequence
                / (double) this.notNameDic.getOtherCount();
        // ����������ָ���
        if (notCharFrequence > 0)
            relativeValue = (double) notGivenNameFrequence
                    / (double) notCharFrequence;
        else
            relativeValue = 0;
        double notValue = absoluteValue * relativeValue;

        // ���ؽ��
        return value / (value + notValue);
    }

    /**
     * @return ���� leftVergeDic��
     */
    public Dictionary getLeftVergeDic()
    {
        return leftVergeDic;
    }

    /**
     * @return ���� nameDic��
     */
    public ChineseNameDictionary getNameDic()
    {
        return nameDic;
    }

    /**
     * @return ���� notNameDic��
     */
    public NotChineseNameDictionary getNotNameDic()
    {
        return notNameDic;
    }

    /**
     * @return ���� rightVergeDic��
     */
    public Dictionary getRightVergeDic()
    {
        return rightVergeDic;
    }

    /**
     * ��ȡ��ֵ
     * 
     * @return ���� setpoint��
     */
    public double getSetpoint()
    {
        return setpoint;
    }

    /**
     * @return ���� tempNameDic��
     */
    public Map<String, String> getTempNameDic()
    {
        return tempNameDic;
    }

    /**
     * �������ԭ�ִʽ��srcList������������ʶ��
     * 
     * @param srcList
     *            ԭ�ִʽ��
     * @return ��������ʶ�����ķִʽ��
     */
    @Override
    public List<String> identify(List<String> srcList)
    {
        // ��ȡ���ϴʵ�
        ChineseFirstNameDictionary firstNameDic = nameDic.getFirstNameDic();
        // ��ʼ������λ��
        int startPos = 0, endPos = 0;
        // ѭ��
        while (startPos < srcList.size())
        {
            // ��ʾstartPosλ�ò������ϵ����
            boolean isNotFirstName = startPos < srcList.size()
                    && (srcList.get(startPos).length() != 1 || srcList.get(
                            startPos).length() != 2)
                    && !firstNameDic.match(srcList.get(startPos));
            // ��ʾstartPosλ���������Ͽ�ͷ�Ҵʳ�Ϊ2�����
            boolean isBeginWithFirstName = startPos < srcList.size()
                    && srcList.get(startPos).length() == 2
                    && firstNameDic
                            .match(srcList.get(startPos).substring(0, 1))
                    && this.getFirstNameValue(srcList.get(startPos).substring(
                            0, 1)) > 0.5 && this.leftVergeDic != null
                    && startPos > 0 && startPos + 1 < srcList.size()
                    && this.leftVergeDic.match(srcList.get(startPos - 1))
                    && this.seperator.indexOf(srcList.get(startPos + 1)) < 0;

            // Ѱ�ҷִʽ�������ϵ�λ��
            while (isNotFirstName && !isBeginWithFirstName)
            {
                startPos++;
                // ��ʾstartPosλ�ò������ϵ����
                isNotFirstName = startPos < srcList.size()
                        && (srcList.get(startPos).length() != 1 || srcList.get(
                                startPos).length() != 2)
                        && !firstNameDic.match(srcList.get(startPos));
                // ��ʾstartPosλ���������Ͽ�ͷ�Ҵʳ�Ϊ2�����
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
            // ���startPosλ���������������Ͽ�ͷ�Ҵʳ�Ϊ2
            if (isBeginWithFirstName)
            {
                String f = srcList.get(startPos).substring(0, 1);
                String s = srcList.get(startPos).substring(1);
                srcList.set(startPos, f);
                srcList.add(startPos + 1, s);
            }

            // ��startPos����ԭ�ִʽ�����дʻ����򷵻�
            if (startPos >= srcList.size())
                break;

            // �����ж�[startPos - endPos]֮���ַ�����ɵĴ����Ƿ�����������ʵ���
            String tempName = srcList.get(startPos);

            endPos = startPos + 1;
            // ��endPos����ԭ�ִʽ�����дʻ����򷵻�
            if (endPos >= srcList.size())
                break;
            // �жϽ������Ϻ�ķִʽ�������Ƿ����2
            if (srcList.get(endPos).length() > 2)
                startPos = endPos + 1;
            else if (srcList.get(endPos).length() == 1
                    && this.seperator.indexOf(srcList.get(endPos)) >= 0)
                // �ж�endPosλ���ϵ��Ƿ�Ϊ������
                startPos = endPos + 1;
            else
            {
                tempName += srcList.get(endPos);
                // endPosλ���ϵķִʽ������Ϊ1ʱ������endPos+1λ���ϵķִʽ������ҲΪ1��endPos+1��Ϊ������
                if (srcList.get(endPos).length() == 1
                        && endPos + 1 < srcList.size()
                        && srcList.get(endPos + 1).length() == 1
                        && this.seperator.indexOf(srcList.get(endPos + 1)) < 0)
                {
                    endPos++;
                    tempName += srcList.get(endPos);
                }

                // �ж���ʱ�����Ƿ��������ʵ��г��ֹ�
                if (this.nameDic.match(tempName))
                {
                    // hades ����ʶ��
                    // �ϲ��ַ���
                    srcList.set(startPos, tempName);
                    for (int i = startPos; i < endPos; i++)
                        srcList.remove(startPos + 1);
                    startPos++;
                }
                // �ж���ʱ�����Ƿ��ڷ������ʵ��г��ֹ�
                else if (this.notNameDic.match(tempName))
                {
                    // hades ����ʶ��
                    startPos = endPos + 1;
                }
                else
                {
                    boolean doubleOrNot = (endPos - startPos == 2)
                            || (endPos - startPos == 1 && srcList.get(endPos)
                                    .length() == 2);
                    if (doubleOrNot)
                    {
                        // startPos���ַ�������ϳ�һ����
                        if (endPos - startPos == 1
                                && this
                                        .getFirstNameValue(srcList
                                                .get(startPos)) < 0.01)
                        {
                            startPos = endPos + 1;
                            continue;
                        }

                        // ˫����������
                        String fch, sch;
                        if (endPos - startPos == 1)
                        {
                            // ���Ϻ���ַ�����һ��2�ִ���
                            fch = srcList.get(endPos).substring(0, 1);
                            sch = srcList.get(endPos).substring(1);
                        }
                        else
                        {
                            // ���Ϻ��2���ַ�����Ϊ����
                            fch = srcList.get(endPos - 1);
                            sch = srcList.get(endPos);
                        }
                        // ���1����˫������
                        double singleValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * this.getGivenNameValue(fch);
                        double doubleValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * Math.sqrt(this.getGivenNameValue(fch)
                                        * this.getGivenNameValue(sch));

                        // ��startPos-1��Ϊ��߽�ļ�ֵ���е���
                        if (startPos == 0
                                || (startPos > 0 && ((this.leftVergeDic != null && this.leftVergeDic
                                        .match(srcList.get(startPos - 1))) || srcList
                                        .get(startPos - 1).equals("\n"))))
                        {
                            singleValue *= M;
                            doubleValue *= M;
                        }
                        // ��endPos+1��Ϊ�ұ߽�ʵļ�ֵ���е���
                        if (endPos + 1 < srcList.size()
                                && this.rightVergeDic != null
                                && this.rightVergeDic.match(srcList
                                        .get(endPos + 1)))
                            doubleValue *= M;
                        // �����ʱ���ݿ���������������ֵ����
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

                        // ��ʾ�Ƿ��ѽ��кϲ�����
                        boolean finish = false;
                        // ��fchҲΪ�����Ǽ������2
                        if (srcList.get(startPos).length() == 1
                                && firstNameDic.match(fch))
                        {
                            // ���2�����Ͼ���
                            double nameValue = this.getFirstNameValue(fch)
                                    * this.getGivenNameValue(sch);

                            // ��endPos+1��Ϊ�ұ߽�ʵļ�ֵ���е���
                            if (endPos + 1 < srcList.size()
                                    && this.rightVergeDic != null
                                    && this.rightVergeDic.match(srcList
                                            .get(endPos + 1)))
                                nameValue *= M;
                            // �����ʱ���ݿ���������������ֵ����
                            if (this.tempNameDic != null
                                    && this.tempNameDic.containsKey(fch + sch))
                                nameValue *= M;

                            // // hades print
                            // System.out
                            // .println(tempName + "\tLast:" + nameValue);
                            // System.out.println();

                            // ��������ֳ�Ϊ���ֵĸ�������Ҵ��ڷ�ֵʱ
                            if (nameValue > this.setpoint
                                    && nameValue > singleValue
                                    && nameValue > doubleValue)
                            {
                                // �ж�fch��sch�Ƿ������Ϊ��
                                if (endPos - startPos == 1)
                                {
                                    startPos = endPos + 1;
                                    finish = true;
                                }
                                else
                                {
                                    // hades ����ʶ��
                                    srcList.set(endPos - 1, fch + sch);
                                    // ������ʱ���ݿ�
                                    if (this.tempNameDic != null)
                                    {
                                        this.tempNameDic.put(srcList
                                                .get(endPos - 1), tempName);
                                    }
                                    srcList.remove(endPos);
                                    // ����fchҲ������������ΪstartPos�����һ��
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
                            // ��������ʤ��
                            if (singleValue > this.setpoint
                                    && singleValue > doubleValue)
                            {
                                // hades ����ʶ��
                                srcList.set(startPos, srcList.get(startPos)
                                        + fch);
                                // ������ʱ���ݿ�
                                if (this.tempNameDic != null)
                                    this.tempNameDic.put(srcList.get(startPos),
                                            tempName);
                                srcList.set(startPos + 1, sch);
                                // ˫�������
                                if (endPos - startPos == 2)
                                {
                                    srcList.remove(endPos);
                                }
                                startPos += 2;
                            }
                            // ˫������ʤ��
                            else if (doubleValue > this.setpoint
                                    && doubleValue >= singleValue)
                            {
                                // hades ����ʶ��
                                srcList.set(startPos, tempName);
                                // ������ʱ���ݿ�
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
                        // ������������
                        double nameValue = this.getFirstNameValue(srcList
                                .get(startPos))
                                * this.getGivenNameValue(srcList.get(endPos));
                        // ��startPos-1��Ϊ��߽�ʵļ�ֵ���е���
                        if (startPos > 0
                                && ((this.leftVergeDic != null && this.leftVergeDic
                                        .match(srcList.get(startPos - 1))) || srcList
                                        .get(startPos - 1).equals("\n")))
                            nameValue *= M;

                        // ��endPos+1��Ϊ�ұ߽�ļ�ֵ���е���
                        if (endPos + 1 < srcList.size()
                                && this.rightVergeDic != null
                                && this.rightVergeDic.match(srcList
                                        .get(endPos + 1)))
                            nameValue *= M;
                        // �����ʱ���ݿ���������������ֵ����
                        if (this.tempNameDic != null
                                && this.tempNameDic.containsKey(tempName))
                            nameValue *= M;

                        if (nameValue > this.setpoint)
                        {
                            // hades ����ʶ��
                            // �ϲ��ַ���
                            srcList.set(startPos, tempName);
                            // ������ʱ���ݿ�
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
     * ���÷�ֵ
     * 
     * @param setpoint
     *            Ҫ���õ� setpoint��
     */
    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
    }
}
