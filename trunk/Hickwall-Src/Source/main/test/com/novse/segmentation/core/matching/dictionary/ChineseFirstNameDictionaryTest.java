package com.novse.segmentation.core.matching.dictionary;

import com.novse.segmentation.core.unlistedword.dictionary.ChineseFirstNameDictionary;

/**
 * ”√”ŕ≤‚ ‘ChineseFirstNameDictionary
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
