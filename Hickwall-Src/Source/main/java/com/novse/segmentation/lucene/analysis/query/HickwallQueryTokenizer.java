/*
 * @����:Hades , ��������:May 31, 2007
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.lucene.analysis.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

import com.novse.segmentation.core.SegmentProcessor;

/**
 * @author Mac Kwan ���ڲ�ѯ�����ķִ���
 */
public class HickwallQueryTokenizer extends Tokenizer
{
    /**
     * ���Token���е��б�
     */
    private List<Token> list = null;

    /**
     * �зֽ���α��λ��
     */
    private int listIndex = 0;

    /**
     * �ִʹ���
     */
    private SegmentProcessor processor = null;

    /**
     * �Էִʹ�������beanName��һ���Ķ���ReaderΪ��ʼ�������Ĺ��캯��
     * 
     * @param beanName
     *            �ִʹ�������
     * @param reader
     *            �Ķ���
     */
    public HickwallQueryTokenizer(SegmentProcessor processor, Reader reader)
    {
        super(reader);
        this.processor = processor;
        try
        {
            list = this.process();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * ���� Javadoc��
     * 
     * @see org.apache.lucene.analysis.TokenStream#next()
     */
    @Override
    public Token next() throws IOException
    {
        // �ж��α�λ���Ƿ񳬽�
        if (this.listIndex >= this.list.size())
            return null;
        else
            return list.get(listIndex++);
    }

    /**
     * �ִʴ���
     * 
     * @throws IOException
     */
    private List<Token> process() throws IOException
    {
        // ��ʼ���������
        List<Token> tokenList = new ArrayList<Token>();
        // �ַ��α�λ��
        int posIndex = 0;

        // ��ʼ���������
        BufferedReader in = new BufferedReader(this.input);
        // ��������
        String line = null;
        while ((line = in.readLine()) != null)
        {
            // ִ�зִʴ���
            List<String> strList = this.processor.textProcess((line + System
                    .getProperties().getProperty("line.separator"))
                    .toLowerCase());
            // �����ִʽ������Token����
            for (int i = 0; i < strList.size(); i++)
            {
                // ��ȡ����
                String word = strList.get(i);
                // ����Token
                Token token = new Token(word, posIndex, posIndex
                        + word.length());
                // �޸��ַ��α�λ��
                posIndex += word.length();
                tokenList.add(token);
            }

        }
        // �ر�������
        this.close();

        // ����Token���н��
        return tokenList;
    }

}