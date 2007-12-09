/*
 * @作者:Hades , 创建日期:2006-11-19
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.test;

/**
 * @author Mac Kwan
 * 
 */
public class SeperatorTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO 自动生成方法存根
        String charAndNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // 初始化分隔符
        StringBuffer buffer = new StringBuffer();
        for (char c = '\u0000'; c <= '\u007F'; c++)
        {
            if (charAndNum.indexOf(c) < 0)
                buffer.append(c);
        }
        for (char c = '\uFF00'; c <= '\uFFEF'; c++)
            buffer.append(c);
        // buffer.append("《》？，。、：“；‘’”『』【】－―—─＝÷＋§·～！◎＃￥％…※×（） ");
        System.out.println(buffer.toString());
    }

}
