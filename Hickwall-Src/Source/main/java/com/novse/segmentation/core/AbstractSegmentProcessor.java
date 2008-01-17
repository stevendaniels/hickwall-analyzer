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
package com.novse.segmentation.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.statistic.fetcher.StringFetcher;
import com.novse.segmentation.lucene.analysis.StopWordMaker;

/**
 * ���ڴʵ�ƥ������ķִʳ�����
 * 
 * @author Mac Kwan
 */
public abstract class AbstractSegmentProcessor implements SegmentProcessor
{
    /**
     * �ʵ������
     * 
     */
    protected Dictionary dic = null;

    /**
     * �ʻ��ȡ��
     */
    protected StringFetcher fetcher = null;

    /**
     * �ָ����ַ���
     */
    protected String seperator = null;

    /**
     * ��srcFile�ļ����зִʣ��ѽ������Ϊ��tagFile�ļ���
     * 
     * @param srcFile
     *            ���ִʵ��ı��ļ�
     * @param tagFile
     *            �ִʽ������Ŀ���ļ�
     */
    public void fileProcessor(String srcFile, String tagFile)
    {
        try
        {
            // ��ʼ���������
            BufferedReader in = new BufferedReader(new FileReader(srcFile));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(tagFile)));

            // �����ļ�
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null)
            {
                buffer.append(line);
                buffer.append(System.getProperties().getProperty(
                        "line.separator"));
            }
            // �ر�����
            in.close();

            // �ִʴ���
            List<String> result = this.textProcess(buffer.toString()
                    .toLowerCase());

            // �����д���ļ�
            for (String w : result)
                out.print(w + " ");
            // �ر����
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return ���� dic��
     */
    public Dictionary getDic()
    {
        return dic;
    }

    /**
     * @return ���� fetcher��
     */
    public StringFetcher getFetcher()
    {
        return fetcher;
    }

    /**
     * ��ʼ���ָ����ķ���
     */
    protected void initSeperator()
    {
        this.seperator = StopWordMaker.retreiveString();
    }

    /**
     * @param dic
     *            Ҫ���õ� dic��
     */
    public void setDic(Dictionary dic)
    {
        this.dic = dic;
    }

    /**
     * @param fetcher
     *            Ҫ���õ� fetcher��
     */
    public void setFetcher(StringFetcher fetcher)
    {
        this.fetcher = fetcher;
    }

    /**
     * ��text�ı����зִʣ��ѽ������Ϊ�ַ�������
     * 
     * @param text
     *            ���ִʵ��ı�
     * @return �ִʽ��
     */
    abstract public List<String> textProcess(String text);

    /**
     * ת��ǵĺ���(DBC case)
     * 
     * @param str
     *            ��ת�����ַ���
     * @return ת��Ϊ��Ǻ���ַ���
     */
    protected String toDBC(String str)
    {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 12288)
            {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        str = null;
        return new String(c);
    }
}
