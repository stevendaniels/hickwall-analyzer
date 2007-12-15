/*
 * @����:Hades , ��������:2006-11-15
 *
 * ��ͷ��ѧ03���������
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
 * @author Mac Kwan ���ں�׺������޴ʵ�ִʲ�����
 */
public class BasedSuffixArrayStringFetcher extends AbstractStringFetcher
{
    /**
     * @author Mac Kwan �ڲ��࣬���ڼ�¼����ʵ��
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
         * @return ���� frequence��
         */
        public int getFrequence()
        {
            return frequence;
        }

        /**
         * @return ���� word��
         */
        public String getWord()
        {
            return word;
        }

        /**
         * @return ���� need��
         */
        public boolean isNeed()
        {
            return need;
        }

        /**
         * @param frequence
         *            Ҫ���õ� frequence��
         */
        public void setFrequence(int frequence)
        {
            this.frequence = frequence;
        }

        /**
         * @param need
         *            Ҫ���õ� need��
         */
        public void setNeed(boolean need)
        {
            this.need = need;
        }

        /**
         * @param word
         *            Ҫ���õ� word��
         */
        public void setWord(String word)
        {
            this.word = word;
        }

    }

    /**
     * @author Mac Kwan �ڲ��࣬���WordInfo��ʹ�õĻ��ڴʳ��ıȽ���
     */
    static public class WordInfoLenComparator implements Comparator<WordInfo>
    {
        /*
         * ���� Javadoc��
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
     * ��¼��ѡ�ʵĹ�ϣ��
     */
    private HashMap<String, Integer> map = null;

    /**
     * ���Ŷ�����
     */
    private double maxConfidence = 0.9;

    /**
     * ���Ŷ�����
     */
    private double minConfidence = 0.1;

    /**
     * �����׺����Ĺ���
     */
    private SuffixArrayImpl suffixTool = null;

    /**
     * �Դ��ִ��ĵ����л��ں�׺�����޴ʵ�ִ��㷨�����ô���
     */
    private WordInfo[] wordsArray = null;

    /**
     * Ĭ�Ϲ��캯��
     */
    public BasedSuffixArrayStringFetcher()
    {
        this.initSeperator();
    }

    /**
     * �Զ������ŶȵĹ��캯��
     * 
     * @param maxConfidence
     *            ���Ŷ�����
     * @param minConfidence
     *            ���Ŷ�����
     */
    public BasedSuffixArrayStringFetcher(double maxConfidence,
            double minConfidence)
    {
        this.initSeperator();
        this.maxConfidence = maxConfidence;
        this.minConfidence = minConfidence;
    }

    /**
     * �Զ���ָ����Ĺ��캯��
     * 
     * @param seperator
     *            �Զ���ָ���
     */
    public BasedSuffixArrayStringFetcher(String seperator)
    {
        this.seperator = seperator;
    }

    /**
     * �Զ���ָ��������Ŷȹ��캯��
     * 
     * @param seperator
     *            �Զ���ָ���
     * @param maxConfidence
     *            ���Ŷ�����
     * @param minConfidence
     *            ���Ŷ�����
     */
    public BasedSuffixArrayStringFetcher(String seperator,
            double maxConfidence, double minConfidence)
    {
        this.seperator = seperator;
        this.maxConfidence = maxConfidence;
        this.minConfidence = minConfidence;
    }

    /**
     * �ݹ����fileName�ļ��к���ͳ�ƴʿ�
     * 
     * @param file
     *            ���ݹ������Ŀ¼
     * @param result
     *            �ʵ�����������
     * @return �ʵ�
     * @throws IOException
     */
    public TreeMap<String, Integer> buildDictionary(File file,
            TreeMap<String, Integer> result) throws IOException
    {
        if (result == null)
            result = new TreeMap<String, Integer>();

        // �жϴ�������Ŀ¼�Ƿ����
        if (!file.exists())
            return null;

        // ��ǰ�ļ��Ƿ�ΪĿ¼�ļ�
        if (file.isDirectory())
        {
            // ����Ŀ¼�������ļ�
            for (File f : file.listFiles())
            {
                result = this.buildDictionary(f, result);
            }
            return result;
        }
        else if (file.getName().endsWith(".txt")) // �ж��Ƿ�Ϊtxt�ı�
        {
            // ��ʼ���Ķ���
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Ԥͳ�Ƶõ���Ƶ�ʻ�
            this.getWords(reader);
            // �ر�����
            reader.close();
            // ɸѡԤͳ�ƽ��
            this.postProcess();

            if (this.wordsArray == null)
                return result;
            else
            {
                for (WordInfo w : this.wordsArray)
                {
                    if (w.isNeed())
                    {
                        // �жϽ�������Ƿ��Ѱ�����ǰ�ʻ�
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
     * �Ӵ������ļ�srcFile�г�ȡ�ʻ�
     * 
     * @param srcFile
     *            �������ļ�
     * @return ��ȡ���ôʻ�
     */
    public List<String> fileFetch(String srcFile)
    {
        ArrayList<String> result = null;
        try
        {
            // Ԥͳ�Ƶõ���Ƶ�ʻ�
            this.getWords(new BufferedReader(new FileReader(srcFile)));
            // ɸѡԤͳ�ƽ��
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
     * ������Ĵ��ִ��Ķ������зִ�
     * 
     * @param in
     *            ���ִʵ��Ķ���
     */
    protected void getWords(BufferedReader in)
    {
        try
        {
            // ��ʼ������
            StringBuffer buffer = new StringBuffer();
            String line = null;

            // ���Ķ����ж�������
            while ((line = in.readLine()) != null)
            {
                buffer.append(line);
            }
            // �ر�����
            in.close();
            // �ִʴ���
            this.getWords(buffer.toString().trim());
        }
        catch (IOException e)
        {
            // TODO �Զ����� catch ��
            e.printStackTrace();
        }
    }

    /**
     * ������Ĵ��ִʵ��ĵ����зִ�
     * 
     * @param sentence
     *            ���ִ��ĵ�
     */
    protected void getWords(String doc)
    {
        this.map = new HashMap<String, Integer>();
        // ����ָ���
        StringTokenizer tokenizer = new StringTokenizer(doc, this.seperator);
        // ���ĵ����շָ����ָ���Ϊ�̾����зִʲ���
        while (tokenizer.hasMoreTokens())
        {
            this.getWordsByToken(tokenizer.nextToken().trim());
        }
    }

    /**
     * �������ȫ����̾���зִ�
     * 
     * @param sentence
     *            ȫ����̾�
     */
    protected void getWordsByToken(String sentence)
    {
        if (this.map != null)
        {
            // ��ʼ�������׺����Ĳ�����ʵ��
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
     * ��Ԥ�ִʵõ��ĺ�ѡ�ʽ���ɸѡ
     * 
     */
    protected void postProcess()
    {
        if (this.map != null)
        {
            // ����HashMap�洢�ĺ�ѡ��תΪ������洢
            this.wordsArray = new WordInfo[this.map.size()];
            int i = 0;
            for (String key : this.map.keySet())
            {
                wordsArray[i] = new WordInfo(key, this.map.get(key).intValue());
                i++;
            }

            // �����鳤�ȴӴ�С����
            Arrays.sort(wordsArray, new WordInfoLenComparator());

            int n = wordsArray.length;
            i = 0;
            while (i < n - 1)
            {
                // ȥ�����ġ����ˡ����ء����š���ͷ�Ĵʻ�
                if (wordsArray[i].getWord().indexOf("��") == 0
                        || wordsArray[i].getWord().indexOf("��") == 0
                        || wordsArray[i].getWord().indexOf("��") == 0
                        || wordsArray[i].getWord().indexOf("��") == 0)
                {
                    wordsArray[i].setNeed(false);
                    i++;
                    continue;
                }
                int j = n - 1, len = wordsArray[i].getWord().length();
                while (wordsArray[j].getWord().length() < len)
                {
                    // wordsArray[j]����Ĵ����Ƿ���wordsArray[i]���Ӵ�
                    if (wordsArray[i].getWord().lastIndexOf(
                            wordsArray[j].getWord()) >= 0)
                    {
                        // ���ִ�i������ַ���j�����Ŷ�
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
     *            Ҫ���õ� maxConfidence��
     */
    public void setMaxConfidence(double maxConfidence)
    {
        this.maxConfidence = maxConfidence;
    }

    /**
     * @param minConfidence
     *            Ҫ���õ� minConfidence��
     */
    public void setMinConfidence(double minConfidence)
    {
        this.minConfidence = minConfidence;
    }

    /**
     * �Ӵ������ַ���doc�г�ȡ�ʻ�
     * 
     * @param doc
     *            �������ַ���
     * @return ��ȡ���ôʻ�
     */
    public List<String> textFetch(String doc)
    {
        // Ԥͳ�Ƶõ���Ƶ�ʻ�
        this.getWords(doc);
        // ɸѡԤͳ�ƽ��
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
            // �������
            Collections.sort(result);
            return result;
        }
    }
}
