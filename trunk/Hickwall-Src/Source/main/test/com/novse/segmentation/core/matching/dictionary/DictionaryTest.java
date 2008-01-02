package com.novse.segmentation.core.matching.dictionary;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * 测试新增一个null到词典
	 */
	public void testInsertNll(){
		this.dictionary.insertWord((String)null);
		assertFalse(this.dictionary.match(null));
	}
	
	/**
	 * 测试插入空白字符到词典
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
	 * 测试插入重复的词到词典
	 */
	public void testInsertSameWord(){
		String same = "相同";
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.insertWord(same);
		assertTrue(this.dictionary.match(same));
		this.dictionary.deleteWord(same);
		assertFalse(this.dictionary.match(same));
	}
		
	/**
	 * 测试插入多个词到词典的情况
	 */
	public void  testInsertSeveralWords(){
		String [] strArr = {"中文", "分词","技术"};
		for(int i=0; i<strArr.length; i++){
			this.dictionary.insertWord(strArr[i]);
		}
		for(int i=0; i<strArr.length; i++){
			assertTrue(this.dictionary.match(strArr[i]));
		}
	}
	
	/**
	 * 测试插入空列表
	 */
	public void testInsertListWithEmpty(){
		final String word = "词典";
		final String other = "典词";
		this.dictionary.insertWord(word);
		this.dictionary.insertWord(new ArrayList<String>());
		assertTrue(this.dictionary.match(word));
		assertFalse(this.dictionary.match(other));
	}
	
	/**
	 * 测试插入包含空字符的列表
	 */
	public void testInsertListWithNullWords(){
		List<String> words = this.createWordList(this.blankWords);
		words.add("词典");
			
		this.dictionary.insertWord(words);
		assertTrue(this.dictionary.match("词典"));
		for(String w : this.blankWords){
			assertFalse(this.dictionary.match(w));
		}
	}
	
	/**
	 * 测试插入包含相同词的列表
	 */
	public void testInsertListWithSameWords(){
		String sameWord = "词典";
		String [] ws = {sameWord, "历史", sameWord, "人物"};
		List<String> words = createWordList(ws);
		
		this.dictionary.insertWord(words);
		
		for(String s : ws){
			assertTrue(this.dictionary.match(s));
		}
		this.dictionary.deleteWord(sameWord);
		assertFalse(this.dictionary.match(sameWord));
	}
	
	/**
	 * 测试插入词列表，列表中的某些词已存在词典中
	 */
	public void testInsertListWithSomeWordsExisted(){
		String sameWord = "词典";
		String [] ws = {sameWord, "历史", "人物"};
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
	 * 正确地插入词列表，测试是否都能match得到
	 */
	public void testInsertListCorrect(){
		String [] ws = {"测试", "插入", "列表", "某些", "存在"};
		List<String> words = createWordList(ws);
		
		this.dictionary.insertWord(words);
		for(String s : ws){
			assertTrue(this.dictionary.match(s));
		}
	}
	
	/**
	 * 删除一个词典中不存在的词
	 */
	public void testDeleteNotExisted(){
		String word = "中文";
		String wordDel = "分词";
		this.dictionary.insertWord(word);
		this.dictionary.deleteWord(wordDel);
		
		assertTrue(this.dictionary.match(word));
		assertFalse(this.dictionary.match(wordDel));
	}
	
	/**
	 * 删除一个词典中存在的词
	 */
	public void testDeleteExisted(){
		String word = "中文";
		String wordDel = "中文";
		this.dictionary.insertWord(word);
		this.dictionary.deleteWord(wordDel);
		
		assertFalse(this.dictionary.match(word));
		assertFalse(this.dictionary.match(wordDel));
	}
	
	/**
	 * 删除一个空的列表
	 */
	public void testDeleteListWithEmpty(){
		String [] words = {"中文", "分词"};
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
	 * 删除一个列表，列表中的词都不在词典中
	 */
	public void testDeleteListWithNotWordsExist(){
		String [] words = {"中文", "分词"};
		String [] wordsDel = {"文中", "词分"};
		List<String> list = this.createWordList(words);
		List<String> delList = this.createWordList(wordsDel);
		this.dictionary.insertWord(list);
		this.dictionary.deleteWord(delList);
		for(String s : words){
			assertTrue(this.dictionary.match(s));
		}				
	}
	
	/**
	 * 删除一个列表，列表中有某些词是在词典中的
	 */
	public void testDeleteListWithWordsExist(){
		String [] words = {"中文", "分词"};
		String [] wordsDel = {"中文", "词分"};
		List<String> list = this.createWordList(words);
		List<String> delList = this.createWordList(wordsDel);
		this.dictionary.insertWord(list);
		this.dictionary.deleteWord(delList);
		assertTrue(this.dictionary.match("分词"));
		assertFalse(this.dictionary.match("中文"));
	}
	
	/*
	 * 创建词列表
	 */
	private List<String> createWordList(String[] words) {
		List<String> wordList = new ArrayList<String>();
		for(String s : words){
			wordList.add(s);
		}
		return wordList;
	}
}
