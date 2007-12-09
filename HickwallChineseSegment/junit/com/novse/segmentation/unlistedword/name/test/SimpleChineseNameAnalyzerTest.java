/*
 * @����:Hades , ��������:May 15, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.unlistedword.name.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.matching.dictionary.HashDictionary;
import com.novse.segmentation.core.matching.processor.MaxMatchSegmentProcessor;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;

/**
 * @author Mac Kwan
 * 
 */
public class SimpleChineseNameAnalyzerTest extends TestCase
{
    private SimpleChineseNameAnalyzer analyzer = null;

    private ChineseNameDictionary dic = null;

    private NotChineseNameDictionary notDic = null;

    private ChineseFirstNameDictionary firstNameDic = null;

    private List<String> list = new ArrayList<String>();

    private MaxMatchSegmentProcessor processor = null;

    /*
     * ���� Javadoc��
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        // ��ʼ��������
        firstNameDic = new ChineseFirstNameDictionary();
        firstNameDic.loadDictionary("Dic/FirstName.txt");

        dic = new ChineseNameDictionary(firstNameDic);
        dic.loadDictionary("Dic/Name.txt");

        notDic = new NotChineseNameDictionary(firstNameDic);
        notDic.loadDictionary("Dic/UnName.txt");

        Dictionary left = new HashDictionary();
        left.loadDictionary("Dic/LeftVerge.txt");

        Dictionary right = new HashDictionary();
        right.loadDictionary("Dic/RightVerge.txt");

        HashMap<String, String> temp = new HashMap<String, String>();
        analyzer = new SimpleChineseNameAnalyzer(dic, notDic, temp, left, right);
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "springConfig.xml");
        processor = (MaxMatchSegmentProcessor) context
                .getBean("maxMatchSegmentBean");
    }

    public final void testIdentify() throws Exception
    {
        // �����������
        String text = "";
        String line = null;
        BufferedReader in = new BufferedReader(new FileReader("src.txt"));
        // ʵ���е�����
        TreeSet<String> answer = new TreeSet<String>();
        // �����ı�
        while ((line = in.readLine()) != null)
        {
            text += line;
            text += "\r";
        }
        in.close();

        // ������
        in = new BufferedReader(new FileReader("nameResult.txt"));
        while ((line = in.readLine()) != null)
        {
            if (StringUtils.isBlank(line))
                continue;
            answer.add(line);
        }
        in.close();

        for (int i = 0; i < text.length(); i++)
        {
            list.add(text.substring(i, i + 1));
        }
        list = processor.textProcess(text);

        // ʶ���õ�����
        TreeSet<String> result;

        for (int i = 1; i < 2; i++)
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter("result/name/����ʶ��" + i + ".txt")));

            result = new TreeSet<String>();
            ArrayList<String> tempList = new ArrayList<String>();
            for (String s : list)
                tempList.add(s);

            double setpoint = (double) i / 10000.0;
            analyzer.setSetpoint(setpoint);

            System.out.println("��ֵΪ��" + setpoint);

            for (String s : analyzer.identify(tempList))
            {
                out.println(s);
                if (s.endsWith("name"))
                    result.add(s);
            }

            // ��ʼ����ȷ������δʶ�����Ŀ
            int right = 0, wrong = 0;
            for (String s : answer)
            {
                if (tempList.contains(s))
                    right++;

                if (!result.contains(s))
                    wrong++;
            }
            System.out
                    .println("�ٻ��ʣ�" + (double) right / (double) answer.size());
            System.out.println("׼ȷ�ʣ�" + (double) right
                    / (double) (right + wrong));

            out.flush();
            out.close();
            out = null;

            result.clear();
            result = null;
            tempList.clear();
            tempList = null;
        }

    }

}
