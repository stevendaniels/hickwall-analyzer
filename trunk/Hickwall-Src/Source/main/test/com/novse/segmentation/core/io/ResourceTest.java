/*
 * @作者:Mac Kwan , 创建日期:2007-12-22
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;

/**
 * @author Mac Kwan
 * 
 */
public abstract class ResourceTest extends TestCase
{
    private static final String STR = "测试";

    // 资源
    private Resource resource;

    // 获取资源
    abstract protected Resource getResource();

    @Override
    protected void setUp() throws Exception
    {
        this.resource = this.getResource();
    }

    public void testGetReader() throws Exception
    {
        Reader reader = new InputStreamReader(this.resource.getInputStream());
        assertNotNull(reader);

        String line = null;
        BufferedReader bufferReader;
        if (reader instanceof BufferedReader)
            bufferReader = (BufferedReader) reader;
        else
            bufferReader = new BufferedReader(reader);
        // 读取数据
        line = bufferReader.readLine();
        assertNotNull(line);
        assertEquals(line, STR);

        bufferReader.close();
    }
}
