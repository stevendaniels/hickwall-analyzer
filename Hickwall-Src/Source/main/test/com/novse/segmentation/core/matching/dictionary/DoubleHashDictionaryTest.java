package com.novse.segmentation.core.matching.dictionary;

/**
 * ���ڲ���DoubleHashDictionary
 * 
 * @author gbu
 * 
 */
public class DoubleHashDictionaryTest extends DictionaryTest
{

    @Override
    protected Dictionary getDictionary()
    {
        return new DoubleHashDictionary();
    }

}
