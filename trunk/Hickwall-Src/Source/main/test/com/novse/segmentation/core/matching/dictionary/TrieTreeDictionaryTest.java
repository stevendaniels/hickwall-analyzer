package com.novse.segmentation.core.matching.dictionary;

/**
 * ”√”⁄≤‚ ‘TrieTreeDictionary
 * 
 * @author gbu
 *
 */
public class TrieTreeDictionaryTest extends DictionaryTest {

	@Override
	protected Dictionary getDictionary() {
		return new TrieTreeDictionary();
	}

}
