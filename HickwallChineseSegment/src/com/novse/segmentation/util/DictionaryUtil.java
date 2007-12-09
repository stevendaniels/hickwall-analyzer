/*
 * @作者:Hades , 创建日期:2006-11-18
 *
 * 汕头大学03计算机本科
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
