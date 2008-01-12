/*
 * @����:Mac Kwan , ��������:2007-12-28
 *
 * ��ͷ��ѧ03���������
 * 
 */
package com.novse.segmentation.core.matching.dictionary.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.novse.segmentation.core.io.Resource;

/**
 * @author Mac Kwan TXT�ı���ʽ�Ĵʵ���Դ������
 */
public class TextDictionaryLoader extends AbstractDictionaryLoader
{

    /**
     * �Ӵʵ���Դ�г�ȡ��ôʻ��б�
     * 
     * @param dicResource
     *            �ʵ���Դ
     * @return �ʻ��б�
     */
    @Override
    protected List<String> extract(Resource dicResource)
    {
        // ��ʼ���������
        List<String> result = new ArrayList<String>();

        try
        {
            // ��ʼ���Ķ���
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    dicResource.getInputStream()));
            // �����ı�
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                result.add(line.trim());
            }
            // �ر�������
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // ���ؽ��
        return result;
    }
}
