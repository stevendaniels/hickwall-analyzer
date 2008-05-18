/*
 * @����:Hades , ��������:2007-1-25
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.lucene.analysis.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

import com.novse.segmentation.core.SegmentProcessor;

/**
 * @author Mac Kwan
 * 
 */
public class HickwallIndexTokenizer extends Tokenizer
{
    /**
     * �ַ����������С
     */
    private static final int CHAR_BUFFER_SIZE = 512;

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
    public HickwallIndexTokenizer(SegmentProcessor processor, Reader reader)
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
        Reader bufferReader = null;
        if (this.input instanceof BufferedReader)
            bufferReader = this.input;
        else
            bufferReader = new BufferedReader(this.input);
        // ��������
        char[] cbuffer = new char[CHAR_BUFFER_SIZE];
        int pos = -1;
        StringBuffer buffer = new StringBuffer();
        while ((pos = bufferReader.read(cbuffer)) >= 0)
        {
            buffer.append(cbuffer, 0, pos);
        }
        // �ر�������
        this.close();
        // ִ�зִʴ���
        List<String> strList = this.processor.textProcess(buffer.toString());

        // �����ִʽ������Token����
        for (int i = 0; i < strList.size(); i++)
        {
            // ��ȡ����
            String word = strList.get(i);

            // ����Token
            Token token = new Token(word, posIndex, posIndex + word.length());
            tokenList.add(token);

            // ������з�Ϊ�������ڽ�����
            if (word.length() > 1)
            {
                for (int j = 0; j < word.length(); j++)
                {
                    // ���ɵ���Token
                    Token singleToken = new Token(word.substring(j, j + 1),
                            posIndex + j, posIndex + j + 1);
                    singleToken.setPositionIncrement(0);
                    tokenList.add(singleToken);
                }
            }

            // �޸��ַ��α�λ��
            posIndex += word.length();
        }
        // ����Token���н��
        return tokenList;
    }

}
