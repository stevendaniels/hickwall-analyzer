/*
 * @作者:Hades , 创建日期:2006-11-15
 *
 * 汕头大学03计算机本科
 * 
 */
package com.novse.segmentation.core.statistic.fetcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.novse.segmentation.core.statistic.suffix.SimpleSuffixArray;
import com.novse.segmentation.core.statistic.suffix.SuffixArrayImpl;

/**
 * @author Mac Kwan 基于后缀数组的无词典分词操作器
 */
public class BasedSuffixArrayStringFetcher extends AbstractStringFetcher
{
    /**
     * @author Mac Kwan 内部类，用于记录词组实例
     */
    static public class WordInfo
    {
        private int frequence = 0;

        private boolean need = true;

        private String word = null;

        public WordInfo()
        {
            this.need = true;
        }

        public WordInfo(String word, int frequence)
        {
            this.word = word;
            this.frequence = frequence;
            this.need = true;
        }

        /**
         * @return 返回 frequence。
         */
        public int getFrequence()
        {
            return frequence;
        }

        /**
         * @return 返回 word。
         */
        public String getWord()
        {
            return word;
        }

        /**
         * @return 返回 need。
         */
        public boolean isNeed()
        {
            return need;
        }

        /**
         * @param frequence
         *            要设置的 frequence。
         */
        public void setFrequence(int frequence)
        {
            this.frequence = frequence;
        }

        /**
         * @param need
         *            要设置的 need。
         */
        public void setNeed(boolean need)
        {
            this.need = need;
        }

        /**
         * @param word
         *            要设置的 word。
         */
        public void setWord(String word)
        {
            this.word = word;
        }

    }

    /**
     * @author Mac Kwan 内部类，配合WordInfo类使用的基于词长的比较器
     */
    static public class WordInfoLenComparator implements Comparator<WordInfo>
    {
        /*
         * （非 Javadoc）
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(WordInfo o1, WordInfo o2)
        {
            int l1 = o1.getWord().length(), l2 = o2.getWord().length();
            int result = 0;
            if (l1 > l2)
                result = -1;
            else if (l1 < l2)
                result = 1;
            return result;
        }
    }

    /**
     * 记录候选词的哈希表
     */
    private HashMap<String, Integer> map = null;

    /**
     * 置信度上限
     */
    private double maxConfidence = 0.9;

    /**
     * 置信度下限
     */
    private double minConfidence = 0.1;

    /**
     * 计算后缀数组的工具
     */
    private SuffixArrayImpl suffixTool = null;

    /**
     * 对待分词文档进行基于后缀数组无词典分词算法求解后获得词组
     */
    private WordInfo[] wordsArray = null;

    /**
     * 默认构造函数
     */
    public BasedSuffixArrayStringFetcher()
    {
        this.initSeperator();
    }

    /**
     * 自定义置信度的构造函数
     * 
     * @param maxConfidence
     *            置信度上限
     * @param minConfidence
     *            置信度下限
     */
    public BasedSuffixArrayStringFetcher(double maxConfidence,
            double minConfidence)
    {
        this.initSeperator();
        this.maxConfidence = maxConfidence;
        this.minConfidence = minConfidence;
    }

    /**
     * 自定义分隔符的构造函数
     * 
     * @param seperator
     *            自定义分隔符
     */
    public BasedSuffixArrayStringFetcher(String seperator)
    {
        this.seperator = seperator;
    }

    /**
     * 自定义分隔符与置信度构造函数
     * 
     * @param seperator
     *            自定义分隔符
     * @param maxConfidence
     *            置信度上限
     * @param minConfidence
     *            置信度下限
     */
    public BasedSuffixArrayStringFetcher(String seperator,
            double maxConfidence, double minConfidence)
    {
        this.seperator = seperator;
        this.maxConfidence = maxConfidence;
        this.minConfidence = minConfidence;
    }

    /**
     * 递归遍历fileName文件夹后建立统计词库
     * 
     * @param file
     *            待递归分析的目录
     * @param result
     *            词典结果保存容器
     * @return 词典
     * @throws IOException
     */
    public TreeMap<String, Integer> buildDictionary(File file,
            TreeMap<String, Integer> result) throws IOException
    {
        if (result == null)
            result = new TreeMap<String, Integer>();

        // 判断待分析的目录是否存在
        if (!file.exists())
            return null;

        // 当前文件是否为目录文件
        if (file.isDirectory())
        {
            // 遍历目录下所有文件
            for (File f : file.listFiles())
            {
                result = this.buildDictionary(f, result);
            }
            return result;
        }
        else if (file.getName().endsWith(".txt")) // 判断是否为txt文本
        {
            // 初始化阅读器
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // 预统计得到高频词汇
            this.getWords(reader);
            // 关闭输入
            reader.close();
            // 筛选预统计结果
            this.postProcess();

            if (this.wordsArray == null)
                return result;
            else
            {
                for (WordInfo w : this.wordsArray)
                {
                    if (w.isNeed())
                    {
                        // 判断结果集中是否已包括当前词汇
                        if (result.containsKey(w.getWord()))
                            result.put(w.getWord(), result.get(w.getWord())
                                    + w.getFrequence());
                        else
                            result.put(w.getWord(), w.getFrequence());
                    }
                }
                return result;
            }
        }
        else
            return result;
    }

    /**
     * 从待处理文件srcFile中抽取词汇
     * 
     * @param srcFile
     *            待处理文件
     * @return 抽取所得词汇
     */
    public List<String> fileFetch(String srcFile)
    {
        ArrayList<String> result = null;
        try
        {
            // 预统计得到高频词汇
            this.getWords(new BufferedReader(new FileReader(srcFile)));
            // 筛选预统计结果
            this.postProcess();

            if (this.wordsArray == null)
                return result;
            else
            {
                result = new ArrayList<String>();
                for (WordInfo w : this.wordsArray)
                {
                    if (w.isNeed())
                        result.add(w.getWord());
                }
                return result;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 对输入的待分词阅读器进行分词
     * 
     * @param in
     *            待分词的阅读器
     */
    protected void getWords(BufferedReader in)
    {
        try
        {
            // 初始化变量
            StringBuffer buffer = new StringBuffer();
            String line = null;

            // 从阅读器中读入内容
            while ((line = in.readLine()) != null)
            {
                buffer.append(line);
            }
            // 关闭输入
            in.close();
            // 分词处理
            this.getWords(buffer.toString().trim());
        }
        catch (IOException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 对输入的待分词的文档进行分词
     * 
     * @param sentence
     *            待分词文档
     */
    protected void getWords(String doc)
    {
        this.map = new HashMap<String, Integer>();
        // 定义分隔器
        StringTokenizer tokenizer = new StringTokenizer(doc, this.seperator);
        // 将文档按照分隔符分隔成为短句后进行分词操作
        while (tokenizer.hasMoreTokens())
        {
            this.getWordsByToken(tokenizer.nextToken().trim());
        }
    }

    /**
     * 对输入的全汉语短句进行分词
     * 
     * @param sentence
     *            全汉语短句
     */
    protected void getWordsByToken(String sentence)
    {
        if (this.map != null)
        {
            // 初始化计算后缀数组的操作类实例
            this.suffixTool = new SimpleSuffixArray(sentence);

            int[] s = this.suffixTool.getSuffixArray(), lcp = this.suffixTool
                    .getLCP();
            int i = 0, n = s.length;

            while (i < n)
            {
                String str1 = sentence.substring(s[i], s[i] + lcp[i]);
                int j = 2;
                while (j <= str1.length())
                {
                    String str2 = str1.substring(0, j);
                    if (this.map.containsKey(str2))
                        map.put(str2, map.get(str2) + 1);
                    else
                        map.put(str2, 1);
                    j++;
                }
                i++;
            }
        }
    }

    /**
     * 对预分词得到的候选词进行筛选
     * 
     */
    protected void postProcess()
    {
        if (this.map != null)
        {
            // 将以HashMap存储的候选词转为以数组存储
            this.wordsArray = new WordInfo[this.map.size()];
            int i = 0;
            for (String key : this.map.keySet())
            {
                wordsArray[i] = new WordInfo(key, this.map.get(key).intValue());
                i++;
            }

            // 按词组长度从大到小排序
            Arrays.sort(wordsArray, new WordInfoLenComparator());

            int n = wordsArray.length;
            i = 0;
            while (i < n - 1)
            {
                // 去除“的”“了”“地”“着”开头的词汇
                if (wordsArray[i].getWord().indexOf("的") == 0
                        || wordsArray[i].getWord().indexOf("了") == 0
                        || wordsArray[i].getWord().indexOf("地") == 0
                        || wordsArray[i].getWord().indexOf("着") == 0)
                {
                    wordsArray[i].setNeed(false);
                    i++;
                    continue;
                }
                int j = n - 1, len = wordsArray[i].getWord().length();
                while (wordsArray[j].getWord().length() < len)
                {
                    // wordsArray[j]代表的词组是否是wordsArray[i]的子串
                    if (wordsArray[i].getWord().lastIndexOf(
                            wordsArray[j].getWord()) >= 0)
                    {
                        // 汉字串i相对于字符串j的置信度
                        double confidence = (double) wordsArray[i]
                                .getFrequence()
                                / (double) wordsArray[j].getFrequence();
                        if (confidence < this.minConfidence)
                            wordsArray[i].setNeed(false);
                        if (confidence > this.maxConfidence)
                            wordsArray[j].setNeed(false);
                    }
                    j--;
                }
                i++;
            }
        }
    }

    /**
     * @param maxConfidence
     *            要设置的 maxConfidence。
     */
    public void setMaxConfidence(double maxConfidence)
    {
        this.maxConfidence = maxConfidence;
    }

    /**
     * @param minConfidence
     *            要设置的 minConfidence。
     */
    public void setMinConfidence(double minConfidence)
    {
        this.minConfidence = minConfidence;
    }

    /**
     * 从待处理字符串doc中抽取词汇
     * 
     * @param doc
     *            待处理字符串
     * @return 抽取所得词汇
     */
    public List<String> textFetch(String doc)
    {
        // 预统计得到高频词汇
        this.getWords(doc);
        // 筛选预统计结果
        this.postProcess();
        if (this.wordsArray == null)
            return null;
        else
        {
            ArrayList<String> result = new ArrayList<String>();
            for (WordInfo w : this.wordsArray)
            {
                if (w.isNeed())
                    result.add(w.getWord());
            }
            // 结果排序
            Collections.sort(result);
            return result;
        }
    }
}
