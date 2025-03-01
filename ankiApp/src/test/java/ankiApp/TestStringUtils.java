package ankiApp;

import static org.junit.Assert.*;

import org.junit.Test;

import bean.DataOfFile;
import utils.StringUtils;

public class TestStringUtils {

	@Test
	public void isValidStringTest() {
		StringUtils stringUtils = new StringUtils();
		
        assertFalse("ひらがな is invalid", stringUtils.isValidString("あ"));
        assertFalse("_ is invalid", stringUtils.isValidString("_")); 
        assertFalse("! is invalid", stringUtils.isValidString("!"));
        assertFalse("spaces is invalid", stringUtils.isValidString("Harada Yuta"));
        assertFalse("empty is invalid", stringUtils.isValidString(""));
        assertFalse("null is invalid", stringUtils.isValidString(null));       
	}

	@Test
	public void isDataOfFileTest1() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		dataOfFile.setElement(1, "question", "answer");
		
		assertTrue("Ok isDataOfFile function", stringUtils.isDataOfFile(dataOfFile, 1));
		
		assertFalse("id is not exist", stringUtils.isDataOfFile(dataOfFile, 2));
		assertFalse("null is isDataOfFile is false", stringUtils.isDataOfFile(null, 1));
	}
	@Test
	public void isDataOfFileTest2() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		
		assertFalse("questionMap is null is false", stringUtils.isDataOfFile(dataOfFile, 1));
	}
	
	@Test
	public void backOrNextIdTest1() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		dataOfFile.setElement(1, "question", "answer");
		dataOfFile.setElement(3, "question", "answer");
		dataOfFile.setElement(5, "question", "answer");
		
		assertEquals(3, stringUtils.backOrNextId(dataOfFile, "back", 5));
	
	}
	
	@Test
	public void backOrNextIdTest2() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		dataOfFile.setElement(1, "question", "answer");
		dataOfFile.setElement(3, "question", "answer");
		dataOfFile.setElement(5, "question", "answer");
		
		assertEquals(3, stringUtils.backOrNextId(dataOfFile, "next", 1));
	}
	
	@Test
	public void backOrNextIdTest3() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		dataOfFile.setElement(1, "question", "answer");
		dataOfFile.setElement(3, "question", "answer");
		dataOfFile.setElement(5, "question", "answer");
		
		assertEquals(1, stringUtils.backOrNextId(dataOfFile, "back", 1));
	}
	
	@Test
	public void backOrNextIdTest4() {
		StringUtils stringUtils = new StringUtils();
		DataOfFile dataOfFile = new DataOfFile();
		dataOfFile.setElement(1, "question", "answer");
		dataOfFile.setElement(3, "question", "answer");
		dataOfFile.setElement(5, "question", "answer");
		
		assertEquals(5, stringUtils.backOrNextId(dataOfFile, "next", 5));
	}
//	public int backOrNextId(DataOfFile dataOfFile, String backOrNext, int id) {
//		if("back".equals(backOrNext)) {
//			//idが最小ではなくそのidが存在するときwhile文を抜ける
//			id--;
//			while(!isDataOfFile(dataOfFile, id) && id != dataOfFile.getMinId()) {
//				id--;
//			}
//			return id;
//		}else {
//			id++;
//			while(isDataOfFile(dataOfFile, id) && id != dataOfFile.getMaxId()) {
//				id++;
//			}
//			return id;
//		}
//	}
//	
	public boolean isEmptyOrSpace(String str) {
		return str == null || str.trim().isEmpty();
	}
}
