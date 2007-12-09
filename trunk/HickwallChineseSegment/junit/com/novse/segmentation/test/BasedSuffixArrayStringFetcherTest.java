/*
 * @����:Hades , ��������:2006-11-15
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.apache.commons.lang.time.StopWatch;

import com.novse.segmentation.core.statistic.fetcher.BasedSuffixArrayStringFetcher;

public class BasedSuffixArrayStringFetcherTest extends TestCase
{
    private BasedSuffixArrayStringFetcher processor = null;

    protected void setUp() throws Exception
    {
        processor = new BasedSuffixArrayStringFetcher();
    }

    public final void testFileFetch() throws IOException
    {
        StopWatch sw = new StopWatch();
        sw.start();
        int num = 0;
        for (String word : this.processor.fileFetch("sports.txt"))
        {
            System.out.println(++num + "\t" + word);
        }
        sw.stop();
        System.out.println("��ʱ��" + sw.getTime() + "ms");
    }

    public final void estTextFetch()
    {
        List<String> al = processor
                .textFetch("��ͷ��ѧ��1981�꾭����Ժ��׼�����Ĺ㶫ʡ���ۺϴ�ѧ��ѧУ�Ľ����뷢չһֱ�õ����롢�㶫ʡ����ͷ�еĴ���֧�֣��õ�����������ʿ��������ҵ����γ������Ķ����������������ۼ�ΪѧУ���ʼ���23�ڸ�Ԫ��ѧУ�����ڷ羰�����ĺ���������ͷ�ı�����ռ�����1928.85Ķ�����������40.60��ƽ���ס�У԰��ɽ��ˮ������������ţ�����Ϊ����У����֮�������Ǵ�ķ�չ�ܵ������쵼�����йػ���������ͬ־���������Ǵ��Ӳ죬�����������Ϊ�Ǵ��ѧ�������ʵ���γ�����ʱʱ�����Ǵ�Ϊ�Ǵ�ķ�չ�����˴�����ʱ�����Ѫ���Ǵ��˲������롢ʡ���и����쵼����γ�����������������ʮ�����Ŭ�����ѵ춨�˳�����չ�����û�����Ӫ����һ�������������˲ŵ����û�����");
        for (String s : al)
            System.out.println(s);
    }

    public final void estBuildDictionary() throws IOException
    {
        long start = System.currentTimeMillis();
        File file = new File("E:\\��������\\�ı�\\small\\");
        TreeMap<String, Integer> result = null;
        result = processor.buildDictionary(file, result);
        long end = System.currentTimeMillis();
        System.out.println("������ʱ��" + (end - start));

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
                "dic/analyse.txt")));
        for (String word : result.keySet())
        {
            out.println(word + "\t\t" + result.get(word));
        }
        out.flush();
        out.close();
    }

}
