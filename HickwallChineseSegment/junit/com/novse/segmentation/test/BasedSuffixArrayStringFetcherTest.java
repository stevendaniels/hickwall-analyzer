/*
 * @作者:Hades , 创建日期:2006-11-15
 *
 * 汕头大学03计算机本科
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
        System.out.println("耗时：" + sw.getTime() + "ms");
    }

    public final void estTextFetch()
    {
        List<String> al = processor
                .textFetch("汕头大学是1981年经国务院批准成立的广东省属综合大学，学校的建设与发展一直得到中央、广东省和汕头市的大力支持，得到著名爱国人士及国际企业家李嘉诚先生的鼎力资助。李先生累计为学校捐资己逾23亿港元。学校座落在风景秀丽的海滨城市汕头的北区。占地面积1928.85亩，建筑总面积40.60万平方米。校园依山傍水，建筑风格优雅，被誉为“高校建筑之花”。汕大的发展受到中央领导的亲切关怀，江泽民同志两次亲临汕大视察，并给予鼓励。为汕大办学捐赠巨资的李嘉诚先生时时关心汕大，为汕大的发展付出了大量的时间和心血。汕大人不负中央、省、市各级领导和李嘉诚先生所望，经过二十多年的努力，已奠定了持续发展的良好基础，营造了一个培养跨世纪人才的良好环境。");
        for (String s : al)
            System.out.println(s);
    }

    public final void estBuildDictionary() throws IOException
    {
        long start = System.currentTimeMillis();
        File file = new File("E:\\工作猎手\\文本\\small\\");
        TreeMap<String, Integer> result = null;
        result = processor.buildDictionary(file, result);
        long end = System.currentTimeMillis();
        System.out.println("分析耗时：" + (end - start));

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
