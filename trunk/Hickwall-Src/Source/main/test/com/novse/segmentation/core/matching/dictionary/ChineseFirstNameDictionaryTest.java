package com.novse.segmentation.core.matching.dictionary;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;

/**
 * ”√”⁄≤‚ ‘ChineseFirstNameDictionary
 * 
 * @author gbu
 *
 */
public class ChineseFirstNameDictionaryTest extends DictionaryTest {

	@Override
	protected Dictionary getDictionary() {
		return new ChineseFirstNameDictionary();
	}

	
}
