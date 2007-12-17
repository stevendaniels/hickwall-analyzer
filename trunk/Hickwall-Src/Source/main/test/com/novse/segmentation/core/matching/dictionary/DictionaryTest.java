package com.novse.segmentation.core.matching.dictionary;

import junit.framework.TestCase;

/**
 * DictionaryTest���ڲ������еĴʵ��ࡣDictionaryTest�����࣬�ھ���Ĳ��������������ʵ��
 * {@link #getDictionary()}����ȡ��ʵ�ʱ����ԵĴʵ��ࡣ
 * 
 * @author gbu
 *
 */
public abstract class DictionaryTest extends TestCase {

	//���ڲ��ԵĿհ��ַ�
	//TODO �޸Ŀհ��ַ�
	private String[] blankWords ={"", "  ", "\n"}; 
	
	//��Ҫ���Ե�Dictionary
	protected Dictionary dictionary = null;
	
	protected void setUp() throws Exception {
		dictionary = this.getDictionary();
	}

	protected void tearDown() throws Exception {
		dictionary = null;
	}
	
	/**
	 * ȡ�þ����Dictionary�����ڲ��ԡ�
	 * 
	 * @return
	 * ����Ե�Dictionary
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
		String same = "��ͬ";
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.deleteWord(same);
		assertFalse(this.dictionary.match(same));
	}
	
	
}
