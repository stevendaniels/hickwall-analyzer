/*
 * @����:Hades , ��������:May 27, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.dic.test;

import java.io.BufferedReader;
import java.io.FileReader;

import junit.framework.TestCase;

import org.apache.commons.lang.time.StopWatch;

import com.novse.segmentation.core.matching.dictionary.DiagramDictionary;
import com.novse.segmentation.core.matching.dictionary.Dictionary;

/**
 * @author Mac Kwan ���ڱȽϸ��ִʵ����ܵĲ�������
 */
public class DictionaryTest extends TestCase
{
    /**
     * �ʵ�ʵ��
     */
    private Dictionary dic = null;

    private static final String DICPATH = "Dic/Txt/Dic.txt";

    private static final String WORDPATH = "TestMatch.txt";

    /*
     * ���� Javadoc��
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        dic = new DiagramDictionary();
    }

    /**
     * ��������ʿ�ʱ��
     * 
     * @throws Exception
     */
    public void estLoadTime() throws Exception
    {
        StopWatch sw = new StopWatch();
        sw.start();
        dic.loadDictionary(DICPATH);
        sw.stop();
        System.out.println("����ʵ�ʱ�䣺" + sw.getTime() + "ms");
    }

    /**
     * ˳��������дʻ�
     * 
     * @throws Exception
     */
    public void estMatchAll() throws Exception
    {
        StopWatch sw = new StopWatch();
        dic.loadDictionary(DICPATH);
        BufferedReader in = new BufferedReader(new FileReader(DICPATH));
        String line = null;

        sw.start();
        while ((line = in.readLine()) != null)
        {
            assertTrue(dic.match(line));
        }
        in.close();
        sw.stop();
        System.out.println("˳��������дʻ�ʱ�䣺" + sw.getTime() + "ms");
    }

    /**
     * �����ѯ���ִʻ�
     * 
     * @throws Exception
     */
    public void testMatch() throws Exception
    {
        StopWatch sw = new StopWatch();
        dic.loadDictionary(DICPATH);
        BufferedReader in = new BufferedReader(new FileReader(WORDPATH));
        String line = null;

        sw.start();
        while ((line = in.readLine()) != null)
        {
            dic.match(line);
        }
        in.close();
        sw.stop();
        System.out.println("�����ѯ���ִʻ�ʱ�䣺" + sw.getTime() + "ms");
    }

}
