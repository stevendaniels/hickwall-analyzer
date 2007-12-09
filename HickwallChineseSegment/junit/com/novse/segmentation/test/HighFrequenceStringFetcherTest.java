/*
 * @����:Hades , ��������:2006-12-29
 *
 * ��ͷ��ѧ03���������
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
     * ���� Javadoc��
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
        System.out.println("��ʱ��" + sw.getTime() + "ms");
    }

    /**
     * Test method for
     * {@link com.novse.segmentation.core.statistic.fetcher.BasicStatisticStringFetcher#textFetch(java.lang.String)}.
     * 
     * @throws Exception
     */
    public final void estTextFetch() throws Exception
    {
        for (String word : this.fetcher.textFetch("С�ǵĹ��¡�С�ǹ���"))
        {
            System.out.println(word);
        }
    }

}
