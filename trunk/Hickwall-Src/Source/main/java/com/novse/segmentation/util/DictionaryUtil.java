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
package com.novse.segmentation.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.novse.segmentation.core.Loadable;

/**
 * @author Mac Kwan 词典工具类，用于序列化与反序列化词典实例
 * 
 */
public class DictionaryUtil<T extends Loadable>
{
    /**
     * 从fileName文件中读入词典实例
     * 
     * @param fileName
     *            存储文件
     * @return 词典实例
     */
    @SuppressWarnings("unchecked")
    public T readDictionary(String fileName)
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(fileName)));
            T dic = (T) in.readObject();
            in.close();
            return dic;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * 将词典实例dic写入到fileName文件中
     * 
     * @param dic
     *            词典实例
     * @param fileName
     *            存储文件
     * @return 操作成功与否
     */
    public boolean writeDictionary(T dic, String fileName)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(fileName)));
            out.writeObject(dic);
            out.flush();
            out.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
