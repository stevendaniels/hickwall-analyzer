package com.novse.segmentation.core.matching.dictionary;

/**
 * ���ڲ���HashDictionary.
 * 
 * @author gbu
 *
 */
public class HashDictionaryTest extends DictionaryTest {

	@Override
	protected Dictionary getDictionary() {
		return new HashDictionary();
	}

}
