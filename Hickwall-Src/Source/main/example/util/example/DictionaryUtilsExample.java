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
package util.example;

import com.novse.segmentation.core.io.FileResource;
import com.novse.segmentation.core.io.Resource;
import com.novse.segmentation.core.matching.dictionary.Dictionary;
import com.novse.segmentation.util.DictionaryUtils;

/**
 * @author Mac Kwan 词典工具类，用于序列化与反序列化词典实例
 */
public class DictionaryUtilsExample
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 文本资源
        Resource dicResource = new FileResource("Dic/Txt/SmallDic.txt");

        // 词典接口
        Dictionary dic = DictionaryUtils.createSimpleDictionary(dicResource);

        if (dic.match("中国"))
            System.out.print("pass...");
        else
            System.out.println("fail...");
    }

}
