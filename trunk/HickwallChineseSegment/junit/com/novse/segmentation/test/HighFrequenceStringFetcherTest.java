/*
 * @作者:Hades , 创建日期:2006-12-29
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

import junit.framework.TestCase;

import org.apache.commons.lang.time.StopWatch;

import com.novse.segmentation.core.statistic.basic.SetpointPostProcessor;
import com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher;

/**
 * @author Mac Kwan
 * 
 */
public class HighFrequenceStringFetcherTest extends TestCase
{
    private BasicStatisticStringFetcher fetcher = null;

    /*
     * （非 Javadoc）
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        this.fetcher = new BasicStatisticStringFetcher(
                new SetpointPostProcessor());
    }

    /**
     * Test method for
     * {@link com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher#fileFetch(java.lang.String)}.
     * 
     * @throws Exception
     */
    public final void testFileFetch() throws Exception
    {
        StopWatch sw = new StopWatch();
        sw.start();
        int num = 0;
        for (String word : this.fetcher.fileFetch("sports.txt"))
        {
            System.out.println(++num + "\t" + word);
        }
        sw.stop();
        System.out.println("耗时：" + sw.getTime() + "ms");
    }

    /**
     * Test method for
     * {@link com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher#textFetch(java.lang.String)}.
     * 
     * @throws Exception
     */
    public final void estTextFetch() throws Exception
    {
        for (String word : this.fetcher.textFetch("小城的故事。小城故事"))
        {
            System.out.println(word);
        }
    }

}
