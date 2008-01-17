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
package core.unlistedword.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.core.unlistedword.dictionary.ChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.dictionary.NotChineseNameDictionary;
import com.novse.segmentation.core.unlistedword.name.SimpleChineseNameAnalyzer;
import com.novse.segmentation.util.DictionaryUtils;

/**
 * @author Mac Kwan ��������ʶ�������
 */
public class SimpleChineseNameAnalyzerExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // �ı���Դ
        Resource dicResource = new FileResource("Dic/Txt/FirstName.txt");

        // δ��¼��ʶ��

        // ����������Դ
        Resource firstNameDicResource = new FileResource(
                "Dic/Txt/FirstName.txt");
        // ����������Դ
        Resource chineseNameDicResource = new FileResource("Dic/Txt/Name.txt");
        // α����������Դ
        Resource notChineseNameDicResource = new FileResource(
                "Dic/Txt/UnName.txt");

        // ���������ʵ�
        ChineseNameDictionary nameDic = DictionaryUtils
                .createChineseNameDictionary(firstNameDicResource,
                        chineseNameDicResource);

        // ���Ͽ�ͷ�����������Ĵʵ�
        NotChineseNameDictionary notNameDic = DictionaryUtils
                .createNotChineseNameDictionary(firstNameDicResource,
                        notChineseNameDicResource);

        // ��߽�ʴʵ���Դ
        dicResource = new FileResource("Dic/Txt/LeftVerge.txt");
        // ��߽�ʴʵ�
        Dictionary leftVergeDic = DictionaryUtils
                .createHashDictionary(dicResource);

        // �ұ߽�ʴʵ���Դ
        dicResource = new FileResource("Dic/Txt/RightVerge.txt");
        // �ұ߽�ʴʵ�
        Dictionary rightVergeDic = DictionaryUtils
                .createHashDictionary(dicResource);

        // ��������ʶ����
        SimpleChineseNameAnalyzer analyzer = new SimpleChineseNameAnalyzer(
                nameDic, notNameDic, leftVergeDic, rightVergeDic);

        // ���ַ������е��ֲַ�
        String source = "11��29�գ�ԭ����ʦ����ѧУ����ԭ��ͷ��ѧ��ѧ�о����������й���ѧԺԺʿ����������ݰ����У����ͨ���Լ����о�����ָ���о���������������ѧ����������Ϊ���ʦ���о���������Ϊ���о�����ѧ��ʮ�꡷�ı��档";
        String[] array = source.split("");
        List<String> result = new ArrayList<String>(Arrays.asList(array));

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();

        result = analyzer.identify(result);

        for (String s : result)
        {
            System.out.print(s);
            System.out.print(" ");
        }
    }

}
