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
	
	/**
	 * ��������һ��null���ʵ�
	 */
	public void testInsertNll(){
		this.dictionary.insertWord((String)null);
		assertFalse(this.dictionary.match(null));
	}
	
	/**
	 * ���Բ���հ��ַ����ʵ�
	 */
	public void testInsertBlankWord(){
		for(String word : blankWords){
			this.dictionary.insertWord(word);
		}
		for(String word : blankWords){
			assertFalse(this.dictionary.match(word));
		}
	}
	
	/**
	 * ���Բ����ظ��Ĵʵ��ʵ�
	 */
	public void testInsertSameWord(){
		String same = "��ͬ";
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.deleteWord(same);
		assertFalse(this.dictionary.match(same));
	}
	
	/**
	 * ���Բ��뵥�ֵ����
	 */
	public void testInsertSingleWord(){
		String s = "��";
		this.dictionary.insertWord(s);
		assertTrue(this.dictionary.match(s));	
	}	
	
	/**
	 * ���Բ������ʵ��ʵ�����
	 */
	public void  testInsertSeveralWords(){
		String [] strArr = {"����", "�ִ�","����"};
		for(int i=0; i<strArr.length; i++){
			this.dictionary.insertWord(strArr[i]);
		}
		for(int i=0; i<strArr.length; i++){
			assertTrue(this.dictionary.match(strArr[i]));
		}
	}
}
