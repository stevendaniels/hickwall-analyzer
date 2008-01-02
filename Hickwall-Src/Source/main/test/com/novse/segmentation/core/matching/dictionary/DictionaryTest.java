package com.novse.segmentation.core.matching.dictionary;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * ���Բ�����б�
	 */
	public void testInsertListWithEmpty(){
		final String word = "�ʵ�";
		final String other = "���";
		this.dictionary.insertWord(word);
		this.dictionary.insertWord(new ArrayList<String>());
		assertTrue(this.dictionary.match(word));
		assertFalse(this.dictionary.match(other));
	}
	
	/**
	 * ���Բ���������ַ����б�
	 */
	public void testInsertListWithNullWords(){
		List<String> words = this.createWordList(this.blankWords);
		words.add("�ʵ�");
			
		this.dictionary.insertWord(words);
		assertTrue(this.dictionary.match("�ʵ�"));
		for(String w : this.blankWords){
			assertFalse(this.dictionary.match(w));
		}
	}
	
	/**
	 * ���Բ��������ͬ�ʵ��б�
	 */
	public void testInsertListWithSameWords(){
		String sameWord = "�ʵ�";
		String [] ws = {sameWord, "��ʷ", sameWord, "����"};
		List<String> words = createWordList(ws);
		
		this.dictionary.insertWord(words);
		
		for(String s : ws){
			assertTrue(this.dictionary.match(s));
		}
		this.dictionary.deleteWord(sameWord);
		assertFalse(this.dictionary.match(sameWord));
	}
	
	/**
	 * ���Բ�����б��б��е�ĳЩ���Ѵ��ڴʵ���
	 */
	public void testInsertListWithSomeWordsExisted(){
		String sameWord = "�ʵ�";
		String [] ws = {sameWord, "��ʷ", "����"};
		List<String> words = createWordList(ws);
		
		this.dictionary.insertWord(sameWord);
		this.dictionary.insertWord(words);
		
		for(String s : ws){
			assertTrue(this.dictionary.match(s));
		}
		this.dictionary.deleteWord(sameWord);
		assertFalse(this.dictionary.match(sameWord));
	}
	
	/**
	 * ��ȷ�ز�����б������Ƿ���match�õ�
	 */
	public void testInsertListCorrect(){
		String [] ws = {"����", "����", "�б�", "ĳЩ", "����"};
		List<String> words = createWordList(ws);
		
		this.dictionary.insertWord(words);
		for(String s : ws){
			assertTrue(this.dictionary.match(s));
		}
	}
	
	/**
	 * ɾ��һ���ʵ��в����ڵĴ�
	 */
	public void testDeleteNotExisted(){
		String word = "����";
		String wordDel = "�ִ�";
		this.dictionary.insertWord(word);
		this.dictionary.deleteWord(wordDel);
		
		assertTrue(this.dictionary.match(word));
		assertFalse(this.dictionary.match(wordDel));
	}
	
	/**
	 * ɾ��һ���ʵ��д��ڵĴ�
	 */
	public void testDeleteExisted(){
		String word = "����";
		String wordDel = "����";
		this.dictionary.insertWord(word);
		this.dictionary.deleteWord(wordDel);
		
		assertFalse(this.dictionary.match(word));
		assertFalse(this.dictionary.match(wordDel));
	}
	
	/**
	 * ɾ��һ���յ��б�
	 */
	public void testDeleteListWithEmpty(){
		String [] words = {"����", "�ִ�"};
		String [] wordsDel = {};
		List<String> list = this.createWordList(words);
		List<String> delList = this.createWordList(wordsDel);
		this.dictionary.insertWord(list);
		this.dictionary.deleteWord(delList);
		for(String s : words){
			assertTrue(this.dictionary.match(s));
		}				
	}
	
	/**
	 * ɾ��һ���б��б��еĴʶ����ڴʵ���
	 */
	public void testDeleteListWithNotWordsExist(){
		String [] words = {"����", "�ִ�"};
		String [] wordsDel = {"����", "�ʷ�"};
		List<String> list = this.createWordList(words);
		List<String> delList = this.createWordList(wordsDel);
		this.dictionary.insertWord(list);
		this.dictionary.deleteWord(delList);
		for(String s : words){
			assertTrue(this.dictionary.match(s));
		}				
	}
	
	/**
	 * ɾ��һ���б��б�����ĳЩ�����ڴʵ��е�
	 */
	public void testDeleteListWithWordsExist(){
		String [] words = {"����", "�ִ�"};
		String [] wordsDel = {"����", "�ʷ�"};
		List<String> list = this.createWordList(words);
		List<String> delList = this.createWordList(wordsDel);
		this.dictionary.insertWord(list);
		this.dictionary.deleteWord(delList);
		assertTrue(this.dictionary.match("�ִ�"));
		assertFalse(this.dictionary.match("����"));
	}
	
	/*
	 * �������б�
	 */
	private List<String> createWordList(String[] words) {
		List<String> wordList = new ArrayList<String>();
		for(String s : words){
			wordList.add(s);
		}
		return wordList;
	}
}
