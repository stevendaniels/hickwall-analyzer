package com.novse.segmentation.core.matching.dictionary;

import junit.framework.TestCase;

/**
 * DictionaryTest用于测试所有的词典类。DictionaryTest是虚类，在具体的测试用例类里必须实现
 * {@link #getDictionary()}方法取得实际被测试的词典类。
 * 
 * @author gbu
 *
 */
public abstract class DictionaryTest extends TestCase {

	//用于测试的空白字符
	//TODO 修改空白字符
	private String[] blankWords ={"", "  ", "\n"}; 
	
	//需要测试的Dictionary
	protected Dictionary dictionary = null;
	
	protected void setUp() throws Exception {
		dictionary = this.getDictionary();
	}

	protected void tearDown() throws Exception {
		dictionary = null;
	}
	
	/**
	 * 取得具体的Dictionary，用于测试。
	 * 
	 * @return
	 * 需测试的Dictionary
	 */
	protected abstract Dictionary getDictionary();
	
	public void testInsertNll(){
		this.dictionary.insertWord((String)null);
		assertFalse(this.dictionary.match(null));
	}
	
	public void testInsertBlankWord(){
		for(String word : blankWords){
			this.dictionary.insertWord(word);
		}
		for(String word : blankWords){
			assertFalse(this.dictionary.match(word));
		}
	}
	
	public void testInsertSameWord(){
		String same = "相同";
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.deleteWord(same);
		assertFalse(this.dictionary.match(same));
	}
	
	
}
