/*
 * @����:Mac Kwan , ��������:2007-12-22
 *
 * ��ͷ��ѧ03���������
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
    private static final String STR = "����";

    // ��Դ
    private Resource resource;

    // ��ȡ��Դ
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
        // ��ȡ����
        line = bufferReader.readLine();
        assertNotNull(line);
        assertEquals(line, STR);

        bufferReader.close();
    }
}
